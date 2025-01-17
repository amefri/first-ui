package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import javafx.beans.property.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AddRouteLogViewModel implements ObjectSubscriber {

    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);
    private final Publisher publisher;
    private final TourLogService tourLogService;
    private final TourListService tourListService;


    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty rating = new SimpleStringProperty("");
    private final StringProperty info = new SimpleStringProperty("");
    private final BooleanProperty addTourLogButtonDisabled = new SimpleBooleanProperty(true);



    public AddRouteLogViewModel(Publisher publisher, TourLogService tourLogService, TourListService tourListService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;
        this.tourListService = tourListService;


        // Listen to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        date.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        rating.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        info.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
    }

    private void updateAddTourLogButtonDisabled() {
        // Check if any of the fields are empty
        addTourLogButtonDisabled.set(name.get().isEmpty() || date.get().isEmpty() ||
                rating.get().isEmpty() || info.get().isEmpty());
    }

    public void addTourLog() {
        // Check if addButton is enabled
        if (!addTourLogButtonDisabled.get()) {
            if(tourListService.isSelected()){
                TourLog tourLog = new TourLog(name.get(), date.get(),rating.get(), info.get());
                tourLog.setTour(tourListService.getCurrentlySelected());
                tourLogService.addTourLog(tourLog);
                publisher.publish(Event.TOUR_LOG_ADDED, tourLog);

                logger.info("Added new tour log: {}", tourLog);

                // Clear fields after publishing
                name.set("");
                date.set("");
                rating.set("");
                info.set("");

            } else {
                logger.warn("TourList is empty or you did not select a tour. Add a Tour first or Select a Tour first");
            }
        }
    }


    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public StringProperty infoProperty() {
        return info;
    }

    public BooleanProperty addTourLogButtonDisabledProperty() {
        return addTourLogButtonDisabled;
    }

    @Override
    public void notify(Object message) {}}
