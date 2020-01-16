package smarttraffic.smartmoving.dataModels.navigations;

class CreateNavigationRequest {

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
        return "ReportsPoi{" +
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


}
