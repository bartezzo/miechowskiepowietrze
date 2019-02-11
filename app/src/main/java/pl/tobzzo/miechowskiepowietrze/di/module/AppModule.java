package pl.tobzzo.miechowskiepowietrze.di.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.connection.IonHelper;
import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject;

@Module
public class AppModule {
  private Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Singleton @Provides
  public Context provideContext() {
    return context;
  }

  @Singleton @Provides
  public IonHelper provideIonHelper(Context context){
    return new IonHelper(context);
  }

  @Singleton @Provides
  public SensorObject provideSensorObject(){
    return new SensorObject();
  }
}
