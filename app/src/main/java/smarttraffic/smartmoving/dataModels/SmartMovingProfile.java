package smarttraffic.smartmoving.dataModels;

public class SmartMovingProfile {

    private String birth_date;
    private String sex;
    private Integer movement_type;

    public SmartMovingProfile(){

    }

    @Override
    public String toString(){
        return "smartmovingprofile{" +
                "birth_date='" + birth_date + '\'' +
                ", sex='" + sex + '\'' +
                ",movement_type='" + movement_type + '\'' +
                '}';
    }
    public String getBirth_date(){return birth_date; }
    public void setBirth_date(String birth_date){ this.birth_date = birth_date; }

    public String getSex() {    return sex; }

    public void setSex(String sex) {this.sex = sex; }

    public Integer getTypemovement() {
        return movement_type;
    }

    public void setTypemovement(Integer movement_type) {
        this.movement_type = movement_type;
    }
}