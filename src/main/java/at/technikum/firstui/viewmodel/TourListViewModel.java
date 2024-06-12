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

public class TourListViewModel implements ObjectSubscriber {

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
                System.out.println("Primary key: " + tour.getId());
                return tour.getId();
                // Assuming getId() returns the primary key
            }
        }
        return null;
    }

    private void selectTourNames(int index) {
        if (index == -1) {
            System.out.println("No tour selected.");
        } else {
            System.out.println("Selected Tour: " + tourList.get(index));
            System.out.println("Selected Index: " + index);
            String tourName = tourList.get(index);
            Long dbindex = getPKTour(); // Ensure this is not null
            if (dbindex != null) {
                System.out.println("DB Index (ID): " + dbindex);
                tourLogService.getTourLogsByTourName(tourName);
                publisher.publish(Event.SELECTED_TOUR_CHANGED, dbindex);
            } else {
                System.out.println("DB Index is null for tour: " + tourName);
            }
        }
    }

    public void addToTourList(String tourName) {
        tourList.add(tourName);
        System.out.println("Added tour: " + tourName + " to the list");
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
                System.out.println("Tour deleted: " + tourName);
            } else {
                System.out.println("Failed to delete tour: " + tourName);
            }
        } else {
            System.out.println("Invalid index or empty list.");
        }
    }

    public void selectTour(int index) {
        if (selectedAddTourProperty().isBound()) {
            selectedAddTourProperty().unbind();
        }
        selectedAddTourProperty().set(index);
    }

    @Override
    public void notify(Object message) {
        if (message instanceof Tours) {
            Tours tour = (Tours) message;
            addToTourList(tour.getName());
        }
    }
}
