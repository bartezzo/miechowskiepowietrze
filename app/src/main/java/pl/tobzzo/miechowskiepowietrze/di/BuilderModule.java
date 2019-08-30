package pl.tobzzo.miechowskiepowietrze.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pl.tobzzo.miechowskiepowietrze.di.sensor.SensorModule;
import pl.tobzzo.miechowskiepowietrze.di.sensor.SensorViewModule;
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainActivity;
import pl.tobzzo.miechowskiepowietrze.di.main.MainModule;
import pl.tobzzo.miechowskiepowietrze.di.main.MainViewModule;
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorActivity;

@Module
public abstract class BuilderModule {
  @ActivityScoped
  @ContributesAndroidInjector(modules = { MainViewModule.class, MainModule.class})
  abstract MainActivity bindMainActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = { SensorViewModule.class, SensorModule.class })
  abstract SensorActivity bindSensorActivity();
}
