package pl.tobzzo.miechowskiepowietrze;

import android.app.Activity;
import android.app.Application;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;
import pl.tobzzo.miechowskiepowietrze.di.DaggerAppComponent;

public class MpowApplication extends AnalyticsApplication implements HasActivityInjector {
  @Inject
  DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  //private AppComponent appComponent;
  //private SensorComponent sensorComponent;
  //private MainComponent mainComponent;

  @Override public void onCreate() {
    super.onCreate();

    //appComponent = createAppComponent();

    DaggerAppComponent
        .builder()
        .application(this)
        .build()
        .inject(this);
  }

  @Override public AndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }
  //
  //private AppComponent createAppComponent() {
  //  appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  //  return appComponent;
  //}
  //
  //public SensorComponent createSensorComponent() {
  //  sensorComponent = appComponent.plus(new SensorModule());
  //  return sensorComponent;
  //}
  //
  //public MainComponent createMainActivityComponent() {
  //  mainComponent = appComponent.plus(new MainModule());
  //  return mainComponent;
  //}
  //
  //public void releaseSensorComponent() {
  //  sensorComponent = null;
  //}
  //
  //public void releaseMainActivityComponent() {
  //  mainComponent = null;
  //}
  //
  //
  //public AppComponent getAppComponent() {
  //  return appComponent;
  //}
  //
  //public SensorComponent getSensorComponent() {
  //  return sensorComponent;
  //}
  //
  //public MainComponent getMainComponent() {
  //  return mainComponent;
  //}
}
