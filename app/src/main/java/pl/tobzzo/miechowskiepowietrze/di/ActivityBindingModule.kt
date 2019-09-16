package pl.tobzzo.miechowskiepowietrze.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainActivity
import pl.tobzzo.miechowskiepowietrze.mvp.splash.SplashActivity

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    internal abstract fun mainActivity(): MainActivity

}
