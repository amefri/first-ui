package at.technikum.firstui.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
public class SelectedTime {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private Instant dateTime;

    @ManyToOne
    private SearchTerm searchTerm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public SearchTerm getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(SearchTerm searchTerm) {
        this.searchTerm = searchTerm;
    }
}
