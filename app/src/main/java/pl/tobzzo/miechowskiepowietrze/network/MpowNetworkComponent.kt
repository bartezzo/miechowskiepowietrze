package pl.tobzzo.miechowskiepowietrze.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import pl.tobzzo.miechowskiepowietrze.BuildConfig
import pl.tobzzo.miechowskiepowietrze.MpowApplication
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import pl.tobzzo.miechowskiepowietrze.connection.IonProvider
import pl.tobzzo.miechowskiepowietrze.rest.v1.SensorMeasurementsResponseV1
import pl.tobzzo.miechowskiepowietrze.rest.v2.SensorMeasurementsResponseV2
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace
import pl.tobzzo.miechowskiepowietrze.utils.extensions.mapToUrl
import timber.log.Timber
import java.util.HashMap
import javax.inject.Inject


class MpowNetworkComponent(private val context: Context) : NetworkComponent{
  @Inject lateinit var ionProvider: IonProvider
  @Inject lateinit var analyticsComponent: AnalyticsComponent
  @Inject lateinit var sensorObject: SensorObject

  private var responseMap: MutableMap<SensorPlace, SensorMeasurementsResponseV1>? = null
  private val listeners = mutableListOf<NetworkListener>()
  private var lastLoading: Long = 0
  private val apiKey: String
    get() {
      return (if (System.currentTimeMillis() % 2 == 0L)
        BuildConfig.hiddenPassword1
      else
        BuildConfig.hiddenPassword2
        ).also {
        analyticsComponent.logAction("logAction", "onCreate")
        Timber.d("api key:$it")
      }
    }

  override fun initialize() {
    (context as MpowApplication).appComponent.inject(this)
  }

  override fun makeHttpRequest(url: String?, sensor: Sensor) {
    if (url == null)
      return

    responseMap!!.remove(sensor.place)
    ionProvider.readSensorValues(url, apiKey) {exception:Exception?, result: JsonObject -> parseResult(exception, result, sensor)}
  }

  override fun restartLoading(forceRefresh: Boolean) {
    analyticsComponent.logAction("logAction", "restartLoading")

      if (forceRefresh)
        lastLoading = 0

      if (System.currentTimeMillis() - lastLoading < 30 * 60 * 1000) {
        showResult()
      } else {
        lastLoading = System.currentTimeMillis()
        responseMap = HashMap()
//        httpHandler!!.postDelayed({ listSensorsMeasurements() }, (2 * 1000).toLong())
        listSensorsMeasurements()
       listeners.forEach {
         it.onValuesLoading()
       }
      }
  }

  override fun getResponseMap(): MutableMap<SensorPlace, SensorMeasurementsResponseV1>? {
    return responseMap
  }

  override fun attachNetworkListener(listener: NetworkListener) {
    listeners.add(listener)
  }

  override fun detachNetworkListener(listener: NetworkListener) {
    listeners.remove(listener)
  }

  @Synchronized private fun parseResult(exception: Exception?, result: JsonObject, sensor: Sensor) {
    try {
      exception?.let {
        Timber.e(exception,"parseResult ERROR original exception")
      }

      val gsonBuilder = GsonBuilder()
      val gson = gsonBuilder.create()
      val decoded = gson.fromJson(result, SensorMeasurementsResponseV2::class.java)
      val isDecoded = SensorMeasurementsResponseV1::class.java.isInstance(decoded)
      val sensorMeasurementsResponse = decoded as SensorMeasurementsResponseV1
      responseMap!![sensor.place] = sensorMeasurementsResponse

      tryToShowResult()
    } catch (ex: Exception) {
      Timber.e(ex, "parseResult ERROR")
    }
  }

  private fun tryToShowResult() {
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
        makeHttpRequest(sensor.mapToUrl(), sensor)
      }
    }
  }
}
