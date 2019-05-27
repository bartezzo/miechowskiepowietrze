package pl.tobzzo.miechowskiepowietrze.network

import android.content.Context
import pl.tobzzo.miechowskiepowietrze.BuildConfig
import pl.tobzzo.miechowskiepowietrze.MpowApplication
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import pl.tobzzo.miechowskiepowietrze.connection.RetrofitProvider
import pl.tobzzo.miechowskiepowietrze.rest.retrofit.GetMeasurementsServiceCallback
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace
import pl.tobzzo.miechowskiepowietrze.utils.TimeUtils
import timber.log.Timber
import java.util.HashMap
import javax.inject.Inject


class MpowNetworkComponent(private val context: Context) : NetworkComponent {
  @Inject lateinit var retrofitProvider: RetrofitProvider
  @Inject lateinit var analyticsComponent: AnalyticsComponent
  @Inject lateinit var sensorObject: SensorObject

  private var responseMap: MutableMap<SensorPlace, Measurements>? = null
  private val listeners = mutableListOf<NetworkListener>()
  private var lastLoading: Long = 0


  override fun initialize() {
    (context as MpowApplication).appComponent.inject(this)
  }

  private fun makeHttpRequest(
    sensor: Sensor
  )/*: Single<Measurements>*/ {

    Timber.d("Response map remove:${sensor.place}")
    responseMap!!.remove(sensor.place)


    retrofitProvider.getAllMeasurements("AIRLY_CAQI", sensor, ::parseNewResult)
  }

  private fun parseNewResult(sensor: Sensor,
    response: Measurements) {
    responseMap!![sensor.place] = response
    tryToShowResult()
  }

  override fun restartLoading(forceRefresh: Boolean) {
    analyticsComponent.logAction("logAction", "restartLoading")

    if (forceRefresh)
      lastLoading = 0

    if (!needToRefresh()) {
      showResult()
    } else {
      lastLoading = System.currentTimeMillis()
      responseMap = HashMap()
      listSensorsMeasurements()
      listeners.forEach {
        it.onValuesLoading()
      }
    }
  }

  override fun getResponseMap(): MutableMap<SensorPlace, Measurements>? = responseMap

  override fun attachNetworkListener(listener: NetworkListener) {
    listeners.add(listener)
  }

  override fun detachNetworkListener(listener: NetworkListener) {
    listeners.remove(listener)
  }

  private fun needToRefresh() = System.currentTimeMillis() - lastLoading > 30 * TimeUtils.ONE_SECOND

  private fun tryToShowResult() {
    Timber.d("Response map tryToShowResult size:${responseMap!!.size}")
    val calls = responseMap!!.size
    var responses = 0

    val iterator = responseMap!!.entries.iterator()
    while (iterator.hasNext()) {
      iterator.next()
      responses++
    }

    if (responses < calls) {
      Timber.d("tryToShowResult only $responses available (calls=$calls)")
    } else {
      Timber.d("tryToShowResult all $responses available (calls=$calls)")
      showResult()
    }
  }

  private fun showResult() {
    analyticsComponent.logAction("logAction", "showResult")

    listeners.forEach(action = {
      it.onValuesAvailable()
    })
  }

  private fun listSensorsMeasurements() {
    val iterator = sensorObject.activeSensors.iterator()
    while (iterator.hasNext()) {
      val sensorPlace = iterator.next()
      val sensor = sensorObject.getSensor(sensorPlace)
      sensor?.let {
          makeHttpRequest(sensor)
      }
    }
  }
}
