package pl.tobzzo.miechowskiepowietrze.sensor

class MpowSensorObject : SensorObject{

  override val sensors: HashMap<SensorPlace, Sensor> = hashMapOf()

  override fun addSensor(sensor: Sensor) {
    sensors[sensor.name] = sensor
  }
}