package smarttraffic.smartmoving.dataModels.navigations;

import smarttraffic.smartmoving.dataModels.Point;

public class NavigationChangeScore {

    private String type = "Feature";
    private ChangeScore properties;
    private Point geometry = null;

    @Override
    public String toString() {
        return "NavigationRequests {" +
                "type='" + type + '\'' +
                ", properties=" + properties + '\'' +
                ", geometry=" + geometry + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChangeScore getProperties() {
        return properties;
    }

    public void setProperties(ChangeScore properties) {
        this.properties = properties;
    }

    public Point getGeometry() {
        return geometry;
    }

    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }
}
