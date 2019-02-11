package pl.tobzzo.miechowskiepowietrze.di.component;

import dagger.Component;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.activities.MainActivity;
import pl.tobzzo.miechowskiepowietrze.di.module.AppModule;

@Singleton
@Component (modules = { AppModule.class })
public interface AppComponent {
  void inject(MainActivity activity);
}
