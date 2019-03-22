package pl.tobzzo.miechowskiepowietrze.rest.v1

data class MeasurementsTimeFramedV1(
  var fromDateTime: String,
  var measurementsV1: AllMeasurementsV1,
  var tillDateTime: String
){
}
