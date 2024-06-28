package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.FavPlaces;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.repository.FavPlaceDatabaseRepository;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFavoriteViewModel implements ObjectSubscriber {
    private final Logger logger = LogManager.getLogger(SearchHistoryViewModel.class);

    private final ObservableList<FavPlaces> favoritePlaces = FXCollections.observableArrayList();
    private final FavPlaceDatabaseRepository favPlaceService;
    private final Publisher publisher;
    private final IntegerProperty selectedIndex = new SimpleIntegerProperty();

    public SaveFavoriteViewModel(Publisher publisher, FavPlaceDatabaseRepository favPlaceService) {
        this.publisher = publisher;
        this.favPlaceService = favPlaceService;

        this.selectedIndex.addListener((observable, oldVal, newVal) -> selectFavPlace());

        // Load favorite places from the database
        loadFavoritePlacesFromDatabase();

        // Subscribe to the ADD_FAV event
        this.publisher.subscribe(Event.ADD_FAV, this);
    }

    public void selectFavPlace() {
        if (selectedIndex.get() == -1) {
            return;
        }
        FavPlaces place = favoritePlaces.get(selectedIndex.get());
        logger.info("\"%s\" selected in history".formatted(place));

        // send history select event
        favPlaceService.setCurrentlySelected(place);
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

    public FavPlaces getCurrentlySelected() {
        logger.info("currently selected Place: " + favPlaceService.getCurrentlySelected());
        return favPlaceService.getCurrentlySelected();
    }

    public void delete() {
        int index = selectedIndex.get();
        if (index >= 0 && index < favoritePlaces.size()) {
            FavPlaces place = favoritePlaces.get(index);
            if (favPlaceService.deleteTerm(place.getNameFav())) {
                favoritePlaces.remove(index);
                logger.info("Place deleted: " + place.getNameFav());
            } else {
                logger.warn("Failed to delete Place: " + place.getNameFav());
            }
        } else {
            logger.warn("Invalid index or empty list.");
        }
    }

    public IntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }
}
