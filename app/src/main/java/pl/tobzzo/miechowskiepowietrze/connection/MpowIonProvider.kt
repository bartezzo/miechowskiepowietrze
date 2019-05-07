package pl.tobzzo.miechowskiepowietrze.connection

import android.content.Context
import com.google.gson.JsonObject
import io.reactivex.Single

class MpowIonProvider(private val context: Context) : IonProvider {
//  override fun readSensorValues(url: String, apiKey: String, callback: (Exception?, JsonObject) -> Unit) {
//    Ion.with(context)
//      .load(url)
//      .addHeader("apikey", apiKey)
//      .asJsonObject()
//      .setCallback { exception, result ->
//        callback(exception, result)
//      }
//  }

  override fun readSensorValues(url: String, apiKey: String) : Single<JsonObject> {


//    val future = Ion.with(context)
//      .load(url)
//      .addHeader("apikey", apiKey)
//      .asJsonObject()
//      .setCallback { exception, result ->
//
//      }

    return Single.just(null)
  }
}