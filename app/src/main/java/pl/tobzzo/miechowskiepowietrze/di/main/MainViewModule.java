package pl.tobzzo.miechowskiepowietrze.di.main;

import dagger.Binds;
import dagger.Module;
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainActivity;
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainContract;

@Module
public abstract class MainViewModule {
  @Binds
  abstract MainContract.MainView provideMainView(MainActivity mainActivity);
}
