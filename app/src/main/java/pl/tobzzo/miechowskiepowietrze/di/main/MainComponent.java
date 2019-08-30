package pl.tobzzo.miechowskiepowietrze.di.main;

import dagger.Subcomponent;
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainActivity;

@Subcomponent(modules = MainModule.class)
public interface MainComponent {
  void inject(MainActivity mainActivity);
}
