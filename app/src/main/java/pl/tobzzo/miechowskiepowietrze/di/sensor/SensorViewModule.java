package pl.tobzzo.miechowskiepowietrze.di.sensor;

import dagger.Binds;
import dagger.Module;
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorActivity;
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorContract;

@Module
public abstract class SensorViewModule {
  @Binds
  abstract SensorContract.SensorView provideSensorView(SensorActivity sensorActivity);
}
