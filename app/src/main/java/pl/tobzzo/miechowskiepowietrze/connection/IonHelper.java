package pl.tobzzo.miechowskiepowietrze.connection;

import com.google.gson.JsonObject;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;


public class IonHelper {

  public IonHelper(){

  }

  public void getSmth(@NotNull String url, @NotNull String apiKey,
      @NotNull Function2<? super Exception, ? super JsonObject, Unit> function) {


  }
}
