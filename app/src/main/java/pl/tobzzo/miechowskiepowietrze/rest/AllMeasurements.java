package pl.tobzzo.miechowskiepowietrze.rest;


public class AllMeasurements {
    double airQualityIndex; //Common Air Quality Index (CAQI). http://www.airqualitynow.eu/about_indices_definition.php ,
    double humidity;
    double pm1;
    double pm10;
    double pm25;
    double pollutionLevel; //Pollution level based on CAQI value. Possible values: [0 to 6]. 0 - unknown, 1 - best air, 6 - worst ,
    double pressure;
    double temperature;

    public double getAirQualityIndex() {
        return airQualityIndex;
    }

    public void setAirQualityIndex(double airQualityIndex) {
        this.airQualityIndex = airQualityIndex;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPm1() {
        return pm1;
    }

    public void setPm1(double pm1) {
        this.pm1 = pm1;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public double getPollutionLevel() {
        return pollutionLevel;
    }

    public void setPollutionLevel(double pollutionLevel) {
        this.pollutionLevel = pollutionLevel;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[AllMeasurements]\n")
                .append("<airQualityIndex>\n").append(airQualityIndex).append("\n")
                .append("<humidity>\n").append(humidity).append("\n")
                .append("<pm1>\n").append(pm1).append("\n")
                .append("<pm10>\n").append(pm10).append("\n")
                .append("<pm25>\n").append(pm25).append("\n")
                .append("<pollutionLevel>\n").append(pollutionLevel).append("\n")
                .append("<pressure>\n").append(pressure).append("\n")
                .append("<temperature>\n").append(temperature).append("\n");

        return stringBuilder.toString();
    }
}
