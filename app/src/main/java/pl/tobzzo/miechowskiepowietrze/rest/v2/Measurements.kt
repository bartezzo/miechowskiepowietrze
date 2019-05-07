package pl.tobzzo.miechowskiepowietrze.rest.v2

import com.google.gson.annotations.SerializedName

class Measurements(
  @SerializedName("current") val current: AveragedValues,
  @SerializedName("history") val history: Array<AveragedValues>,
  @SerializedName("forecast") val forecast: Array<AveragedValues>
)