package pl.tobzzo.miechowskiepowietrze.rest.v2

data class Measurements(
        val current: AveragedValues,
        val history: Array<AveragedValues>,
        val forecast: Array<AveragedValues>
)