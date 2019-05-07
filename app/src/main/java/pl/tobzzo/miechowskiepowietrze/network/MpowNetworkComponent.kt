package pl.tobzzo.miechowskiepowietrze.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.reactivex.Single
import pl.tobzzo.miechowskiepowietrze.BuildConfig
import pl.tobzzo.miechowskiepowietrze.MpowApplication
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import pl.tobzzo.miechowskiepowietrze.connection.RetrofitProvider
import pl.tobzzo.miechowskiepowietrze.rest.retrofit.GetMeasurementsService
import pl.tobzzo.miechowskiepowietrze.rest.retrofit.RetrofitClientInstance
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace
import pl.tobzzo.miechowskiepowietrze.utils.TimeUtils
import pl.tobzzo.miechowskiepowietrze.utils.extensions.mapToUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.HashMap
import javax.inject.Inject


class MpowNetworkComponent(private val context: Context) : NetworkComponent {
  //  @Inject lateinit var ionProvider: IonProvider
  @Inject lateinit var retrofitProvider: RetrofitProvider
  @Inject lateinit var analyticsComponent: AnalyticsComponent
  @Inject lateinit var sensorObject: SensorObject

  private var responseMap: MutableMap<SensorPlace, Measurements>? = null
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

  private fun makeHttpRequest(
    url: String,
    sensor: Sensor
  )/*: Single<Measurements>*/ {

    Timber.d("Response map remove:${sensor.place}")
    responseMap!!.remove(sensor.place)


    val service = RetrofitClientInstance.getRetrofitInstance().create(GetMeasurementsService::class.java)
    val call = service.getAllMeasurements("AIRLY_CAQI", sensor.gpsLatitude, sensor.gpsLongitude)
    call.enqueue(object : Callback<Measurements> {
      override fun onFailure(call: Call<Measurements>, t: Throwable) {
        Timber.d("mpow allMeasurements onFailure")
      }

      override fun onResponse(call: Call<Measurements>, response: Response<Measurements>) {
        Timber.d("mpow allMeasurements onResponse")

        response.let {
          it.body()?.let { measurements ->
            parseNewResult(sensor, measurements)
          }
        }
      }
    })

//    ionProvider.readSensorValues(
//      url, apiKey)

//    var httpexc: Throwable? = null
//    var httpres: JsonObject? = null
//
//    ionProvider.readSensorValues(
//      url, apiKey
//    ) { exception: Exception?, result: JsonObject ->
//      httpexc = exception
//      httpres = result
//    }
//
//    return if (httpexc != null) {
//      Single.error(httpexc)
//    } else {
//      parseResult(httpres, sensor)
//    }


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

  @Synchronized private fun parseResult(
    result: JsonObject?,
    sensor: Sensor
  ): Single<Measurements> {
    try {

      val gsonBuilder = GsonBuilder()
      val gson = gsonBuilder.create()
      val decoded = gson.fromJson(result, Measurements::class.java)
      val isDecoded = Measurements::class.java.isInstance(decoded)
      val sensorMeasurementsResponse = decoded as Measurements
      Timber.d("Response map add:${sensor.place}")
//      responseMap!![sensor.place] = sensorMeasurementsResponse

//      tryToShowResult()
      return Single.just(sensorMeasurementsResponse)
    } catch (ex: Exception) {
      Timber.e(ex, "parseResult ERROR")
      return Single.error(ex)
    }
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
        val url = sensor.mapToUrl()
        url?.let {
          makeHttpRequest(it, sensor)
//          val httpRequestObservable: Single<Measurements> =
//            makeHttpRequest(url, sensor)
//
//          httpRequestObservable.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//              onSuccess = { print("listSensorsMeasurements SUCCESS" + it) },
//              onError = { print("listSensorsMeasurements ERROR" + it) }
//            )
        }
      }
    }
  }
}
