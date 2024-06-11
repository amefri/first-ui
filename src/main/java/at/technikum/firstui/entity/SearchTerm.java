package at.technikum.firstui.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SearchTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String term;

    @OneToMany(
            targetEntity = SelectedTime.class,
            mappedBy = "searchTerm",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )

    private List<SelectedTime> selectedTimes;

    public SearchTerm() {}

    public SearchTerm(String term) {
        this.term = term;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void addSelectedTime(SelectedTime selectedTime) {
        selectedTime.setSearchTerm(this);
        selectedTimes.add(selectedTime);
    }

}
