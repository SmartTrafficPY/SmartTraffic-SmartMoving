package smarttraffic.smartmoving.dataModels.Reports;

public class ReportsPoi {

    private String url;
    private Integer user_created;
    private Integer report_type;
    private String status;
    private Integer gid;
    private String modified;



    public ReportsPoi() {
    }

    @Override
    public String toString() {
        return "ReportsPoi{" +
                "url='" + url + '\'' +
                ", user_created='" + user_created + '\'' +
                ", report_type='" + report_type + '\'' +
                ", status='" + status + '\'' +
                ", gid='" + gid +'\'' +
                ", modified='" + modified + '\'' +
                '}';
    }


    public int getIdFromUrl() {
        String[] parts = this.url.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }

    public Integer getUsercreated() {
        return user_created;
    }

    public void setUsercreated(Integer usercreated) {
        this.user_created = usercreated;
    }

    public Integer getReport_type() {
        return report_type;
    }

    public void setReport_type(Integer report_type) {
        this.report_type = report_type;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getUrl() {
        return url;
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
}

