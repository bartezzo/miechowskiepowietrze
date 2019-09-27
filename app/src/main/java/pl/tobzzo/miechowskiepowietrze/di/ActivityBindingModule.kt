package pl.tobzzo.miechowskiepowietrze.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainActivity
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorActivity
import pl.tobzzo.miechowskiepowietrze.mvp.splash.SplashActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(SensorModule::class))
    internal abstract fun bindSensorActivity(): SensorActivity

}
