package pl.tobzzo.miechowskiepowietrze.connection

import android.content.Context
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion


class IonHelper(private val context: Context) {

  fun getSmth(url: String, apiKey: String,
    callback: Function2<Exception?, JsonObject, Unit>) {
    Ion.with(context)
      .load(url)
      .addHeader("apikey", apiKey)
      .asJsonObject()
      .setCallback { e, result ->
        callback(e, result)
      }
  }
}
