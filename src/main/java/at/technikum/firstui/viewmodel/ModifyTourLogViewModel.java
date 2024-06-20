package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModifyTourLogViewModel implements ObjectSubscriber {

    private final Publisher publisher;
    private final TourLogService tourLogService;
    private final TourListService tourListService;


    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final BooleanProperty modifyTourLogButtonDisabled = new SimpleBooleanProperty(true);


    public ModifyTourLogViewModel(Publisher publisher, TourLogService tourLogService, TourListService tourListService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;
        this.tourListService = tourListService;

        this.publisher.subscribe(Event.MODIFY_TOUR_LOG, (ObjectSubscriber) this::updateTourLog);



        // Listen to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        date.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        duration.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
    }

    private void updateTourLog(Object o) {
    }


    private void updateAddTourLogButtonDisabled() {
        // Check if any of the fields are empty
        modifyTourLogButtonDisabled.set(name.get().isEmpty() || date.get().isEmpty() ||
                duration.get().isEmpty() || distance.get().isEmpty());
    }


    public void modifyTourLog() {
        // Check if addButton is enabled
        if (!modifyTourLogButtonDisabled.get()) {
            System.out.println("Add Button Works");

            if(tourListService.getTourListState()){
                TourLog tourLog = new TourLog(name.get(), date.get(), duration.get(), distance.get());
                tourLog.setTour(tourListService.getCurrentlySelected());
                tourLogService.addTourLog(tourLog);
                publisher.publish(Event.TOUR_LOG_ADDED, tourLog);

                // Clear fields after publishing
                name.set("");
                date.set("");
                duration.set("");
                distance.set("");

            } else {
                System.out.println("TourList is empty. Add a Tour first");
            }
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

    public BooleanProperty modifyTourLogButtonDisabledProperty() {
        return modifyTourLogButtonDisabled;
    }



    @Override
    public void notify(Object message) {
        
    }
}
