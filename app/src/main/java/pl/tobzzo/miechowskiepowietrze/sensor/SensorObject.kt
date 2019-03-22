package pl.tobzzo.miechowskiepowietrze.sensor

interface SensorObject{
  var activeSensors: MutableList<SensorPlace>
  fun getSensor(sensorPlace: SensorPlace): Sensor?
}
