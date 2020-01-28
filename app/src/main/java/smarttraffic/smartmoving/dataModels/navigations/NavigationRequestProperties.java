package smarttraffic.smartmoving.dataModels.navigations;

public class NavigationRequestProperties {
    private String url;
    private Integer user_requested;
    private Boolean finished;
    private String origin;
    private String destination;
    private Integer score;
    private String route;
    private String report_severe;
    private String report_light;


    @Override
    public String toString() {
        return "NavigationRequest{" +
                "url='" + url +
                ", user_requested='" + user_requested + '\'' +
                ", finished='" + finished + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", score='" + score + '\'' +
                ", route='" + route + '\'' +
                ", report_severe='" + report_severe + '\'' +
                ", report_light='" + report_light + '\'' +
                '}';
    }


    public String getUrl() {
        return url;
    }

    public Integer getUser_requested() {
        return user_requested;
    }

    public Boolean getFinished() {
        return finished;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getRoute() {
        return route;
    }
}
