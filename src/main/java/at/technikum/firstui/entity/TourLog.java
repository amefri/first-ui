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
    @JoinColumn(name = "tour_id", nullable = true)
    private Tours tour;

    public TourLog() {
    }

    public TourLog(String name, String date, String rating, String info) {
        this.name = name;
        this.date = date;
        this.rating = rating;
        this.info = info;
    }

    // Getter and Setter methods
    // (include getters and setters for all properties including tour)

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public StringProperty ratingProperty() {
        return new SimpleStringProperty(rating);
    }

    public StringProperty infoProperty() {
        return new SimpleStringProperty(info);
    }
}