package pl.tobzzo.miechowskiepowietrze.di.module;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import pl.tobzzo.miechowskiepowietrze.di.ApplicationContext;

@Module
public class ApplicationModule {
  private final Application application;

  public ApplicationModule(Application application){
    this.application = application;
  }

  @Provides
  @ApplicationContext Context provideContext(){
    return application;
  }
}
