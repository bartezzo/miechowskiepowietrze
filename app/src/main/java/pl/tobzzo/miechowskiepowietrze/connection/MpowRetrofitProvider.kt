package pl.tobzzo.miechowskiepowietrze.connection

import io.reactivex.Observable
import pl.tobzzo.miechowskiepowietrze.rest.retrofit.GetMeasurementsService
import pl.tobzzo.miechowskiepowietrze.rest.retrofit.RetrofitClientInstance
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.utils.ApiKeyProvider
import javax.inject.Inject

class MpowRetrofitProvider @Inject constructor(private val apiKeyProvider: ApiKeyProvider) : RetrofitProvider {
  private val getMeasurementsService: GetMeasurementsService = RetrofitClientInstance.getRetrofitInstance().create(
    GetMeasurementsService::class.java)

  override fun getAllMeasurements(indexType: String, sensor: Sensor): Observable<Measurements>? {

    return getMeasurementsService.getAllMeasurements(apiKeyProvider.apiKey, indexType, sensor.gpsLatitude, sensor.gpsLongitude)
  }
}