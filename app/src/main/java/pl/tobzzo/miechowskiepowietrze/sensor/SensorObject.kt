package pl.tobzzo.miechowskiepowietrze.sensor

interface SensorObject{
  fun addSensor(sensor: Sensor)

  val sensors: HashMap<SensorPlace, Sensor>
}
