package smarttraffic.smartmoving.dataModels.navigations;

import java.util.ArrayList;

public class CreateNavigationRequest {

    private Integer user_requested;
    private Boolean finished;
    private String origin;
    private String destination;
    private Integer score;
    private ArrayList<String> route = new ArrayList<>();
    private String report_severe;
    private String report_light;


    @Override
    public String toString() {
        return "NavigationRequest{" +
                "user_requested='" + user_requested + '\'' +
                ", finished='" + finished + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", score='" + score + '\'' +
                ", route='" + route + '\'' +
                ", report_severe='" + report_severe + '\'' +
                ", report_light='" + report_light + '\'' +
                '}';
    }

    public Integer getUser_requested() {
        return user_requested;
    }

    public void setUser_requested(Integer user_requested) {
        this.user_requested = user_requested;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ArrayList<String> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<String> route) {
        this.route = route;
    }

    public String getReport_severe() {
        return report_severe;
    }

    public void setReport_severe(String report_severe) {
        this.report_severe = report_severe;
    }

    public String getReport_light() {
        return report_light;
    }

    public void setReport_light(String report_light) {
        this.report_light = report_light;
    }
}
