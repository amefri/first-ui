package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class ModifyTourViewModel {

    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);


    private final TourListService tourListService;

    private final Publisher publisher;

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");
    private final StringProperty imagePath = new SimpleStringProperty("");

    private final BooleanProperty modifyTourButtonDisabled = new SimpleBooleanProperty(true);

    public ModifyTourViewModel(Publisher publisher, TourListService tourListService){
        this.publisher = publisher;
        this.tourListService =tourListService;



        // Listen to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        description.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        from.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        to.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        transportType.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        estimatedTime.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        imagePath.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
    }

    private void updateAddTourButtonDisabled() {
        // Check if any of the fields are empty
        modifyTourButtonDisabled.set(name.get().isEmpty() || description.get().isEmpty() ||
                from.get().isEmpty() || to.get().isEmpty() ||
                transportType.get().isEmpty() || distance.get().isEmpty() ||
                estimatedTime.get().isEmpty());
    }

    public void modifyTour() {
            if (!modifyTourButtonDisabled.get()) {
                if (tourListService.isSelected()) {
                    Tours toursSelected = tourListService.getCurrentlySelected();
                    String tourNameSelected = toursSelected.getName();
                    String tourName = name.get();
                    if(!Objects.equals(tourName, tourNameSelected)){
                        logger.warn("Tourname and selcted tourname are not the same");
                        return;
                    }
                    Tours newTour = new Tours(toursSelected.getName(), description.get(), from.get(), to.get(), transportType.get(), distance.get(), estimatedTime.get(), imagePath.get());
                    Long id = toursSelected.getId();
                    newTour.setId(id);
                    tourListService.modifyTour(newTour);
                }else{
                    logger.warn("No Tour was selcted");

                }
            }



        name.set("");
        description.set("");
        from.set("");
        to.set("");
        transportType.set("");
        distance.set("");
        estimatedTime.set("");

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

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty estimatedTimeProperty() {
        return estimatedTime;
    }

    public BooleanProperty modifyTourButtonDisabledProperty() {
        return modifyTourButtonDisabled;
    }

}
