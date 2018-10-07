package pl.tobzzo.miechowskiepowietrze.rest;

import java.util.List;

public class SensorMeasurementsResponse {
    AllMeasurements currentMeasurements;
    List<MeasurementsTimeFramed> forecast;
    List<MeasurementsTimeFramed> history;

    public AllMeasurements getCurrentMeasurements() {
        return currentMeasurements;
    }

    public void setCurrentMeasurements(AllMeasurements currentMeasurements) {
        this.currentMeasurements = currentMeasurements;
    }

    public List<MeasurementsTimeFramed> getForecast() {
        return forecast;
    }

    public void setForecast(List<MeasurementsTimeFramed> forecast) {
        this.forecast = forecast;
    }

    public List<MeasurementsTimeFramed> getHistory() {
        return history;
    }

    public void setHistory(List<MeasurementsTimeFramed> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[SensorMeasurementsResponse]\n")
                .append("<currentMeasurements>\n").append(currentMeasurements.toString()).append("\n")
//                .append("<forecast>\n").append(forecast.toString()).append("\n")
//                .append("<history>\n").append(history.toString()).append("\n")
 ;

        return stringBuilder.toString();
    }
}
