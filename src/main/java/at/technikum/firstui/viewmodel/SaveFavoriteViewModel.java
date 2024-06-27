package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.FavPlaces;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.repository.FavPlaceDatabaseRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaveFavoriteViewModel implements ObjectSubscriber {

    private final ObservableList<FavPlaces> favoritePlaces = FXCollections.observableArrayList();
    private final FavPlaceDatabaseRepository favPlaceService;
    private final Publisher publisher;

    public SaveFavoriteViewModel(Publisher publisher, FavPlaceDatabaseRepository favPlaceService) {
        this.publisher = publisher;
        this.favPlaceService = favPlaceService;

        // Load favorite places from the database
        loadFavoritePlacesFromDatabase();

        // Subscribe to the ADD_FAV event
        this.publisher.subscribe(Event.ADD_FAV, this);
    }

    private void loadFavoritePlacesFromDatabase() {
        favoritePlaces.clear();
        favoritePlaces.addAll(favPlaceService.getAll());
    }

    public void saveFavoritePlace(String name, String description) {
        FavPlaces place = new FavPlaces(name, description, "", ""); // Passe ggf. die anderen Felder an
        favPlaceService.save(place);
        publisher.publish(Event.ADD_FAV, place); // Publiziert das Event
    }

    public ObservableList<FavPlaces> getFavoritePlaces() {
        return favoritePlaces;
    }

    @Override
    public void notify(Object message) {
        if (message instanceof FavPlaces) {
            FavPlaces favPlace = (FavPlaces) message;
            favoritePlaces.add(favPlace);
            System.out.println("Added favorite place: " + favPlace.getNameFav() + " to the list");
        }
    }
}
