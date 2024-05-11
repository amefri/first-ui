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

public class TableListViewModel implements ObjectSubscriber {

    private final ObservableList<String> tourLogs = FXCollections.observableArrayList();
    private final IntegerProperty selectedAddTourIndex = new SimpleIntegerProperty();
    private TourLogService tourLogService;
    private Publisher publisher;

    public TableListViewModel(Publisher publisher, TourLogService tourLogService) {
        this.publisher = publisher;
        this.tourLogService = tourLogService;

        // Subscribe this ViewModel to the TOUR_ADDED event
        this.publisher.subscribe(Event.TOUR_LOG_ADDED, this);

        // Add listener to handle selection index changes
        this.selectedAddTourIndex.addListener((obs, oldVal, newVal) -> selectTourNames(newVal.intValue()));
    }

    @Override
    public void notify(Object message) {
        if (message instanceof TourLog) {
            TourLog tourLog = (TourLog) message;
            addToTourLogs(tourLog);
            tourLogService.addTourLog(tourLog);
        }
    }

    private void selectTourNames(int index) {
        if (index == -1) {
            // Handle no selection case, possibly clear a selection display
            System.out.println("No tour selected.");
        } else {
            System.out.println("Selected Tour: " + tourLogs.get(index));
        }
    }

    public void addToTourLogs(TourLog tourLog) {
        tourLogs.add(tourLog.getName() + " - " + tourLog.getDate() + " - " + tourLog.getDuration() + " - " + tourLog.getDistance());
        System.out.println("Added tour log: " + tourLog.getName() + " to the list");

    }

    public ObservableList<String> getTourLogs() {
        return tourLogs;
    }

    public IntegerProperty selectedAddTourProperty() {
        return selectedAddTourIndex;
    }
    public void deleteSelectedTour() {
        int index = selectedAddTourIndex.get();
        if (index >= 0 && index < tourLogs.size()) { // Check if index is valid
            String[] parts = tourLogs.get(index).split(" - ");
            String tourName = parts[0];
            if (tourLogService.deleteTourByName(tourName)) {
                tourLogs.remove(index);
                System.out.println("Tour deleted: " + tourName);
            } else {
                System.out.println("Failed to delete tour: " + tourName);
            }
        } else {
            System.out.println("Invalid index or empty list.");
        }
    }


}
