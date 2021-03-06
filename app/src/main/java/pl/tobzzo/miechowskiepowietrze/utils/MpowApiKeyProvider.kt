package pl.tobzzo.miechowskiepowietrze.utils

import android.content.Context
import pl.tobzzo.miechowskiepowietrze.BuildConfig
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import timber.log.Timber
import javax.inject.Inject

class MpowApiKeyProvider @Inject constructor(val context: Context, val analyticsComponent: AnalyticsComponent) : ApiKeyProvider{

  override val apiKey: String
    get() {
      return (if (System.currentTimeMillis() % 2 == 0L)
        BuildConfig.hiddenPassword1
      else
        BuildConfig.hiddenPassword2
        ).also {
        analyticsComponent.logAction("logAction", "onCreate")
        Timber.d("api key:$it")
      }
    }
}