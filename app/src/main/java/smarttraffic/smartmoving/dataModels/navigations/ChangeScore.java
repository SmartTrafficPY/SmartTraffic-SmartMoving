package smarttraffic.smartmoving.dataModels.navigations;

public class ChangeScore {

    private Integer user_requested;
    private Boolean finished;
    private Integer score;


    @Override
    public String toString() {
        return "NavigationRequest{" +
                ", user_requested='" + user_requested + '\'' +
                ", finished='" + finished + '\'' +
                ", score='" + score + '\'' +
                '}';
    }


    public Integer getUser_requested() {
        return user_requested;
    }

    public void setUser_requested(Integer user_requested) {
        this.user_requested = user_requested;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
