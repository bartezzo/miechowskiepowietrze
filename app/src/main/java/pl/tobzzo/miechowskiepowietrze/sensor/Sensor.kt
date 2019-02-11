package pl.tobzzo.miechowskiepowietrze.sensor

data class Sensor(val id: Int, val name: SensorPlace, val gpsLatitude: String, val gpsLongitude: String, val type: SensorType)
