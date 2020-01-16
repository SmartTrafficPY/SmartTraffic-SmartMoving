package smarttraffic.smartmoving.dataModels.Reports;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import smarttraffic.smartmoving.dataModels.Point;

public class ReportPoi {
    private String type = "Feature";
    private ReportsPoi properties;
    private Point geometry;

    public ReportPoi() {
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

    public ReportsPoi getProperties() {
        return properties;
    }

    public void setProperties(ReportsPoi properties) {
        this.properties = properties;
    }

    public Point getGeometry() {
        return geometry;
    }

    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }
}
