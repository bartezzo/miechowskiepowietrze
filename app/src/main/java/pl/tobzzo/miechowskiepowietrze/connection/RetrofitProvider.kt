package pl.tobzzo.miechowskiepowietrze.connection

interface RetrofitProvider {
  fun readSensorValues(
    url: String,
    apiKey: String
  )
}