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

import java.util.Collection;

public class TourListViewModel implements ObjectSubscriber {

    private final ObservableList<String> tourList = FXCollections.observableArrayList();
    private final IntegerProperty selectedAddTourIndex = new SimpleIntegerProperty();
    private TourListService tourListService;
    private TourLogService tourLogService;
    private Publisher publisher;

    public TourListViewModel(Publisher publisher, TourListService tourListService, TourLogService tourLogService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
        this.tourLogService = tourLogService;


        // Subscribe this ViewModel to the TOUR_ADDED event
        this.publisher.subscribe(Event.TOUR_ADDED, this);


        // Add listener to handle selection index changes
        this.selectedAddTourIndex.addListener((obs, oldVal, newVal) -> selectTourNames(newVal.intValue()));
    }
public void setTourListService(TourListService tourListService) {
        this.tourListService = tourListService;
    }

    public void setSelectedAddTourIndex(int index) {
        selectedAddTourIndex.set(index);
    }
    @Override
    public void notify(Object message) {
        if (message instanceof Tours) {
            Tours tour = (Tours) message;
            addToTourList(tour.getName());
            tourListService.addTour(tour);  // Assuming you also want to add the tour to a service-managed list
        }
    }

    private void selectTourNames(int index) {
        if (index == -1) {
            // Handle no selection case, possibly clear a selection display
            System.out.println("No tour selected.");
        } else {
            System.out.println("Selected Tour: " + tourList.get(index));
            String tourName = tourList.get(index);
            tourLogService.getTourLogsByTourName(tourName);
            publisher.publish(Event.SELECTED_TOUR_CHANGED, tourName);


        }
    }

    public void addToTourList(String tourName) {
        tourList.add(tourName);
        System.out.println("Added tour: " + tourName + " to the list");
    }

    public ObservableList<String> getTourNames() {
        return tourList;
    }
    public ObservableList<String> getTourList() {
        return tourList;
    }

    public IntegerProperty selectedAddTourProperty() {
        return selectedAddTourIndex;
    }
    public void deleteSelectedTour() {
        int index = selectedAddTourIndex.get();
        if (index >= 0 && index < tourList.size()) { // Check if index is valid
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



    private void selectTourNames(String selectedTourName) {
        int index = tourList.indexOf(selectedTourName);
        if (index != -1) {
            selectedAddTourIndex.set(index);
        } else {
            System.out.println("Tour not found: " + selectedTourName);
        }
    }

}
