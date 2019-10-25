package pl.tobzzo.miechowskiepowietrze.rest.retrofit;

import io.reactivex.Observable;
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetMeasurementsService {
  @GET("/v2/measurements/point?") Observable<Call<Measurements>> getAllMeasurements(@Header("apikey") String apiKey, @Query("indexType") String indexType, @Query("lat")
      String lat, @Query("lng") String lng);
}
