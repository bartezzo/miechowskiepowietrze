package pl.tobzzo.miechowskiepowietrze.sensor;


import java.util.HashMap;
import java.util.Map;
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor;

public class SensorMap {
    public enum SensorType{REQ_MAP_POINT, REQ_SENSOR}

//    public static String MIECHOW_SREDNIA = "MIECHOW_SREDNIA";
    public static String MIECHOW_SIKORSKIEGO = "MIECHOW_SIKORSKIEGO";
    public static String MIECHOW_RYNEK = "MIECHOW_RYNEK";
    public static String MIECHOW_KOPERNIKA = "MIECHOW_KOPERNIKA";
    public static String MIECHOW_PARKOWE = "MIECHOW_PARKOWE";
    public static String MIECHOW_SZPITALNA = "MIECHOW_SZPITALNA";
    public static String MIECHOW_KROTKA = "MIECHOW_KROTKA";

//    public static Sensor SENSOR_MIECHOW_SREDNIA = new Sensor(-1, MIECHOW_SREDNIA, "50.356486", "20.027900", SensorType.REQ_MAP_POINT);
    public static Sensor SENSOR_MIECHOW_SIKORSKIEGO = new Sensor(336, MIECHOW_SIKORSKIEGO, "50.352787", "20.019735", SensorType.REQ_MAP_POINT);
    public static Sensor SENSOR_MIECHOW_RYNEK = new Sensor(340, MIECHOW_RYNEK, "50.3568", "20.028696", SensorType.REQ_MAP_POINT);
    public static Sensor SENSOR_MIECHOW_KOPERNIKA = new Sensor(341, MIECHOW_KOPERNIKA, "50.360233", "20.026752", SensorType.REQ_MAP_POINT);
    public static Sensor SENSOR_MIECHOW_PARKOWE = new Sensor(342, MIECHOW_PARKOWE, "50.359778999999996", "20.040627999999998", SensorType.REQ_MAP_POINT);
    public static Sensor SENSOR_MIECHOW_SZPITALNA = new Sensor(343, MIECHOW_SZPITALNA, "50.355094", "20.035085", SensorType.REQ_MAP_POINT);
    public static Sensor SENSOR_MIECHOW_KROTKA = new Sensor(344, MIECHOW_KROTKA, "50.355613", "20.013966999999997", SensorType.REQ_MAP_POINT);

    public static Map<String, Sensor> sensors;
    static {
        sensors = new HashMap<>();
//        sensors.put(MIECHOW_SREDNIA , SENSOR_MIECHOW_SREDNIA);
        sensors.put(MIECHOW_SIKORSKIEGO , SENSOR_MIECHOW_SIKORSKIEGO);
        sensors.put(MIECHOW_RYNEK, SENSOR_MIECHOW_RYNEK);
        sensors.put(MIECHOW_KOPERNIKA, SENSOR_MIECHOW_KOPERNIKA);
        sensors.put(MIECHOW_PARKOWE, SENSOR_MIECHOW_PARKOWE);
        sensors.put(MIECHOW_SZPITALNA, SENSOR_MIECHOW_SZPITALNA);
        sensors.put(MIECHOW_KROTKA, SENSOR_MIECHOW_KROTKA);
    }

    public static Map<String, Sensor> getSensors(){
        return sensors;
    }
}
