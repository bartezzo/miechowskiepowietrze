package pl.tobzzo.miechowskiepowietrze.connection

import android.content.Context
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion

interface IonProvider {
  fun readSensorValues(url: String, apiKey: String,
    callback: Function2<Exception?, JsonObject, Unit>)
}