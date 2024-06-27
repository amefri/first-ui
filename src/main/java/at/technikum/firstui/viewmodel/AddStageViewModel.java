package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.FavPlaces;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.repository.FavPlaceDatabaseRepository;

import at.technikum.firstui.services.TourListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddStageViewModel implements ObjectSubscriber {

    private final TourListService tourListService;
    private final FavPlaceDatabaseRepository favPlaceService;
    private final Publisher publisher;

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");
    private final StringProperty imagePath = new SimpleStringProperty("");

    private final BooleanProperty addTourButtonDisabled = new SimpleBooleanProperty(true);
    private final ObservableList<FavPlaces> favoritePlaces = FXCollections.observableArrayList();

    public AddStageViewModel(Publisher publisher, TourListService tourListService, FavPlaceDatabaseRepository favPlaceService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
        this.favPlaceService = favPlaceService;

        // Load favorite places
        loadFavoritePlaces();

        // Subscribe to the ADD_FAV event
        this.publisher.subscribe(Event.ADD_FAV, this);

        // Listen to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        description.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        from.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        to.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        transportType.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
    }

    private void loadFavoritePlaces() {
        favoritePlaces.clear();
        favoritePlaces.addAll(favPlaceService.getAll());
    }

    private void updateAddTourButtonDisabled() {
        // Check if any of the fields are empty
        addTourButtonDisabled.set(name.get().isEmpty() || description.get().isEmpty() ||
                from.get().isEmpty() || to.get().isEmpty() ||
                transportType.get().isEmpty());
    }

    public void addTour() {
        if (!addTourButtonDisabled.get()) {
            Tours tour = new Tours(name.get(), description.get(), from.get(), to.get(), transportType.get(), distance.get(), estimatedTime.get(), imagePath.get());

            tourListService.addTour(tour);
            publisher.publish(Event.TOUR_ADDED, tour);

            // Clear fields after publishing
            name.set("");
            description.set("");
            from.set("");
            to.set("");
            transportType.set("");
        }
    }

    public ObservableList<FavPlaces> getFavoritePlaces() {
        return favoritePlaces;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }



    public BooleanProperty addTourButtonDisabledProperty() {
        return addTourButtonDisabled;
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
