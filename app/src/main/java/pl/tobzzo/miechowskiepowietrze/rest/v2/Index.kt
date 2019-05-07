package pl.tobzzo.miechowskiepowietrze.rest.v2

import com.google.gson.annotations.SerializedName

class Index(
  @SerializedName("name") val name: String, // (string, optional): Name of this index,
  @SerializedName("value") val value: Number, // (number, optional): Index numerical value,
  @SerializedName("level") val level: String, // (string, optional): Index level name,
  @SerializedName("description") val description: String, // (string, optional) : Text describing this air quality level. Text translation is returned according to language specified in the request (English being default),
  @SerializedName("advice") val advice: String, // (string, optional) : Piece of advice from Airly regarding air quality. Text translation is returned according to language specified in the request (English being default),
  @SerializedName("color") val color: String // (string, optional): Color representing this index level, given by hexadecimal css-style triplet
)