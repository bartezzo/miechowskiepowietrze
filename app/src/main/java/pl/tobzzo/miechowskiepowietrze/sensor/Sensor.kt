package pl.tobzzo.miechowskiepowietrze.sensor

import pl.tobzzo.miechowskiepowietrze.sensor.SensorMap.SensorType

data class Sensor(val id: Int, val name: String, val gpsLatitude: String, val gpsLongitude: String, val type: SensorType)
