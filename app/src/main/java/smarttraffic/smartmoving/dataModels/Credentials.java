package smarttraffic.smartmoving.dataModels;

public class Credentials {

    private String username;
    private String password;

    public Credentials(){
    }

    @Override
    public String toString(){
        return "Credentials{" +
                "usename='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String useremail) {
        this.username = useremail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
