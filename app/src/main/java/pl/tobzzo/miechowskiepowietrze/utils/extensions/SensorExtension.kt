package pl.tobzzo.miechowskiepowietrze.utils.extensions

import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorType.REQ_MAP_POINT
import pl.tobzzo.miechowskiepowietrze.sensor.SensorType.REQ_SENSOR
import java.lang.IllegalArgumentException


const val URL_REQ_MAP_POINT = "https://airapi.airly.eu/v1/mapPoint/measurements?latitude=%1\$s&longitude=%2\$s"
const val URL_REQ_SENSOR = "https://airapi.airly.eu/v1/sensor/measurements?sensorId=%1\$s"

inline fun Sensor.mapToUrl(): String? = when {
  this.type == REQ_MAP_POINT -> String.format(URL_REQ_MAP_POINT, this.gpsLatitude, this.gpsLongitude)
  this.type == REQ_SENSOR -> String.format(URL_REQ_SENSOR, this.id)
  else -> throw IllegalArgumentException("Wrong sensor type:${this.type}")
}