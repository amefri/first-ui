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
import java.util.List;

public class TableListViewModel implements ObjectSubscriber {

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
        this.selectedAddTourIndex.addListener((obs, oldVal, newVal) -> selectTourIndex(newVal.intValue()));
    }

    private void loadTourLogs() {
        List<TourLog> logs = tourLogService.getAllTourLogs();
        tourLogs.clear();
        tourLogs.addAll(logs);
    }

    private void updateTourLogs(Object message) {
        if (message instanceof String) {
            String selectedTourName = (String) message;
            System.out.println("SelectedTourName for table: " + selectedTourName);
            List<TourLog> logs = tourLogService.getTourLogsByTourName(selectedTourName);
            tourLogs.clear();
            tourLogs.addAll(logs);
        }
    }

    private void addToTourLogs(Object message) {
        if (message instanceof TourLog) {
            TourLog tourLog = (TourLog) message;
            tourLogs.add(tourLog);
        }
    }




    private void selectTourIndex(int index) {
        if (index == -1) {
            System.out.println("No tour selected.");
        } else {
            System.out.println("Selected Tour: " + tourLogs.get(index).getName());
        }
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
            //tourLogService.deleteTourByName(tourLog.getName());
            System.out.println("Tour deleted: " + tourLog.getName());
        } else {
            System.out.println("Invalid index or empty list.");
        }

    }

    @Override
    public void notify(Object message) {

    }

    /*
    public void updateTourLogs(String selectedTourName) {
        Collection<TourLog> logs = tourLogService.getTourLogsByTourName(selectedTourName);
        tourLogs.clear();
        tourLogs.addAll(logs);
    }

     */
}

