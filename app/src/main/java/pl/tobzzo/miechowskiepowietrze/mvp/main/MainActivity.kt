package pl.tobzzo.miechowskiepowietrze.mvp.main

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import pl.tobzzo.miechowskiepowietrze.R
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import pl.tobzzo.miechowskiepowietrze.example.ExampleInterface
import pl.tobzzo.miechowskiepowietrze.logging.LoggingManager
import pl.tobzzo.miechowskiepowietrze.main.MainAdapter
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Favorite
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Main
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Settings
import pl.tobzzo.miechowskiepowietrze.mvp.base.BaseActivity
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorActivity
import pl.tobzzo.miechowskiepowietrze.network.NetworkComponent
import pl.tobzzo.miechowskiepowietrze.network.NetworkListener
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject
import pl.tobzzo.miechowskiepowietrze.utils.ApiKeyProvider
import pl.tobzzo.miechowskiepowietrze.utils.extensions.bindView
import timber.log.Timber
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.View, NetworkListener, OnRefreshListener {

  @Inject lateinit var sensorObject: SensorObject
  @Inject lateinit var loggingManager: LoggingManager
  @Inject lateinit var networkComponent: NetworkComponent
  @Inject lateinit var analyticsComponent: AnalyticsComponent
  @Inject lateinit var apiKeyProvider: ApiKeyProvider
  @Inject lateinit var mainPresenter: MainPresenter
  @Inject lateinit var exampleClass: ExampleInterface

  private val swipeLayout: SwipeRefreshLayout by bindView(R.id.swipe_layout)
  private val bottomNavigation: BottomNavigationView by bindView(R.id.bottom_navigation)
  private val mainGridView: GridView by bindView(R.id.main_grid_view)

  override fun getLayoutResId(): Int = R.layout.activity_main

  override fun init() {
    super.init()
  }

  override fun onResume() {
    super.onResume()
    mainPresenter.takeView(this)
    analyticsComponent.logAction("logAction", "onResume")
    analyticsComponent.logScreen("LoginActivity")
    networkComponent.restartLoading()
  }

  override fun onDestroy() {
    super.onDestroy()
    networkComponent.detachNetworkListener(this)
    mainPresenter.dropView()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    exampleClass.takeInt()

    networkComponent.attachNetworkListener(this)
    Timber.d("apiKeyProvider.apiKey: ${apiKeyProvider.apiKey}")
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    analyticsComponent.logAction("logAction", "onCreate")
    setElements()
    setListeners()
  }
  override fun onValuesLoading() {
    Timber.d("onValuesLoading")
//    sensorResultTable.isVisible = false
  }

  override fun onValuesAvailable() {
    Timber.d("onValuesAvailable")
    setGlobalChart()
//    sensorResultTable.isVisible = true
  }

  override fun updateView() {
    networkComponent.restartLoading()
  }

  override fun showFavorite() {
    startActivity(Intent(this, SensorActivity::class.java))
    finish()
  }

  override fun showSettings() {
    //go to settings
  }

  private fun setElements() {
    /*
    swipeLayout.setOnRefreshListener(this)
    swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
      android.R.color.holo_green_light,
      android.R.color.holo_orange_light,
      android.R.color.holo_red_light)
      */
  }

  override fun setListeners() {
    swipeLayout.setOnRefreshListener(this)
    swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
      android.R.color.holo_green_light,
      android.R.color.holo_orange_light,
      android.R.color.holo_red_light)

    bottomNavigation.setOnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.action_main -> {
          mainPresenter.onNavigationItemClicked(Main)
        }

        R.id.action_favorite -> {
          mainPresenter.onNavigationItemClicked(Favorite)
        }

        R.id.action_settings -> {
          mainPresenter.onNavigationItemClicked(Settings)
        }
      }
      return@setOnNavigationItemSelectedListener true
    }
  }

  private fun setGlobalChart() {
    val mainAdapter = MainAdapter(this, networkComponent.getResponseMap())
    mainGridView.adapter = mainAdapter


    ///////////
//    var iterator: Iterator<*> = networkComponent.getResponseMap()!!.entries.iterator()
//    var maxCAQI = 0.0
//    var scaledMaxCAQI = 0
//    var sumCAQI = 0.0
//    var avgCAQI = 0.0
//    var countCAQI = 0
//
//    while (iterator.hasNext()) {
//      val entry = iterator.next() as Map.Entry<SensorPlace, Measurements>
//      val CAQI = entry.value.current?.indexes?.get(0)?.value?.toDouble() ?: 0.0
//      maxCAQI = Math.max(maxCAQI, CAQI)
//      sumCAQI += CAQI
//      countCAQI += 1
//    }
//    scaledMaxCAQI = (100 * maxCAQI / 50).toInt()
//    avgCAQI = sumCAQI / countCAQI
//
//    iterator = networkComponent.getResponseMap()!!.entries.iterator()
//    while (iterator.hasNext()) {
//      val entry = iterator.next() as Map.Entry<SensorPlace, Measurements>
//      val sensorName = entry.key
//      val sensorValues = entry.value
//      var progressToUpdate: NumberProgressBar?
//      var textViewToUpdatePm25: TextView? = null
//      var textViewToUpdatePm10: TextView? = null
//
//      when (sensorName) {
//        MIECHOW_SIKORSKIEGO -> {
////          progressToUpdate = sensorSikorskiegoProgress
////          textViewToUpdatePm25 = sensorSikorskiegoDetailsPm25
////          textViewToUpdatePm10 = sensorSikorskiegoDetailsPm10
//        }
//        MIECHOW_RYNEK -> {
////          progressToUpdate = sensorRynekProgress
////          textViewToUpdatePm25 = sensorRynekDetailsPm25
////          textViewToUpdatePm10 = sensorRynekDetailsPm10
//        }
//        MIECHOW_KOPERNIKA -> {
////          progressToUpdate = sensorKopernikaProgress
////          textViewToUpdatePm25 = sensorKopernikaDetailsPm25
////          textViewToUpdatePm10 = sensorKopernikaDetailsPm10
//        }
//        MIECHOW_PARKOWE -> {
////          progressToUpdate = sensorParkoweProgress
////          textViewToUpdatePm25 = sensorParkoweDetailsPm25
////          textViewToUpdatePm10 = sensorParkoweDetailsPm10
//        }
//        MIECHOW_SZPITALNA -> {
////          progressToUpdate = sensorSzpitalnaProgress
////          textViewToUpdatePm25 = sensorSzpitalnaDetailsPm25
////          textViewToUpdatePm10 = sensorSzpitalnaDetailsPm10
//        }
//        MIECHOW_KROTKA -> {
////          progressToUpdate = sensorKrotkaProgress
////          textViewToUpdatePm25 = sensorKrotkaDetailsPm25
////          textViewToUpdatePm10 = sensorKrotkaDetailsPm10
//        }
//        else -> {
//          throw Exception("Wrong sensor passed")
//        }
//      }
//
//      val CAQI = sensorValues.current?.values?.get(0)?.value?.toDouble() ?: 0.0
//      val scaledCAQI = (100 * CAQI / 50).toInt()
//
////      progressToUpdate.max = maxCAQI.toInt()
////      progressToUpdate.progress = CAQI.toInt()
////      progressToUpdate.reachedBarColor = CAQI.mapToBarColor(resources)
//
//      setDetailsInfo(entry, textViewToUpdatePm25, textViewToUpdatePm10)
//    }

//    airQualityImageView.setImageResource(avgCAQI.mapToLogoImage())
  }

  override fun onRefresh() {
    analyticsComponent.logAction("logAction", "onRefresh")
    networkComponent.restartLoading(true)
    swipeLayout.isRefreshing = false
  }
}