package pl.tobzzo.miechowskiepowietrze.rest.v2

data class SensorMeasurementsResponseV2(
  var current: AllMeasurementsV2,
  var history: List<AllMeasurementsV2>,
  var forecast: List<AllMeasurementsV2>
) {
  override fun toString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[SensorMeasurementsResponseV2]\n")
      .append("<current>\n").append(current.toString()).append("\n")
      .append("<forecast>\n").append(forecast.toString()).append("\n")
      .append("<history>\n").append(history.toString()).append("\n")

    return stringBuilder.toString()
  }
}