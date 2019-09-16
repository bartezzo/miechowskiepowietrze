package pl.tobzzo.miechowskiepowietrze.app


import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import pl.tobzzo.miechowskiepowietrze.di.DaggerAppComponent

class MpowApplication : DaggerApplication() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.builder().application(this).build()
  }

  override fun onCreate() {
    super.onCreate()
  }

}
