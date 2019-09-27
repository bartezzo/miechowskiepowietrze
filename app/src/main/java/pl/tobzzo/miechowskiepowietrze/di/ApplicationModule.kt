package pl.tobzzo.miechowskiepowietrze.di

import android.content.Context
import dagger.Binds
import dagger.Module
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
import pl.tobzzo.miechowskiepowietrze.analytics.MpowAnalyticsComponent
import pl.tobzzo.miechowskiepowietrze.app.MpowApplication
import pl.tobzzo.miechowskiepowietrze.connection.MpowRetrofitProvider
import pl.tobzzo.miechowskiepowietrze.connection.RetrofitProvider
import pl.tobzzo.miechowskiepowietrze.logging.LoggingManager
import pl.tobzzo.miechowskiepowietrze.logging.MpowLoggingManager
import pl.tobzzo.miechowskiepowietrze.network.MpowNetworkComponent
import pl.tobzzo.miechowskiepowietrze.network.NetworkComponent
import pl.tobzzo.miechowskiepowietrze.sensor.MpowSensorObject
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject
import pl.tobzzo.miechowskiepowietrze.utils.ApiKeyProvider
import pl.tobzzo.miechowskiepowietrze.utils.MpowApiKeyProvider

@Module
internal abstract class ApplicationModule {

  @Binds
  internal abstract fun bindContext(application: MpowApplication): Context

  @Binds
  internal abstract fun bindSensorObject(sensorObject: MpowSensorObject): SensorObject

  @Binds
  internal abstract fun bindLoggingManager(loggingManager: MpowLoggingManager): LoggingManager

  @Binds
  internal abstract fun bindAnalyticsComponent(analyticsComponent: MpowAnalyticsComponent): AnalyticsComponent

  @Binds
  internal abstract fun bindApiKeyProvider(apiKeyProvider: MpowApiKeyProvider): ApiKeyProvider

  @Binds
  internal abstract fun bindRetrofitProvider(retrofitProvider: MpowRetrofitProvider): RetrofitProvider

  @Binds
  internal abstract fun bindNetworkComponent(networkComponent: MpowNetworkComponent): NetworkComponent
}
