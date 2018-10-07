package pl.tobzzo.miechowskiepowietrze;
 
public class Sensor {
    private int id;
    private String name;
    private String gpsLatitude;
    private String gpsLongitude;
    private SensorMap.SensorType sesnorType;

    public Sensor(int id, String name, String gpsLatitude, String gpsLongitude, SensorMap.SensorType sensorType) {
        this.id = id;
        this.name = name;
        this.gpsLatitude = gpsLatitude;
        this.gpsLongitude = gpsLongitude;
        this.sesnorType = sensorType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public SensorMap.SensorType getSesnorType() {
        return sesnorType;
    }
}
