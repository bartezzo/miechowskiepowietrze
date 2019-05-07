package pl.tobzzo.miechowskiepowietrze.rest.v2

import com.google.gson.annotations.SerializedName

class Standard(
  @SerializedName("name") val name: String, // (string, optional): Name of this standard,
  @SerializedName("pollutant") val pollutant: String, // (string, optional): Pollutant described by this standard,
  @SerializedName("limit") val limit: Number, // (number, optional): Limit value of the pollutant,
  @SerializedName("percent") val percent: Number // (number, optional): Pollutant measurement as percent of allowable limit
)