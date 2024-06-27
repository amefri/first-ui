package at.technikum.firstui.entity;
import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
@Entity
@Table(name = "fav_places")

public class FavPlaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFav;

    @Column(name = "name")
    private String nameFav;
    @Column(name = "description")
    private String description;

    @Column(name = "longetude")
    private String longetude;
    @Column(name = "latitude")
    private String latitude;

    public FavPlaces() {}
    public FavPlaces(String name, String description, String longetude, String latitude) {
        this.nameFav = name;
        this.description = description;
        this.longetude = longetude;
        this.latitude = latitude;
    }

    public FavPlaces(String name, String description) {
        this.nameFav = name;
        this.description = description;
    }

    public String getNameFav() {
        return nameFav;
    }

    public Long getIdFav() {
        return idFav;
    }

    public String getDescription() {
        return description;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongetude() {
        return longetude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdFav(Long id) {
        this.idFav = id;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongetude(String longetude) {
        this.longetude = longetude;
    }

    public void setNameFav(String name) {
        this.nameFav = name;
    }
}
