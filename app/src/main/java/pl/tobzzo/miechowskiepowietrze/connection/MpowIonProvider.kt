package pl.tobzzo.miechowskiepowietrze.connection

import android.content.Context
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion

class MpowIonProvider(private val context: Context) : IonProvider {
  override fun readSensorValues(url: String, apiKey: String, callback: (Exception?, JsonObject) -> Unit) {
    Ion.with(context)
      .load(url)
      .addHeader("apikey", apiKey)
      .asJsonObject()
      .setCallback { e, result ->
        callback(e, result)
      }
  }
}