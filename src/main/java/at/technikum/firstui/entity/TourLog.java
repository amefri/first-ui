package at.technikum.firstui.entity;

import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "t_tour_logs")
public class TourLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "duration")
    private String duration;

    @Column(name = "distance")
    private String distance;

    @ManyToOne
    private Tours tour;

    public TourLog() {
    }

    public TourLog(String name, String date, String duration, String distance) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.distance = distance;
    }

    public void setTourLog(Tours tour){
        this.tour = tour;
    }

    // Getter and Setter methods
    // (include getters and setters for all properties including tour)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Tours getTour() {
        return tour;
    }

    public void setTour(Tours tour) {
        this.tour = tour;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty dateProperty() {
        return new SimpleStringProperty(date);
    }

    public StringProperty durationProperty() {
        return new SimpleStringProperty(duration);
    }

    public StringProperty distanceProperty() {
        return new SimpleStringProperty(distance);
    }
}
