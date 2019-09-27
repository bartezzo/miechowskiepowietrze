package pl.tobzzo.miechowskiepowietrze.connection

import pl.tobzzo.miechowskiepowietrze.rest.retrofit.GetMeasurementsService
import pl.tobzzo.miechowskiepowietrze.rest.retrofit.GetMeasurementsServiceCallback
import pl.tobzzo.miechowskiepowietrze.rest.retrofit.RetrofitClientInstance
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.utils.ApiKeyProvider
import javax.inject.Inject
import kotlin.reflect.KFunction2

class MpowRetrofitProvider @Inject constructor(private val apiKeyProvider: ApiKeyProvider) : RetrofitProvider {
  private val getMeasurementsService: GetMeasurementsService = RetrofitClientInstance.getRetrofitInstance().create(
    GetMeasurementsService::class.java)

  override fun getAllMeasurements(indexType: String, sensor: Sensor, action: KFunction2<@ParameterName(
    name = "sensor") Sensor, @ParameterName(
    name = "response") Measurements, Unit>) {

    return getMeasurementsService.getAllMeasurements(apiKeyProvider.apiKey, indexType, sensor.gpsLatitude, sensor.gpsLongitude).enqueue(
      GetMeasurementsServiceCallback(sensor, action))
  }
}