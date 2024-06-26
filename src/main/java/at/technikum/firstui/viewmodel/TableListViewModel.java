package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourLogService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TableListViewModel implements ObjectSubscriber {
    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);

    private final ObservableList<TourLog> tourLogs = FXCollections.observableArrayList();
    private final IntegerProperty selectedAddTourIndex = new SimpleIntegerProperty();
    private final TourLogService tourLogService;
    private final Publisher publisher;

    public TableListViewModel(Publisher publisher, TourLogService tourLogService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;

        // Subscribe this ViewModel to the TOUR_LOG_ADDED event
        this.publisher.subscribe(Event.SELECTED_TOUR_CHANGED, (ObjectSubscriber) this::updateTourLogs);
        this.publisher.subscribe(Event.TOUR_LOG_ADDED, (ObjectSubscriber) this::addToTourLogs);

        loadTourLogs();

        // Add listener to handle selection index changes
        this.selectedAddTourIndex.addListener((obs, oldVal, newVal) -> {
            selectTourLogIndex(newVal.intValue());
        });
    }

    public void loadTourLogs() {
        List<TourLog> logs = tourLogService.getAllTourLogs();
        tourLogs.clear();
        tourLogs.addAll(logs);
    }

    public void addToTourLogs(Object message) {
        if (message instanceof TourLog) {
            TourLog tourLog = (TourLog) message;
            tourLogs.add(tourLog);
        }
    }

    public void deleteSelectedTour() {
        int index = selectedAddTourIndex.get();
        if (index >= 0 && index < tourLogs.size()) {
            TourLog tourLog = tourLogs.get(index);
            if (tourLogService.deleteTourById(tourLog.getId())) {
                tourLogs.remove(index);
                logger.info("TourLog deleted: " + tourLog.getName());
            } else {
                logger.warn("Failed to delete TourLog: " + tourLog.getName());
            }
        } else {
            logger.warn("Invalid index or empty list.");
        }
    }


    public void selectTourLogIndex(int index) {
        if (index == -1) {
            logger.warn("No tour selected");
        } else if (index >= 0 && index < tourLogs.size()) {
            TourLog tourLog = tourLogs.get(index);
            tourLogService.setCurrentlySelected(tourLog);
            tourLogService.setIsSelected(true);
            logger.info("Selected tour log: " + tourLog.getName() + " with ID: " + tourLog.getId());
        } else {
            logger.warn("Invalid index.");
        }
    }


    public void updateTourLogs(Object message) {
        if (message instanceof Long) {
            Long selectedTourId = (Long) message;
            logger.info("Selected Index: " + selectedTourId);
            List<TourLog> logs = tourLogService.getTourLogsByTourId(selectedTourId);
            tourLogs.clear();
            tourLogs.addAll(logs);
        }
    }

    public ObservableList<TourLog> getTourLogs() {
        return tourLogs;
    }

    public IntegerProperty selectedAddTourProperty() {
        return selectedAddTourIndex;
    }

    @Override
    public void notify(Object message) {
        // Implementation for the notify method if needed
    }
}