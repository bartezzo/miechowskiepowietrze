package pl.tobzzo.miechowskiepowietrze.connection

import io.reactivex.Observable
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor

interface RetrofitProvider {
  fun getAllMeasurements(indexType: String, sensor: Sensor): Observable<Measurements>?
}