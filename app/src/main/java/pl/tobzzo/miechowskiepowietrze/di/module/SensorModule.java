package pl.tobzzo.miechowskiepowietrze.di.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.database.DataBaseManager;
import pl.tobzzo.miechowskiepowietrze.database.MpowDataBaseManager;

@Module
public class SensorModule {
  @Provides
  //@Singleton
  public DataBaseManager provideDataBaseManager() { return new MpowDataBaseManager(); }
}
