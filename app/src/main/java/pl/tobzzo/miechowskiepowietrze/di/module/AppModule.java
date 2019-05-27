package pl.tobzzo.miechowskiepowietrze.di.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent;
import pl.tobzzo.miechowskiepowietrze.analytics.MpowAnalyticsComponent;
import pl.tobzzo.miechowskiepowietrze.connection.IonProvider;
import pl.tobzzo.miechowskiepowietrze.connection.MpowIonProvider;
import pl.tobzzo.miechowskiepowietrze.connection.MpowRetrofitProvider;
import pl.tobzzo.miechowskiepowietrze.connection.RetrofitProvider;
import pl.tobzzo.miechowskiepowietrze.logging.LoggingManager;
import pl.tobzzo.miechowskiepowietrze.logging.MpowLoggingManager;
import pl.tobzzo.miechowskiepowietrze.network.MpowNetworkComponent;
import pl.tobzzo.miechowskiepowietrze.network.NetworkComponent;
import pl.tobzzo.miechowskiepowietrze.sensor.MpowSensorObject;
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject;
import pl.tobzzo.miechowskiepowietrze.utils.ApiKeyProvider;
import pl.tobzzo.miechowskiepowietrze.utils.MpowApiKeyProvider;

@Module
public class AppModule {
  protected Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  public RetrofitProvider provideRetrofitProvider(ApiKeyProvider apiKeyProvider) {
    return new MpowRetrofitProvider(apiKeyProvider);
  }

  @Provides
  @Singleton
  public SensorObject provideSensorObject() {
    return new MpowSensorObject();
  }

  @Provides
  @Singleton
  public LoggingManager provideLoggingManager() { return new MpowLoggingManager(context); }

  @Provides
  @Singleton
  public NetworkComponent provideNetworkCompnent() {return new MpowNetworkComponent(context);}

  @Provides
  @Singleton
  public AnalyticsComponent provideAnalyticsComponent() {return new MpowAnalyticsComponent(context);}

  @Provides
  @Singleton
  public ApiKeyProvider provideApiKeyProvider(Context context) {
    return new MpowApiKeyProvider(context);
  }

}
