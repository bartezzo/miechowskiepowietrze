package pl.tobzzo.miechowskiepowietrze.di.module;

import android.app.Activity;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import pl.tobzzo.miechowskiepowietrze.di.ActivityContext;

@Module
public class ActivityModule {
  private Activity activity;
  public ActivityModule(Activity activity){
    this.activity = activity;
  }

  @Provides
  @ActivityContext Context provideContext() {
    return activity;
  }
}
