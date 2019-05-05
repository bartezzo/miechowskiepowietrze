package pl.tobzzo.miechowskiepowietrze.connection

import android.content.Context
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import io.reactivex.Single
import org.json.JSONObject
import timber.log.Timber

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
    var resultGlobal: JsonObject

    Ion.with(context)
      .load(url)
      .addHeader("apikey", apiKey)
      .asJsonObject()
      .setCallback { exception, result ->
        resultGlobal = result
      }
  }
}