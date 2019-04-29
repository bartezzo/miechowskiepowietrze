package pl.tobzzo.miechowskiepowietrze.network

import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace

interface NetworkComponent {
  fun initialize()
  fun makeHttpRequest(url: String?, sensor: Sensor)
  fun restartLoading(forceRefresh: Boolean)
  fun getResponseMap(): MutableMap<SensorPlace, Measurements>?
  fun attachNetworkListener(listener: NetworkListener)
  fun detachNetworkListener(listener: NetworkListener)
}