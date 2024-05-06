package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel {

    private final ObservableList<String> tourList = FXCollections.observableArrayList();
    private final IntegerProperty selectedAddTourIndex = new SimpleIntegerProperty();

    public TourListViewModel(Publisher publisher) {

        // if item is selected, fill in search text
        this.selectedAddTourIndex.addListener(observable -> selectTourNames());

        // on tour added event, add tour details to the list
        publisher.subscribe(Event.TOUR_ADDED, this::addToTourList);
        System.out.println("TourListViewModel subscribed to TOUR_ADDED event");
    }

    public void selectTourNames() {
        if (selectedAddTourIndex.get() == -1) {
            return;
        }

        // TODO: send history select event
    }

    private void addToTourList(String tourDetails) {
        tourList.add(tourDetails);
    }

    public ObservableList<String> getTourNames() {
        return tourList;
    }

    public IntegerProperty selectedAddTourProperty() {
        return selectedAddTourIndex;
    }
}
