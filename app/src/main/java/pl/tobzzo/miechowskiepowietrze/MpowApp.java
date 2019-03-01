package pl.tobzzo.miechowskiepowietrze;

import pl.tobzzo.miechowskiepowietrze.di.component.AppComponent;
import pl.tobzzo.miechowskiepowietrze.di.component.DaggerAppComponent;
import pl.tobzzo.miechowskiepowietrze.di.module.AppModule;

public class MpowApp extends AnalyticsApplication {

  private static MpowApp app;
  private AppModule appModule;
  private AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();
    app = this;

    appModule = new AppModule(this);
    appComponent = DaggerAppComponent.builder()
        .appModule(appModule)
        .build();
  }

  public static MpowApp getApp() {
    return app;
  }

  public AppModule getAppModule() {
    return appModule;
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}
