package smarttraffic.smartmoving.dataModels;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Point {

  private String type="Point" ;
  private List<Double> coordinates;

  public Point(){
  }



  @Override
  public String toString() {
    return "Geometry{" +
            "type='" + type + '\'' +
            ", coordinates=" + coordinates + '\'' +
            '}';
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<Double> getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(List<Double> coordinates) {
    this.coordinates = coordinates;
  }

  public CoordinatesPoi getPointCoordinates(){
    if(getCoordinates() != null){
      CoordinatesPoi poiPoint = new CoordinatesPoi(getCoordinates().get(1), getCoordinates().get(0));
      return poiPoint;
    }
    return null;
  }



}