package smarttraffic.smartmoving.dataModels;


public class PropertiesOfEvents {

    private int application;
    private String e_type;
    private String agent;

    public PropertiesOfEvents() {
    }

    @Override
    public String toString() {
        return "EventProperties{" +
                "application=" + application +
                ", e_type='" + e_type + '\'' +
                ", agent='" + agent + '\'' +
                '}';
    }

    public int getApplication() {
        return application;
    }

    public void setApplication(int application) {
        this.application = application;
    }

    public String getE_type() {
        return e_type;
    }

    public void setE_type(String e_type) {
        this.e_type = e_type;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
}
