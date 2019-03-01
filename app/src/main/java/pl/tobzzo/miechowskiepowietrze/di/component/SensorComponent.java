package pl.tobzzo.miechowskiepowietrze.di.component;

import dagger.Subcomponent;
import pl.tobzzo.miechowskiepowietrze.activities.SensorActivity;
import pl.tobzzo.miechowskiepowietrze.di.SensorScope;
import pl.tobzzo.miechowskiepowietrze.di.module.SensorModule;

@SensorScope
@Subcomponent(modules = SensorModule.class)
public interface SensorComponent {
  void inject(SensorActivity sensorActivity);
}
