package pl.tobzzo.miechowskiepowietrze.di.component;

import dagger.Component;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.activities.MainActivity;
import pl.tobzzo.miechowskiepowietrze.di.module.AppModule;
import pl.tobzzo.miechowskiepowietrze.di.module.SensorModule;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
  SensorComponent plus(SensorModule sensorModule);
  void inject(MainActivity activity);
}
