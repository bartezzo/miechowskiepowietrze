package pl.tobzzo.miechowskiepowietrze.di.sensor;

import dagger.Module;
import dagger.Provides;
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorContract;
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorPresenter;

@Module
public class SensorModule {
  @Provides SensorPresenter provideSensorPresenter(SensorContract.SensorView sensorView){
    return new SensorPresenter(sensorView);
  }

}
