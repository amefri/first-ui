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

    @Column(name = "rating")
    private String rating;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tours tour;

    public TourLog() {}

    public TourLog(String name, String date, String rating, String info) {
        this.name = name;
        this.date = date;
        this.rating = rating;
        this.info = info;

    }

    // Getter and Setter methods
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Tours getTour() {
        return tour;
    }

    public void setTour(Tours tour) {
        this.tour = tour;
    }

    // Transient properties for distance and duration
    public String getDistance() {
        return tour != null ? tour.getDistance() : null;
    }

    public String getDuration() {
        return tour != null ? tour.getEstimatedTime() : null;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty dateProperty() {
        return new SimpleStringProperty(date);
    }

    public StringProperty ratingProperty() {
        return new SimpleStringProperty(rating);
    }

    public StringProperty infoProperty() {
        return new SimpleStringProperty(info);
    }

    public StringProperty distanceProperty() {
        return new SimpleStringProperty(getDistance());
    }

    public StringProperty durationProperty() {
        return new SimpleStringProperty(getDuration());
    }
}
