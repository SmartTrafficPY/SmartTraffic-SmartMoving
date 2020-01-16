package smarttraffic.smartmoving.dataModels;

public class ProfileRegistry {

    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private SmartMovingProfile smartmovingprofile;


    @Override
    public String toString() {
        return "ProfileRegistry{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + first_name + '\'' +
                ", lastname='" + last_name + '\'' +
                ", email='" + email + '\'' +
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

    public String getFirstname() {
        return first_name;
    }

    public void setFirstname(String first_name) {
        this.first_name = first_name;
    }

    public String getLastname() {
        return last_name;
    }

    public void setLastname(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
