package smarttraffic.smartmoving.dataModels.Reports;

public class CreateContributionPoi {
    private Integer reportid;
    private Integer user;
    private Boolean value;

    @Override
    public String toString() {
        return "ReportsPoi{" +
                ", reportid='" + reportid + '\'' +
                ", user='" + user + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
