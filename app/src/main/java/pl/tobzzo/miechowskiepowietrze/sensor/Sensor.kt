package pl.tobzzo.miechowskiepowietrze.sensor

data class Sensor(val id: Int, val place: SensorPlace, val gpsLatitude: String, val gpsLongitude: String, val type: SensorType)
