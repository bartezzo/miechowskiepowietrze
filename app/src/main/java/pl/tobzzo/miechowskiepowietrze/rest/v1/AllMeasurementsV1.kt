package pl.tobzzo.miechowskiepowietrze.rest.v1


data class AllMeasurementsV1(
  var airQualityIndex: Double = 0.toDouble(), //Common Air Quality Index (CAQI). http://www.airqualitynow.eu/about_indices_definition.php
  var humidity: Double = 0.toDouble(),
  var pm1: Double = 0.toDouble(),
  var pm10: Double = 0.toDouble(),
  var pm25: Double = 0.toDouble(),
  var pollutionLevel: Double = 0.toDouble(), //Pollution level based on CAQI value. Possible values: [0 to 6]. 0 - unknown, 1 - best air,
  // 6 - worst
  var pressure: Double = 0.toDouble(),
  var temperature: Double = 0.toDouble()
)
{

  override fun toString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[AllMeasurementsV1]\n")
      .append("<airQualityIndex>\n").append(airQualityIndex).append("\n")
      .append("<humidity>\n").append(humidity).append("\n")
      .append("<pm1>\n").append(pm1).append("\n")
      .append("<pm10>\n").append(pm10).append("\n")
      .append("<pm25>\n").append(pm25).append("\n")
      .append("<pollutionLevel>\n").append(pollutionLevel).append("\n")
      .append("<pressure>\n").append(pressure).append("\n")
      .append("<temperature>\n").append(temperature).append("\n")

    return stringBuilder.toString()
  }
}
