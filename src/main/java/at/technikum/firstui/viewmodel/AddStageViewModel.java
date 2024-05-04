package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddStageViewModel {

    private final Publisher publisher;

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");

    private final BooleanProperty addButtonDisabled = new SimpleBooleanProperty(true);

    public AddStageViewModel(Publisher publisher) {
        this.publisher = publisher;

        // Listen to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddButtonDisabled());
        description.addListener((observable, oldValue, newValue) -> updateAddButtonDisabled());
        from.addListener((observable, oldValue, newValue) -> updateAddButtonDisabled());
        to.addListener((observable, oldValue, newValue) -> updateAddButtonDisabled());
        transportType.addListener((observable, oldValue, newValue) -> updateAddButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddButtonDisabled());
        estimatedTime.addListener((observable, oldValue, newValue) -> updateAddButtonDisabled());
    }

    private void updateAddButtonDisabled() {
        // Check if any of the fields are empty
        addButtonDisabled.set(name.get().isEmpty() || description.get().isEmpty() ||
                from.get().isEmpty() || to.get().isEmpty() ||
                transportType.get().isEmpty() || distance.get().isEmpty() ||
                estimatedTime.get().isEmpty());
    }

    public void addTour() {
        // Check if addButton is enabled
        if (!addButtonDisabled.get()) {
            String message = String.format("Name: %s, Description: %s, From: %s, To: %s, Transport Type: %s, Distance: %s, Estimated Time: %s",
                    name.get(), description.get(), from.get(), to.get(), transportType.get(), distance.get(), estimatedTime.get());
            publisher.publish(Event.TOUR_ADDED, message);

            // Clear fields after publishing
            name.set("");
            description.set("");
            from.set("");
            to.set("");
            transportType.set("");
            distance.set("");
            estimatedTime.set("");
        }
    }


    // Getters for properties
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

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty estimatedTimeProperty() {
        return estimatedTime;
    }

    public BooleanProperty addButtonDisabledProperty() {
        return addButtonDisabled;
    }
}
