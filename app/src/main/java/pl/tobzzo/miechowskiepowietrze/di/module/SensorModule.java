package pl.tobzzo.miechowskiepowietrze.di.module;

import dagger.Module;
import dagger.Provides;
import pl.tobzzo.miechowskiepowietrze.database.DataBaseManager;
import pl.tobzzo.miechowskiepowietrze.database.MpowDataBaseManager;
import pl.tobzzo.miechowskiepowietrze.di.SensorScope;

@Module
public class SensorModule {
  @Provides
  @SensorScope
  public DataBaseManager provideDataBaseManager() { return new MpowDataBaseManager(); }
}
