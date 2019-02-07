package pl.tobzzo.miechowskiepowietrze.rest

data class MeasurementsTimeFramed(
  var fromDateTime: String,
  var measurements: AllMeasurements,
  var tillDateTime: String
){
}
