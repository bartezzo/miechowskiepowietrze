package pl.tobzzo.miechowskiepowietrze.rest.v2

data class Standard(
  val name: String, // (string, optional): Name of this standard,
  val pollutant: String, // (string, optional): Pollutant described by this standard,
  val limit: Number, // (number, optional): Limit value of the pollutant,
  val percent: Number // (number, optional): Pollutant measurement as percent of allowable limit
)