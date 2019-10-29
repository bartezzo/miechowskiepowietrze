package pl.tobzzo.miechowskiepowietrze.rest.v2

import pl.tobzzo.miechowskiepowietrze.sensor.Sensor

data class SensorMeasurements(val sensor: Sensor, val measurements: Measurements)