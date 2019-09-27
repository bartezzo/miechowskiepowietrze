package pl.tobzzo.miechowskiepowietrze.app


import dagger.android.AndroidInjector
import pl.tobzzo.miechowskiepowietrze.AnalyticsApplication
import pl.tobzzo.miechowskiepowietrze.di.DaggerAppComponent

class MpowApplication : AnalyticsApplication() {
  override fun applicationInjector(): AndroidInjector<out MpowApplication> {
    return DaggerAppComponent.builder().application(this).build()
  }
}
