package pl.tobzzo.miechowskiepowietrze.di.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.tobzzo.miechowskiepowietrze.connection.IonHelper;

@Module
public class AppModule {
  private Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Singleton @Provides
  public Context provideContext() {
    return context;
  }

  @Singleton @Provides
  public IonHelper provideIonHelper(Context context){
    return new IonHelper(context);
  }
}
