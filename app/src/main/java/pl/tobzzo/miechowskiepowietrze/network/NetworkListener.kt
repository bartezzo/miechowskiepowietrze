package pl.tobzzo.miechowskiepowietrze.network

interface NetworkListener{
  fun onValuesLoading()
  fun onValuesAvailable()
}