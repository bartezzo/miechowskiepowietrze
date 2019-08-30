package pl.tobzzo.miechowskiepowietrze.di;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.MpowApplication;
//import pl.tobzzo.miechowskiepowietrze.activities.MainActivityWithCharts;
//import pl.tobzzo.miechowskiepowietrze.analytics.MpowAnalyticsComponent;
//import pl.tobzzo.miechowskiepowietrze.network.MpowNetworkComponent;
//import pl.tobzzo.miechowskiepowietrze.utils.MpowApiKeyProvider;

@Singleton
@Component(modules = { AndroidSupportInjectionModule.class, AppModule.class, BuilderModule.class })
public interface AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder application(MpowApplication application);
    AppComponent build();
  }

  void inject(MpowApplication app);

  //MainComponent plus(MainModule mainModule);
  //
  //SensorComponent plus(SensorModule sensorModule);

  //void inject(MpowNetworkComponent mpowNetworkComponent);
  //
  //void inject(MpowAnalyticsComponent mpowAnalyticsComponent);
  //
  //void inject(MainActivityWithCharts mainActivityWithCharts);
  //
  //void inject(MpowApiKeyProvider mpowApiKeyProvider);
}
