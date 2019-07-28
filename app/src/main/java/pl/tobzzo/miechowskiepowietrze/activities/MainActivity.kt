package pl.tobzzo.miechowskiepowietrze.activities

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.daimajia.numberprogressbar.NumberProgressBar
import pl.tobzzo.miechowskiepowietrze.MpowApplication
import pl.tobzzo.miechowskiepowietrze.R.id
import pl.tobzzo.miechowskiepowietrze.R.layout
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import pl.tobzzo.miechowskiepowietrze.logging.LoggingManager
import pl.tobzzo.miechowskiepowietrze.network.NetworkComponent
import pl.tobzzo.miechowskiepowietrze.network.NetworkListener
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_KOPERNIKA
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_KROTKA
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_PARKOWE
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_RYNEK
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SIKORSKIEGO
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SZPITALNA
import pl.tobzzo.miechowskiepowietrze.utils.ApiKeyProvider
import pl.tobzzo.miechowskiepowietrze.utils.extensions.bindView
import pl.tobzzo.miechowskiepowietrze.utils.extensions.isVisible
import pl.tobzzo.miechowskiepowietrze.utils.extensions.mapToBarColor
import pl.tobzzo.miechowskiepowietrze.utils.extensions.mapToLogoImage
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, NetworkListener {

  @Inject lateinit var sensorObject: SensorObject
  @Inject lateinit var loggingManager: LoggingManager
  @Inject lateinit var networkComponent: NetworkComponent
  @Inject lateinit var analyticsComponent: AnalyticsComponent
  @Inject lateinit var apiKeyProvider: ApiKeyProvider

  private val swipeLayout: SwipeRefreshLayout by bindView(id.swipe_container)
  private val sensorLoadingProgress: ProgressBar by bindView(id.sensorLoadingProgress)
  private val sensorResultTable: TableLayout by bindView(id.sensorResultTable)

  private val sensorSikorskiego: TableRow by bindView(id.sensorSikorskiego)
  private val sensorSikorskiegoProgress: NumberProgressBar by bindView(id.sensorSikorskiegoProgress)
  private val sensorSikorskiegoDetailsPm25: TextView by bindView(id.sensorSikorskiegoDetailsPm25)
  private val sensorSikorskiegoDetailsPm10: TextView by bindView(id.sensorSikorskiegoDetailsPm10)

  private val sensorRynek: TableRow by bindView(id.sensorRynek)
  private val sensorRynekProgress: NumberProgressBar by bindView(id.sensorRynekProgress)
  private val sensorRynekDetailsPm25: TextView by bindView(id.sensorRynekDetailsPm25)
  private val sensorRynekDetailsPm10: TextView by bindView(id.sensorRynekDetailsPm10)

  private val sensorKopernika: TableRow by bindView(id.sensorKopernika)
  private val sensorKopernikaProgress: NumberProgressBar by bindView(id.sensorKopernikaProgress)
  private val sensorKopernikaDetailsPm25: TextView by bindView(id.sensorKopernikaDetailsPm25)
  private val sensorKopernikaDetailsPm10: TextView by bindView(id.sensorKopernikaDetailsPm10)

  private val sensorParkowe: TableRow by bindView(id.sensorParkowe)
  private val sensorParkoweProgress: NumberProgressBar by bindView(id.sensorParkoweProgress)
  private val sensorParkoweDetailsPm25: TextView by bindView(id.sensorParkoweDetailsPm25)
  private val sensorParkoweDetailsPm10: TextView by bindView(id.sensorParkoweDetailsPm10)

  private val sensorSzpitalna: TableRow by bindView(id.sensorSzpitalna)
  private val sensorSzpitalnaProgress: NumberProgressBar by bindView(id.sensorSzpitalnaProgress)
  private val sensorSzpitalnaDetailsPm25: TextView by bindView(id.sensorSzpitalnaDetailsPm25)
  private val sensorSzpitalnaDetailsPm10: TextView by bindView(id.sensorSzpitalnaDetailsPm10)

  private val sensorKrotka: TableRow by bindView(id.sensorKrotka)
  private val sensorKrotkaProgress: NumberProgressBar by bindView(id.sensorKrotkaProgress)
  private val sensorKrotkaDetailsPm25: TextView by bindView(id.sensorKrotkaDetailsPm25)
  private val sensorKrotkaDetailsPm10: TextView by bindView(id.sensorKrotkaDetailsPm10)

  private val airQualityImageView: ImageView by bindView(id.airQualityImageView)
  private val sensorsScrollView: ScrollView by bindView(id.sensorsScrollView)

  private var fakeCAQI = -1.0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    (this.application as MpowApplication).appComponent.inject(this)
    loggingManager.initialize()
    networkComponent.initialize()
    analyticsComponent.initialize()
    apiKeyProvider.initialize()

    networkComponent.attachNetworkListener(this)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    analyticsComponent.logAction("logAction", "onCreate")
    setElements()
    setListeners()
  }

  override fun onDestroy() {
    super.onDestroy()
    networkComponent.detachNetworkListener(this)
  }

  override fun onValuesLoading() {
    sensorLoadingProgress.isVisible = true
    sensorResultTable.isVisible = false
  }

  override fun onValuesAvailable() {
    setGlobalChart()
    sensorLoadingProgress.isVisible = false
    sensorResultTable.isVisible = true
  }

  private fun setElements() {
    swipeLayout.setOnRefreshListener(this)
    swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
      android.R.color.holo_green_light,
      android.R.color.holo_orange_light,
      android.R.color.holo_red_light)
  }

  private fun setListeners() {
    airQualityImageView.setOnClickListener {
      analyticsComponent.logAction("logAction", "airQualityImageView")
      fakeCAQI = (fakeCAQI + 25) % 168 - 1
      airQualityImageView.setImageResource(fakeCAQI.mapToLogoImage())
    }
  }

  private fun setGlobalChart() {
    var iterator: Iterator<*> = networkComponent.getResponseMap()!!.entries.iterator()
    var maxCAQI = 0.0
    var scaledMaxCAQI = 0
    var sumCAQI = 0.0
    var avgCAQI = 0.0
    var countCAQI = 0

    while (iterator.hasNext()) {
      val entry = iterator.next() as Map.Entry<SensorPlace, Measurements>
      val CAQI = entry.value.current?.indexes?.get(0)?.value?.toDouble() ?: 0.0
      maxCAQI = Math.max(maxCAQI, CAQI)
      sumCAQI += CAQI
      countCAQI += 1
    }
    scaledMaxCAQI = (100 * maxCAQI / 50).toInt()
    avgCAQI = sumCAQI / countCAQI

    iterator = networkComponent.getResponseMap()!!.entries.iterator()
    while (iterator.hasNext()) {
      val entry = iterator.next() as Map.Entry<SensorPlace, Measurements>
      val sensorName = entry.key
      val sensorValues = entry.value
      var progressToUpdate: NumberProgressBar?
      var textViewToUpdatePm25: TextView?
      var textViewToUpdatePm10: TextView?

      when (sensorName) {
        MIECHOW_SIKORSKIEGO -> {
          progressToUpdate = sensorSikorskiegoProgress
          textViewToUpdatePm25 = sensorSikorskiegoDetailsPm25
          textViewToUpdatePm10 = sensorSikorskiegoDetailsPm10
        }
        MIECHOW_RYNEK -> {
          progressToUpdate = sensorRynekProgress
          textViewToUpdatePm25 = sensorRynekDetailsPm25
          textViewToUpdatePm10 = sensorRynekDetailsPm10
        }
        MIECHOW_KOPERNIKA -> {
          progressToUpdate = sensorKopernikaProgress
          textViewToUpdatePm25 = sensorKopernikaDetailsPm25
          textViewToUpdatePm10 = sensorKopernikaDetailsPm10
        }
        MIECHOW_PARKOWE -> {
          progressToUpdate = sensorParkoweProgress
          textViewToUpdatePm25 = sensorParkoweDetailsPm25
          textViewToUpdatePm10 = sensorParkoweDetailsPm10
        }
        MIECHOW_SZPITALNA -> {
          progressToUpdate = sensorSzpitalnaProgress
          textViewToUpdatePm25 = sensorSzpitalnaDetailsPm25
          textViewToUpdatePm10 = sensorSzpitalnaDetailsPm10
        }
        MIECHOW_KROTKA -> {
          progressToUpdate = sensorKrotkaProgress
          textViewToUpdatePm25 = sensorKrotkaDetailsPm25
          textViewToUpdatePm10 = sensorKrotkaDetailsPm10
        }
        else -> {
          throw Exception("Wrong sensor passed")
        }
      }

      val CAQI = sensorValues.current?.values?.get(0)?.value?.toDouble() ?: 0.0
      val scaledCAQI = (100 * CAQI / 50).toInt()

      progressToUpdate.max = maxCAQI.toInt()
      progressToUpdate.progress = CAQI.toInt()
      progressToUpdate.reachedBarColor = CAQI.mapToBarColor(resources)

      setDetailsInfo(entry, textViewToUpdatePm25, textViewToUpdatePm10)
    }

    airQualityImageView.setImageResource(avgCAQI.mapToLogoImage())
  }

  private fun setDetailsInfo(entry: Map.Entry<SensorPlace, Measurements>, textViewToUpdatePm25: TextView,
    textViewToUpdatePm10: TextView) {
    val patternPm25 = "%1\$s%% (pm 2.5)"
    val patternPm10 = "%1\$s%% (pm  10)"
    val pm25 = entry.value.current?.standards?.get(0)?.percent
    val pm10 = entry.value.current?.standards?.get(1)?.percent
    val infoPm25 = String.format(patternPm25, pm25?.toInt())
    val infoPm10 = String.format(patternPm10, pm10?.toInt())
    textViewToUpdatePm25.text = infoPm25
    textViewToUpdatePm10.text = infoPm10
  }

  override fun onResume() {
    super.onResume()
    analyticsComponent.logAction("logAction", "onResume")
    analyticsComponent.logScreen("MainActivity")
    networkComponent.restartLoading(false)
  }


  override fun onRefresh() {
    analyticsComponent.logAction("logAction", "onRefresh")
    networkComponent.restartLoading(true)
    swipeLayout.isRefreshing = false
  }
}