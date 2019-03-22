package pl.tobzzo.miechowskiepowietrze.rest.v1

data class SensorMeasurementsResponseV1(
  var currentMeasurementsV1: AllMeasurementsV1,
  var forecast: List<MeasurementsTimeFramedV1>,
  var history: List<MeasurementsTimeFramedV1>
){
  override fun toString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[SensorMeasurementsResponseV1]\n")
      .append("<currentMeasurementsV1>\n").append(currentMeasurementsV1.toString()).append(
        "\n")//                .append("<forecast>\n").append(forecast.toString()).append("\n")
    //                .append("<history>\n").append(history.toString()).append("\n")

    return stringBuilder.toString()
  }
}
