package smarttraffic.smartmoving.dataModels;

public class ProfileUser {

    private String url;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private SmartMovingProfile smartmovingprofile;

    public ProfileUser(){
    }

    @Override
    public String toString(){
        return "ProfileUser{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", smartmovingprofile=" + smartmovingprofile +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUseremail() {
        return username;
    }

    public void setUseremail(String useremail) {
        this.username = useremail;
    }

    public SmartMovingProfile getSmartmovingprofile() {
        return smartmovingprofile;
    }

    public void setSmartmovingprofile(SmartMovingProfile smartmovingprofile) {
        this.smartmovingprofile = smartmovingprofile;
    }
}
