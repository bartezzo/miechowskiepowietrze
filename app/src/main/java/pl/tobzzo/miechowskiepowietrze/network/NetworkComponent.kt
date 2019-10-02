package pl.tobzzo.miechowskiepowietrze.network

import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace

interface NetworkComponent {
  fun restartLoading(forceRefresh: Boolean = false)
  fun getResponseMap(): LinkedHashMap<Sensor, Measurements>?
  fun attachNetworkListener(listener: NetworkListener)
  fun detachNetworkListener(listener: NetworkListener)
}