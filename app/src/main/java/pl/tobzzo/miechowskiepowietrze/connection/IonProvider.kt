package pl.tobzzo.miechowskiepowietrze.connection

import com.google.gson.JsonObject
import io.reactivex.Single

interface IonProvider {
//  fun readSensorValues(
//    url: String,
//    apiKey: String,
//    callback: (Exception?, JsonObject) -> Unit
//  )
  fun readSensorValues(
    url: String,
    apiKey: String
  ) : Single<JsonObject>
}