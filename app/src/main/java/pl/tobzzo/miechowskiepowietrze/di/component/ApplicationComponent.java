package pl.tobzzo.miechowskiepowietrze.di.component;

import android.content.Context;
import dagger.Component;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.MpowApplication;
import pl.tobzzo.miechowskiepowietrze.di.ApplicationContext;
import pl.tobzzo.miechowskiepowietrze.di.module.ApplicationModule;

@Singleton
@Component(modules =  ApplicationModule.class)
public interface ApplicationComponent {
  void inject(MpowApplication demoApplication);

  @ApplicationContext Context getContext();
}
