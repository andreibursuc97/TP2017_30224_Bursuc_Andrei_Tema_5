import java.time.LocalDateTime;

/**
 * Created by Andrei on 14/05/2017.
 */
public class MonitoredData {


    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String activityLabel;

    public MonitoredData(LocalDateTime startTime, LocalDateTime endTime, String activityLabel) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityLabel = activityLabel;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public int getDayStartingTime(){
        return this.startTime.getDayOfMonth();
    }




    //DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //String[] parts = text.split("(?=\\+|\\-)");
}
