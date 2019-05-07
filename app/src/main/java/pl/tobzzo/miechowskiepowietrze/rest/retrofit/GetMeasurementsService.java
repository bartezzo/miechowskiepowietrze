package pl.tobzzo.miechowskiepowietrze.rest.retrofit;

import pl.tobzzo.miechowskiepowietrze.BuildConfig;
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetMeasurementsService {
  //https://airapi.airly.eu/v2/measurements/point?indexType=AIRLY_CAQI&lat=50.062006&lng=19.940984
  //https://airapi.airly.eu/v2/measurements/point?indexType=AIRLY_CAQI&lat=50.062006&lng=19.940984
  //@GET("/v2/measurements/point?indexType=AIRLY_CAQI&lat=50.062006&lng=19.940984")
  @Headers("apikey:"+ BuildConfig.hiddenPassword1)
  @GET("/v2/measurements/point?")
  Call<Measurements> getAllMeasurements(@Query("indexType")String indexType, @Query("lat")String lat, @Query("lng")String lng);
}
