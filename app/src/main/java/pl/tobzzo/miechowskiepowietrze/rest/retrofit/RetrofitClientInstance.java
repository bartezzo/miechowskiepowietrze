package pl.tobzzo.miechowskiepowietrze.rest.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
  private static Retrofit retrofit;
  private static final String BASE_URL = "https://airapi.airly.eu/";

  public static Retrofit getRetrofitInstance() {
    if (retrofit == null) {
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

      retrofit =
          new retrofit2.Retrofit.Builder().baseUrl(BASE_URL).client(client)
              .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
              .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofit;
  }
}
