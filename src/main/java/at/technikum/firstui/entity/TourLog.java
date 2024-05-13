package at.technikum.firstui.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class
TourLog {

    private final StringProperty name;
    private final StringProperty date;
    private final StringProperty duration;
    private final StringProperty distance;

    public TourLog(String name, String date, String duration, String distance) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.duration = new SimpleStringProperty(duration);
        this.distance = new SimpleStringProperty(distance);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDuration() {
        return duration.get();
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public String getDistance() {
        return distance.get();
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }
}
