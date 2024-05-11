package at.technikum.firstui.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class TourLog {


    @JsonProperty("name")
    private String name;
    @JsonProperty("date")
    private String date;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("distance")
    private String distance;


        public TourLog(String name, String date, String duration, String distance){
            this.name = name;
            this.date = date;
            this.duration = duration;
            this.distance = distance;
        }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public String getDuration() {
        return duration;
    }
    public String getDistance() {
        return distance;
    }

    public List<Object> getFieldsAsList() {
        return Arrays.asList(name, date, duration, distance);
    }
}
