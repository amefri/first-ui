package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourLogService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class AddRouteLogViewModel {


    private final TourLogService tourLogService;
    private final Publisher publisher;


    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");

    private final BooleanProperty addTourLogButtonDisabled = new SimpleBooleanProperty(true);


    private final ObservableList<String> addedRouteLogs = FXCollections.observableArrayList();

    public AddRouteLogViewModel(Publisher publisher, TourLogService tourLogService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;

        // Listen to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        date.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        duration.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
    }

    private void updateAddTourLogButtonDisabled() {
        // Check if any of the fields are empty
        addTourLogButtonDisabled.set(name.get().isEmpty() || date.get().isEmpty() ||
                duration.get().isEmpty() || distance.get().isEmpty());
    }

    public void addTourLog() {
        // Check if addButton is enabled
        if (!addTourLogButtonDisabled.get()) {
            System.out.println("Add Button Works");
            TourLog tourLog = new TourLog(name.get(), date.get(), duration.get(), distance.get());
            tourLogService.addTourLog(tourLog);
            publisher.publish(Event.TOUR_LOG_ADDED, tourLog);

            // Clear fields after publishing
            name.set("");
            date.set("");
            duration.set("");
            distance.set("");

        }
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

    public BooleanProperty addTourLogButtonDisabledProperty() {
        return addTourLogButtonDisabled;
    }

}
