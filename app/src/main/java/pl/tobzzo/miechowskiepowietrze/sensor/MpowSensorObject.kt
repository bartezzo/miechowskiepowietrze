package pl.tobzzo.miechowskiepowietrze.sensor

import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_KOPERNIKA
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_KROTKA
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_PARKOWE
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_RYNEK
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SIKORSKIEGO
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SREDNIA
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SZPITALNA
import javax.inject.Inject

class MpowSensorObject @Inject constructor() : SensorObject {
  override var activeSensors = mutableListOf<SensorPlace>()

  override fun getSensor(sensorPlace: SensorPlace): Sensor? = sensors.find { sensorPlace == it.place }

  private val sensors = mutableListOf<Sensor>()
  private var SENSOR_MIECHOW_SREDNIA = Sensor(-1, MIECHOW_SREDNIA, "średnio", "50.356486", "20.027900", SensorType.REQ_MAP_POINT)
  private var SENSOR_MIECHOW_SIKORSKIEGO = Sensor(336, MIECHOW_SIKORSKIEGO, "Sikorskiego", "50.352787", "20.019735", SensorType.REQ_MAP_POINT)
  private var SENSOR_MIECHOW_RYNEK = Sensor(340, MIECHOW_RYNEK, "Rynek", "50.3568", "20.028696", SensorType.REQ_MAP_POINT)
  private var SENSOR_MIECHOW_KOPERNIKA = Sensor(341, MIECHOW_KOPERNIKA, "Kopernika", "50.360233", "20.026752", SensorType.REQ_MAP_POINT)
  private var SENSOR_MIECHOW_PARKOWE = Sensor(342, MIECHOW_PARKOWE, "Parkowe", "50.359778999999996", "20.040627999999998", SensorType.REQ_MAP_POINT)
  private var SENSOR_MIECHOW_SZPITALNA = Sensor(343, MIECHOW_SZPITALNA, "Szpitalna", "50.355094", "20.035085", SensorType.REQ_MAP_POINT)
  private var SENSOR_MIECHOW_KROTKA = Sensor(344, MIECHOW_KROTKA, "Krótka", "50.355613", "20.013966999999997", SensorType.REQ_MAP_POINT)


  init {
    addSensor(SENSOR_MIECHOW_SIKORSKIEGO)
    addSensor(SENSOR_MIECHOW_RYNEK)
    addSensor(SENSOR_MIECHOW_KOPERNIKA)
    addSensor(SENSOR_MIECHOW_PARKOWE)
    addSensor(SENSOR_MIECHOW_SZPITALNA)
    addSensor(SENSOR_MIECHOW_KROTKA)
  }

  private fun addSensor(sensor: Sensor) {
    sensors.add(sensor)
    activeSensors.add(sensor.place)
  }

}