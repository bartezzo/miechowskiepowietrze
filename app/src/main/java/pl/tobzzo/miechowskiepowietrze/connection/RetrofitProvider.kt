package pl.tobzzo.miechowskiepowietrze.connection

import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import retrofit2.Call
import kotlin.reflect.KFunction2

interface RetrofitProvider {
  fun getAllMeasurements(indexType: String, sensor: Sensor, action: KFunction2<@ParameterName(
    name = "sensor") Sensor, @ParameterName(
    name = "response") Measurements, Unit>)
}