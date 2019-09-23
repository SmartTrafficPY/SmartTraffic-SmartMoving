package smarttraffic.smartmoving.dataModels;

public class SmartMovingProfile {

    private String birth_date;
    private String sex;
    private Integer type_movement;

    public SmartMovingProfile(){

    }

    @Override
    public String toString(){
      return "smartmovingprofile{" +
              "birth_date='" + birth_date + '\'' +
              ", sex='" + sex + '\'' +
              ", type_movement='" + type_movement + '\'' +
              '}';
    }
    public String getBirth_date(){return birth_date; }
    public void setBirth_date(String birth_date){ this.birth_date = birth_date; }

    public String getSex() {    return sex; }

    public void setSex(String sex) {this.sex = sex; }

    public Integer getTypemovement() {
        return type_movement;
    }

    public void setTypemovement(Integer type_movement) {
        this.type_movement = type_movement;
    }
}
