package smarttraffic.smartmoving.dataModels;


import smarttraffic.smartmoving.dataModels.PropertiesOfEvents;
/**
 * Created by Joaquin on 09/2019.
 * <p>
 * smarttraffic.smartparking.dataModels
 */

public class Events {

    private String type;
    private PropertiesOfEvents properties;
    private CoordinatesPoi geometry;

    public Events() {
    }

    @Override
    public String toString() {
        return "Events{" +
                "type='" + type + '\'' +
                ", properties=" + properties +
                ", geometry=" + geometry +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PropertiesOfEvents getProperties() {
        return properties;
    }

    public void setProperties(PropertiesOfEvents properties) {
        this.properties = properties;
    }

    public CoordinatesPoi getGeometry() {
        return geometry;
    }

    public void setGeometry(CoordinatesPoi geometry) {
        this.geometry = geometry;
    }
}
