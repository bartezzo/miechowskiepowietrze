package pl.tobzzo.miechowskiepowietrze.rest.v2

data class Index(
  val name: String, // (string, optional): Name of this index,
  val value: Number, // (number, optional): Index numerical value,
  val level: String, // (string, optional): Index level name,
  val description: String, // (string, optional) : Text describing this air quality level. Text translation is returned according to language specified in the request (English being default),
  val advice: String, // (string, optional) : Piece of advice from Airly regarding air quality. Text translation is returned according to language specified in the request (English being default),
  val color: String // (string, optional): Color representing this index level, given by hexadecimal css-style triplet
)