package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class AddRouteLogViewModel {
    private Stage stage;

    private final Publisher publisher;


    private final StringProperty routeName = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");

    private final BooleanProperty addTourLogButtonDisabled = new SimpleBooleanProperty(true);


    private final ObservableList<String> addedRouteLogs = FXCollections.observableArrayList();

    public AddRouteLogViewModel(Publisher publisher) {
        this.publisher = publisher;

        // Listen to changes in fields and update addButtonDisabled property
        routeName.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        date.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        duration.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
    }

    private void updateAddTourLogButtonDisabled() {
        // Check if any of the fields are empty
        addTourLogButtonDisabled.set(routeName.get().isEmpty() || date.get().isEmpty() ||
                duration.get().isEmpty() || distance.get().isEmpty());
    }

    public void addTourLog() {
        // Check if addButton is enabled
        System.out.println("Add Button Works");
        if (!addTourLogButtonDisabled.get()) {
            String routeLog = String.format("Tour Name: %s, Date: %s, Duration: %s, Distance: %s",
                    routeName.get(), date.get(), duration.get(), distance.get());
            addedRouteLogs.add(routeLog);
            publisher.publish(Event.TOUR_LOG_ADDED, routeLog);

            // Clear fields after publishing
            routeName.set("");
            date.set("");
            duration.set("");
            distance.set("");

        }
    }


    public StringProperty routeNameProperty() {
        return routeName;
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
