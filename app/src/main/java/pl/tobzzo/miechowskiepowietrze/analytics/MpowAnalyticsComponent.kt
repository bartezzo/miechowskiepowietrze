package pl.tobzzo.miechowskiepowietrze.analytics

import android.content.Context
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import pl.tobzzo.miechowskiepowietrze.AnalyticsApplication
import javax.inject.Inject

class MpowAnalyticsComponent @Inject constructor(private val context: Context) : AnalyticsComponent{

  private var mTracker: Tracker? = null

  init {
    setGoogleAnalytics()
  }

  override fun logAction(category: String, action: String) {
    mTracker!!.send(HitBuilders.EventBuilder()
      .setCategory(category)
      .setAction(action)
      .build())  }

  override fun logScreen(screenName: String) {
    mTracker!!.setScreenName(screenName)
    mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
  }

  private fun setGoogleAnalytics() {
    // Obtain the shared Tracker instance.
    val application = context as AnalyticsApplication
    mTracker = application.defaultTracker
  }
}