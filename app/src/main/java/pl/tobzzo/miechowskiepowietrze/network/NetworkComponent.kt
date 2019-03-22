package pl.tobzzo.miechowskiepowietrze.network

import pl.tobzzo.miechowskiepowietrze.rest.v1.SensorMeasurementsResponseV1
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace

interface NetworkComponent {
  fun initialize()
  fun makeHttpRequest(url: String?, sensor: Sensor)
  fun restartLoading(forceRefresh: Boolean)
  fun getResponseMap(): MutableMap<SensorPlace, SensorMeasurementsResponseV1>?
  fun attachNetworkListener(listener: NetworkListener)
  fun detachNetworkListener(listener: NetworkListener)
}