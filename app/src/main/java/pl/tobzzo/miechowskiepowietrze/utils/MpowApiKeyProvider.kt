package pl.tobzzo.miechowskiepowietrze.utils

import android.content.Context
import pl.tobzzo.miechowskiepowietrze.BuildConfig
import pl.tobzzo.miechowskiepowietrze.MpowApplication
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import timber.log.Timber
import javax.inject.Inject

class MpowApiKeyProvider(private val context: Context) : ApiKeyProvider{
  @Inject lateinit var analyticsComponent: AnalyticsComponent

  override fun initialize() {
    (context as MpowApplication).appComponent.inject(this)
  }

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