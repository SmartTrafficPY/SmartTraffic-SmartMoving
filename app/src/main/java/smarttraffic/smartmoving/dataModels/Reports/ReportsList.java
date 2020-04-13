package smarttraffic.smartmoving.dataModels.Reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReportsList {

  private String type;
  private List<ReportPoi> features;

  public ReportsList() {
    // Persistence Constructor
  }

  @Override
  public String toString() {
    return "ReportsList{" +
            "type='" + type + '\'' +
            ", features=" + features + '\'' +
            '}';
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<ReportPoi> getFeatures() {
    return features;
  }

  public void setFeatures(List<ReportPoi> features) {
    this.features = features;
  }

  public boolean isEmpty() {
    if(getFeatures().isEmpty()){
      return true;
    }else{
      return false;
    }
  }
}