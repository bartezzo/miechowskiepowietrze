package pl.tobzzo.miechowskiepowietrze.connection

import com.google.gson.JsonObject

interface IonProvider {
  fun readSensorValues(url: String, apiKey: String,
    callback: Function2<Exception?, JsonObject, Unit>)
}