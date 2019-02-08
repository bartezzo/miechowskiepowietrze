package pl.tobzzo.miechowskiepowietrze.di.component;

import dagger.Component;
import pl.tobzzo.miechowskiepowietrze.MainActivity;
import pl.tobzzo.miechowskiepowietrze.di.PerActivity;
import pl.tobzzo.miechowskiepowietrze.di.module.ActivityModule;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(MainActivity mainActivity);

}