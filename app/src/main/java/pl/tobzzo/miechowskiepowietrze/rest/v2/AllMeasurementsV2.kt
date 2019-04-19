package pl.tobzzo.miechowskiepowietrze.rest.v2

data class AllMeasurementsV2(
  val fromDateTime: String,
  val tillDateTime: String,
  val values: String,
  val indexes: String,
  val standards: String
){
  override fun toString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[AllMeasurementsV2]\n")
      .append("<fromDateTime>\n").append(fromDateTime).append("\n")
      .append("<tillDateTime>\n").append(tillDateTime).append("\n")
      .append("<values>\n").append(values).append("\n")
      .append("<indexes>\n").append(indexes).append("\n")
      .append("<standards>\n").append(standards).append("\n")

    return stringBuilder.toString()
  }
}