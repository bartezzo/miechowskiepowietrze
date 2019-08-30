package pl.tobzzo.miechowskiepowietrze.di.main;

import dagger.Module;
import dagger.Provides;
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainContract;
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainPresenter;

@Module
public class MainModule {
  @Provides MainPresenter provideMainPresenter(MainContract.MainView mainView) {
    return new MainPresenter(mainView);
  }
}
