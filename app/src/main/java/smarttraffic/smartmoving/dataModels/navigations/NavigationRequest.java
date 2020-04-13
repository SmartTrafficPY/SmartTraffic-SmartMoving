package smarttraffic.smartmoving.dataModels.navigations;

import smarttraffic.smartmoving.dataModels.Point;

public class NavigationRequest {

    private String type = "Feature";
    private CreateNavigationRequest properties;
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

    public CreateNavigationRequest getProperties() {
        return properties;
    }

    public void setProperties(CreateNavigationRequest properties) {
        this.properties = properties;
    }

    public Point getGeometry() {
        return geometry;
    }

    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }
}
