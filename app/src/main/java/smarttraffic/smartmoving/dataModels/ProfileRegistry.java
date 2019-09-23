package smarttraffic.smartmoving.dataModels;

public class ProfileRegistry {

    private String username;
    private String password;
    private SmartMovingProfile smartmovingprofile;


    @Override
    public String toString() {
        return "ProfileRegistry{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", smartmovingprofile=" + smartmovingprofile +
                '}';
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SmartMovingProfile getSmartMovingProfile() { return smartmovingprofile;}

    public void setSmartMovingProfile(SmartMovingProfile smartmovingprofile){
        this.smartmovingprofile = smartmovingprofile;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
