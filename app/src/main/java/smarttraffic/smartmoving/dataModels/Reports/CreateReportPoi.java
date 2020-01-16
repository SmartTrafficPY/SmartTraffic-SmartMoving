package smarttraffic.smartmoving.dataModels.Reports;

public class CreateReportPoi {
        private Integer user_created;
        private Integer report_type;
        private String status;
        private Integer gid;


    @Override
    public String toString() {
        return "ReportsPoi{" +
                ", user_created='" + user_created + '\'' +
                ", report_type='" + report_type + '\'' +
                ", status='" + status + '\'' +
                ", gid='" + gid + '\'' +
                '}';
    }

    public Integer getUser_created() {
        return user_created;
    }

    public void setUser_created(Integer user_created) {
        this.user_created = user_created;
    }

    public Integer getReport_type() {
        return report_type;
    }

    public void setReport_type(Integer report_type) {
        this.report_type = report_type;
    }

    public String getStatus() {
        return status;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
