package pl.tobzzo.miechowskiepowietrze.rest.v2

data class Standard(
        private val name: String, // (string, optional): Name of this standard,
        private val pollutant: String, // (string, optional): Pollutant described by this standard,
        private val limit: Number, // (number, optional): Limit value of the pollutant,
        private val percent: Number // (number, optional): Pollutant measurement as percent of allowable limit
)