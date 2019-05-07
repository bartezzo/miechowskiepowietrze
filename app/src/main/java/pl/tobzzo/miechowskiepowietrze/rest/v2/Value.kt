package pl.tobzzo.miechowskiepowietrze.rest.v2

import com.google.gson.annotations.SerializedName

class Value(
  @SerializedName("name") val name: String,
  @SerializedName("value") val value: Number
)