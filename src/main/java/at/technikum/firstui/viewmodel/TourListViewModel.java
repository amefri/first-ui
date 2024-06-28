package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TourListViewModel implements ObjectSubscriber {

    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);

    private final ObservableList<String> tourList = FXCollections.observableArrayList();
    private final IntegerProperty selectedAddTourIndex = new SimpleIntegerProperty();
    private final TourListService tourListService;
    private final TourLogService tourLogService;
    private final Publisher publisher;

    public TourListViewModel(Publisher publisher, TourListService tourListService, TourLogService tourLogService) {


        this.publisher = publisher;
        this.tourListService = tourListService;
        this.tourLogService = tourLogService;

        // Initialize the tour list from the database
        loadToursFromDatabase();

        // Subscribe to the TOUR_ADDED event
        this.publisher.subscribe(Event.TOUR_ADDED, this);

        // Add listener to handle selection index changes
        this.selectedAddTourIndex.addListener((obs, oldVal, newVal) -> selectTourNames(newVal.intValue()));
    }

    private void loadToursFromDatabase() {
        tourList.clear();
        for (Tours tour : tourListService.getTours()) {
            tourList.add(tour.getName());
        }
    }

    public Long getPKTour() {
        int index = selectedAddTourIndex.get();
        if (index >= 0 && index < tourList.size()) {
            String tourName = tourList.get(index);
            Tours tour = tourListService.getTourByName(tourName);
            if (tour != null) {
                logger.info("Primary key: " + tour.getId());
                return tour.getId();
            }
        }
        return null;
    }

    private void selectTourNames(int index) {
        if (index == -1) {
            logger.warn("No tour selected.");
        } else {

            logger.info("Selected Tour: " + tourList.get(index));
            String tourName = tourList.get(index);
            Tours tour = tourListService.getTourByName(tourName);
            tourListService.setIsSelected(true);
            tourListService.setCurrentlySelected(tour);

            Long dbindex = getPKTour(); // Ensure this is not null

            if (dbindex != null) {
                tourLogService.getTourLogsByTourName(tourName);
                publisher.publish(Event.SELECTED_TOUR_CHANGED, dbindex);
            } else {
                logger.warn("DB Index is null for tour: " + tourName);
            }
        }
    }

    public void addToTourList(String tourName) {
        tourList.add(tourName);
        logger.info("Added tour: " + tourName + " to the list");
    }

    public ObservableList<String> getTourNames() {
        return tourList;
    }

    public IntegerProperty selectedAddTourProperty() {
        return selectedAddTourIndex;
    }

    public void deleteSelectedTour() {
        int index = selectedAddTourIndex.get();
        if (index >= 0 && index < tourList.size()) {
            String tourName = tourList.get(index);
            if (tourListService.deleteTourByName(tourName)) {
                tourList.remove(index);
                logger.info("Tour deleted: " + tourName);
            } else {
                logger.warn("Failed to delete tour: " + tourName);
            }
        } else {
            logger.warn("Invalid index or empty list.");
        }
    }


    @Override
    public void notify(Object message) {
        if (message instanceof Tours) {
            Tours tour = (Tours) message;
            addToTourList(tour.getName());
        }
    }
}
