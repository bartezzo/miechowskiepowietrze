package pl.tobzzo.miechowskiepowietrze.di.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.connection.IonProvider;
import pl.tobzzo.miechowskiepowietrze.connection.MpowIonProvider;
import pl.tobzzo.miechowskiepowietrze.logging.LoggingManager;
import pl.tobzzo.miechowskiepowietrze.logging.MpowLoggingManager;
import pl.tobzzo.miechowskiepowietrze.sensor.MpowSensorObject;
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject;

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
  public IonProvider provideIonProvider(Context context) {
    return new MpowIonProvider(context);
  }

  @Provides
  @Singleton
  public SensorObject provideSensorObject() {
    return new MpowSensorObject();
  }

  @Provides
  @Singleton
  public LoggingManager provideLoggingManager() { return new MpowLoggingManager(context); }

}
