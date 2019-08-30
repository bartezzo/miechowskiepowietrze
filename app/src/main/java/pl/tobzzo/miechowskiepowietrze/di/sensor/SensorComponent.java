package pl.tobzzo.miechowskiepowietrze.di.sensor;

import dagger.Subcomponent;
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorActivity;

@Subcomponent(modules = SensorModule.class)
public interface SensorComponent {
  void inject(SensorActivity sensorActivity);
}
