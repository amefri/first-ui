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

import java.util.Collection;

public class TableListViewModel implements ObjectSubscriber {

    private final ObservableList<TourLog> tourLogs = FXCollections.observableArrayList();
    private final IntegerProperty selectedAddTourIndex = new SimpleIntegerProperty();
    private final TourLogService tourLogService;
    private final Publisher publisher;


    public TableListViewModel(Publisher publisher, TourLogService tourLogService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;

        // Subscribe this ViewModel to the TOUR_LOG_ADDED event
        this.publisher.subscribe(Event.TOUR_LOG_ADDED, this);
        this.publisher.subscribe(Event.SELECTED_TOUR_CHANGED,this);

        // Add listener to handle selection index changes
        this.selectedAddTourIndex.addListener((obs, oldVal, newVal) -> selectTourIndex(newVal.intValue()));
    }


    @Override
    public void notify(Object message) {
        if (message instanceof TourLog) {
            TourLog tourLog = (TourLog) message;
            addToTourLogs(tourLog);
            tourLogService.addTourLog(tourLog);
        }

        if (message instanceof String && message.equals(Event.SELECTED_TOUR_CHANGED)) {
            String selectedTourName = (String) message;
            updateTourLogs(selectedTourName);
        }


    }

    private void selectTourIndex(int index) {
        if (index == -1) {
            System.out.println("No tour selected.");
        } else {
            System.out.println("Selected Tour: " + tourLogs.get(index).getName());
        }
    }

    public void addToTourLogs(TourLog tourLog) {
        tourLogs.add(tourLog);
        System.out.println("Added tour log: " + tourLog.getName() + " to the list");
    }

    public ObservableList<TourLog> getTourLogs() {
        return tourLogs;
    }

    public IntegerProperty selectedAddTourProperty() {
        return selectedAddTourIndex;
    }

    public void deleteSelectedTour() {
        int index = selectedAddTourIndex.get();
        if (index >= 0 && index < tourLogs.size()) {
            TourLog tourLog = tourLogs.remove(index);
            tourLogService.deleteTourByName(tourLog.getName());
            System.out.println("Tour deleted: " + tourLog.getName());
        } else {
            System.out.println("Invalid index or empty list.");
        }

    }

    private void updateTourLogs(String selectedTourName) {
        Collection<TourLog> logs = tourLogService.getTourLogsByTourName(selectedTourName);
        tourLogs.clear();
        tourLogs.addAll(logs);
    }
}

