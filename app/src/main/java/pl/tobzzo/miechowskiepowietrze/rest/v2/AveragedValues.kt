package pl.tobzzo.miechowskiepowietrze.rest.v2

data class AveragedValues(
        val fromDateTime: String, // (string, optional): Left bound of the time period over which average measurements were calculated, inclusive, always UTC,
        val tillDateTime: String, // (string, optional): Right bound of the time period over which average measurements were calculated, exclusive, always UTC,
        val values: Array<Value>, // (array [Value], optional): List of raw measurements, averaged over specified period. Measurement types available in this list depend on the capabilities of the queried installation, e.g. particulate matter (PM1, PM25, PM10), gases (CO, NO2, SO2, O3) or weather conditions (temperature, humidity, pressure),
        val indexes: Array<Index>, // (array [Index], optional): List of indexes calculated from the values available. Indexes are defined by relevant national and international institutions, e.g. EU, GIOŚ or US EPA,
        val standards: Array<Standard> // (array [Standard], optional): List of 'standard' values, or 'limits' for pollutants that should not be exceeded over certain period of time. Limits are defined by relevant national and international institutions, like e.g. WHO or EPA. For each standard limit in this list there is also a corresponding measurement expressed as a percent value of the limit
)