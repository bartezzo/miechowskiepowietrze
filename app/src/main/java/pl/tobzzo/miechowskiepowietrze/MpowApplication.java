package pl.tobzzo.miechowskiepowietrze;

import pl.tobzzo.miechowskiepowietrze.di.component.AppComponent;
import pl.tobzzo.miechowskiepowietrze.di.component.DaggerAppComponent;
import pl.tobzzo.miechowskiepowietrze.di.component.SensorComponent;
import pl.tobzzo.miechowskiepowietrze.di.module.AppModule;
import pl.tobzzo.miechowskiepowietrze.di.module.SensorModule;

public class MpowApplication extends AnalyticsApplication {
  private AppComponent appComponent;
  private SensorComponent sensorComponent;

  @Override public void onCreate() {
    super.onCreate();

    appComponent = createAppComponent();
  }

  private AppComponent createAppComponent() {
    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    return appComponent;
  }

  public SensorComponent createSensorComponent() {
    sensorComponent = appComponent.plus(new SensorModule());
    return sensorComponent;
  }

  public void releaseSensorComponent() {
    sensorComponent = null;
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

  public SensorComponent getSensorComponent() {
    return sensorComponent;
  }
}
