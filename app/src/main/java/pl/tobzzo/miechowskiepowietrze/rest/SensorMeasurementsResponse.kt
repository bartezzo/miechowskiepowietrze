package pl.tobzzo.miechowskiepowietrze.rest

data class SensorMeasurementsResponse(
  var currentMeasurements: AllMeasurements,
  var forecast: List<MeasurementsTimeFramed>,
  var history: List<MeasurementsTimeFramed>
){
  override fun toString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[SensorMeasurementsResponse]\n")
      .append("<currentMeasurements>\n").append(currentMeasurements.toString()).append(
        "\n")//                .append("<forecast>\n").append(forecast.toString()).append("\n")
    //                .append("<history>\n").append(history.toString()).append("\n")

    return stringBuilder.toString()
  }
}
