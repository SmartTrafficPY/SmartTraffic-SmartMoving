package smarttraffic.smartmoving.dataModels.Reports;

import smarttraffic.smartmoving.dataModels.Point;

public class Properties {

    private String type = "Feature";
    private CreateReportPoi properties;
    private Point geometry;

    public Properties() {
        // Persistence Constructor
    }

    @Override
    public String toString() {
        return "ReportPoi{" +
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

    public CreateReportPoi getProperties() {
        return properties;
    }

    public void setProperties(CreateReportPoi properties) {
        this.properties = properties;
    }

    public Point getGeometry() {
        return geometry;
    }

    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }
}
