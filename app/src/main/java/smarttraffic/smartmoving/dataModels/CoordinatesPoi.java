package smarttraffic.smartmoving.dataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordinatesPoi {
    @SerializedName("latitud")
    @Expose
    private double latitud;
    @SerializedName("longitud")
    @Expose
    private double longitud;

    public CoordinatesPoi(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "CoordinatesPoi{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}