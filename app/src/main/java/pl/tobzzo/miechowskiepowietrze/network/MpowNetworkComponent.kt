package pl.tobzzo.miechowskiepowietrze.network

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.*
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import pl.tobzzo.miechowskiepowietrze.connection.RetrofitProvider
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_KOPERNIKA
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_KROTKA
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_PARKOWE
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_RYNEK
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SIKORSKIEGO
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SZPITALNA
import pl.tobzzo.miechowskiepowietrze.utils.TimeUtils
import timber.log.Timber
import javax.inject.Inject


class MpowNetworkComponent @Inject constructor(val context: Context, val retrofitProvider: RetrofitProvider, val analyticsComponent:
AnalyticsComponent, val sensorObject: SensorObject) : NetworkComponent {

  private var responseMap: LinkedHashMap<Sensor, Measurements>? = null
  private val listeners = mutableListOf<NetworkListener>()
  private var lastLoading: Long = 0


  private fun makeHttpRequest(
    sensor: Sensor
  ): Observable<Measurements>? {

    Timber.d("Response map remove:${sensor.place}")
    responseMap!!.remove(sensor)


    return retrofitProvider.getAllMeasurements("AIRLY_CAQI", sensor)
  }

  private fun parseNewResult(sensor: Sensor,
    response: Measurements) {
    Timber.d("parseNewResult for sensor:${sensor.name}")
    responseMap!![sensor] = response
  }

  override fun restartLoading(forceRefresh: Boolean) {
    analyticsComponent.logAction("logAction", "restartLoading")

    if (forceRefresh)
      lastLoading = 0

    if (!needToRefresh()) {
      showResult()
    } else {
      lastLoading = System.currentTimeMillis()
      responseMap = LinkedHashMap()
      listSensorsMeasurements()
      listeners.forEach {
        it.onValuesLoading()
      }
    }
  }

  override fun getResponseMap(): LinkedHashMap<Sensor, Measurements>? = responseMap

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
//    val iterator = sensorObject.activeSensors.iterator()
//    while (iterator.hasNext()) {
//      val sensorPlace = iterator.next()
//      val sensor = sensorObject.getSensor(sensorPlace)
//      sensor?.let {
//        makeHttpRequest(sensor)
//      }
//    }

    val sensorSIKORSKIEGO = sensorObject.getSensor(MIECHOW_SIKORSKIEGO)
    val sensorRYNEK = sensorObject.getSensor(MIECHOW_RYNEK)
    val sensorKOPERNIKA = sensorObject.getSensor(MIECHOW_KOPERNIKA)
    val sensorPARKOWE = sensorObject.getSensor(MIECHOW_PARKOWE)
    val sensorSZPITALNA = sensorObject.getSensor(MIECHOW_SZPITALNA)
    val sensorKROTKA = sensorObject.getSensor(MIECHOW_KROTKA)

    val completable1 = makeHttpRequest(sensorSIKORSKIEGO!!)
    val completable2 = makeHttpRequest(sensorRYNEK!!)
    val completable3 = makeHttpRequest(sensorKOPERNIKA!!)
    val completable4 = makeHttpRequest(sensorPARKOWE!!)
    val completable5 = makeHttpRequest(sensorSZPITALNA!!)
    val completable6 = makeHttpRequest(sensorKROTKA!!)

    val disposable = completable1
      ?.mergeWith(completable2)
      ?.mergeWith(completable3)
      ?.mergeWith(completable4)
      ?.mergeWith(completable5)
      ?.mergeWith(completable6)
      ?.observeOn(mainThread())
      ?.subscribeOn(Schedulers.io())
      ?.map { item -> parseNewResult(sensorSIKORSKIEGO, item) }
      ?.subscribe { tryToShowResult()
      }

  }

  private fun onError(error: Throwable) {
    Timber.d("Completable onError:$error")
    showResult()
  }

  private fun onSuccess() {
    Timber.d("Completable onSuccess")
    showResult()
  }
}
