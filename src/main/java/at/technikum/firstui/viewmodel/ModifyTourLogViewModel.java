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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModifyTourLogViewModel implements ObjectSubscriber {
    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);

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

        // Listen to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        date.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        duration.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
    }




    private void updateAddTourLogButtonDisabled() {
        // Check if any of the fields are empty
        modifyTourLogButtonDisabled.set(name.get().isEmpty() || date.get().isEmpty() ||
                duration.get().isEmpty() || distance.get().isEmpty());
    }


    public void modifyTourLog() {
        if(!modifyTourLogButtonDisabled.get()){
            if(tourLogService.isSelected()){
                TourLog currentlySelected = tourLogService.getCurrentlySelected();
                Long id = currentlySelected.getId();
                TourLog newTourLog = new TourLog(name.get(), date.get(), duration.get(), distance.get());
                newTourLog.setId(id);
                tourLogService.modifyTourLog(newTourLog);
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
