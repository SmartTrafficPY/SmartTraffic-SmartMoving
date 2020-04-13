package smarttraffic.smartmoving.dataModels;

public class UserToken {

    private String token;
    private String url;

    public UserToken(){
    }

    @Override
    public String toString(){
        return "UserToken{" +
                "token='" + token + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdFromUrl(){
        String[] parts = this.url.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }

}
