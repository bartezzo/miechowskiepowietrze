package pl.tobzzo.miechowskiepowietrze.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import pl.tobzzo.miechowskiepowietrze.app.MpowApplication
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class
        ))

interface AppComponent : AndroidInjector<MpowApplication> {

    override fun inject(instance: MpowApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MpowApplication): Builder

        fun build(): AppComponent
    }
}
