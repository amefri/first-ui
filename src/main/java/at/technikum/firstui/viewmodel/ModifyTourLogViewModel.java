package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import javafx.beans.property.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModifyTourLogViewModel implements ObjectSubscriber {
    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);

    private final Publisher publisher;
    private final TourLogService tourLogService;
    private final TourListService tourListService;


    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty rating = new SimpleStringProperty("");
    private final StringProperty info = new SimpleStringProperty("");

    private final BooleanProperty modifyTourLogButtonDisabled = new SimpleBooleanProperty(true);


    public ModifyTourLogViewModel(Publisher publisher, TourLogService tourLogService, TourListService tourListService) {

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
        modifyTourLogButtonDisabled.set(name.get().isEmpty() || date.get().isEmpty() ||
                rating.get().isEmpty() || info.get().isEmpty());
    }


    public void modifyTourLog() {
        if(!modifyTourLogButtonDisabled.get()){
            if(tourLogService.isSelected()){
                TourLog currentlySelected = tourLogService.getCurrentlySelected();
                Long id = currentlySelected.getId();
                TourLog newTourLog = new TourLog(name.get(), date.get(), rating.get(), info.get());
                newTourLog.setId(id);
                tourLogService.modifyTourLog(newTourLog);
                logger.info("TourLog modified: " + newTourLog);
                publisher.publish(Event.MODIFY_TOUR, newTourLog);
            }else{
                logger.warn("No TourLog was selected");

            }

        }
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty dateProperty() {
        return date;
    }


    public StringProperty infoProperty() {
        return info;
    }

    public StringProperty ratingProperty() {
        return rating;
    }


    public BooleanProperty modifyTourLogButtonDisabledProperty() {
        return modifyTourLogButtonDisabled;
    }



    @Override
    public void notify(Object message) {
        
    }


}
