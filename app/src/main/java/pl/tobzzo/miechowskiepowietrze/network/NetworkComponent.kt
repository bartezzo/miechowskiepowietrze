package pl.tobzzo.miechowskiepowietrze.network

import pl.tobzzo.miechowskiepowietrze.activities.MainActivity
import pl.tobzzo.miechowskiepowietrze.rest.SensorMeasurementsResponse
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace

interface NetworkComponent {
  fun initialize()
  fun makeHttpRequest(url: String?, sensor: Sensor)
  fun restartLoading(forceRefresh: Boolean)
  fun getResponseMap(): MutableMap<SensorPlace, SensorMeasurementsResponse>?
  fun attachNetworkListener(listener: NetworkListener)
  fun detachNetworkListener(listener: NetworkListener)
}