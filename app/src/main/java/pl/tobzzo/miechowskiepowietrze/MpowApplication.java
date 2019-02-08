package pl.tobzzo.miechowskiepowietrze;

import android.content.Context;
import pl.tobzzo.miechowskiepowietrze.di.component.ApplicationComponent;
import pl.tobzzo.miechowskiepowietrze.di.component.DaggerApplicationComponent;
import pl.tobzzo.miechowskiepowietrze.di.module.ApplicationModule;

public class MpowApplication extends AnalyticsApplication {
  protected ApplicationComponent applicationComponent;

  public static MpowApplication get(Context context){
    return (MpowApplication) context.getApplicationContext();
  }

  @Override public void onCreate() {
    super.onCreate();
    applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(new ApplicationModule(this))
        .build();

    applicationComponent.inject(this);
  }

  public ApplicationComponent getComponent(){
    return applicationComponent;
  }
}
