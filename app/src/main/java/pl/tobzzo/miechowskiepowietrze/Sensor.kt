package pl.tobzzo.miechowskiepowietrze

data class Sensor(val id: Int, val name: String, val gpsLatitude: String, val gpsLongitude: String, val type: SensorMap.SensorType)
