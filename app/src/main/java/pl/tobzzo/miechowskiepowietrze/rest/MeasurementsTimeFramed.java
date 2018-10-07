package pl.tobzzo.miechowskiepowietrze.rest;

public class MeasurementsTimeFramed {
    String fromDateTime;
    AllMeasurements measurements;
    String tillDateTime;

    public String getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public AllMeasurements getMeasurements() {
        return measurements;
    }

    public void setMeasurements(AllMeasurements measurements) {
        this.measurements = measurements;
    }

    public String getTillDateTime() {
        return tillDateTime;
    }

    public void setTillDateTime(String tillDateTime) {
        this.tillDateTime = tillDateTime;
    }
}
