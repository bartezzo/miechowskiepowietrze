package pl.tobzzo.miechowskiepowietrze.rest.retrofit

import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.reflect.KFunction2

class GetMeasurementsServiceCallback(
  private val sensor: Sensor,
  private val action: KFunction2<@ParameterName(
    name = "sensor") Sensor, @ParameterName(
    name = "response") Measurements, Unit>
) : Callback<Measurements> {


  override fun onFailure(call: Call<Measurements>, t: Throwable) {
    Timber.e("mpow allMeasurements onFailure")
  }

  override fun onResponse(call: Call<Measurements>, response: Response<Measurements>) {
    Timber.d("mpow allMeasurements onResponse")

    response.let {
      it.body()?.let { measurements ->
        action(sensor, measurements)
      }
    }
  }

}