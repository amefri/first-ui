package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListViewModel {

    //private final Publisher publisher;

    private final ObservableList<String> items
            = FXCollections.observableArrayList();
    private IntegerProperty selectedIndex
            = new SimpleIntegerProperty();

    public ListViewModel(){//Publisher publisher) {
        //this.publisher = publisher;
        items.addAll("Item 1", "Item 2", "Item 3");
        // if item is selected, fill in search text
        this.selectedIndex.addListener(

                observable -> {System.out.println("Listener triggered");
                    selectItems();}

        );

        // on search event, add term to history
        //publisher.subscribe(Event.SEARCH_TERM_SEARCHED, this::addToList);
    }

    public void selectItems() {
    int index = selectedIndex.get();
    System.out.println("Index: " + index);
    if (index == -1) {
        // No item is selected in the ListView, so return without doing anything
        return;
    }

    String selectedItem = items.get(index);
        System.out.println("Selected Item: " + selectedItem);


}

    private void addToList(String routItem) {
        items.add(routItem);
    }

    public ObservableList<String> getItems() {
        return items;
    }

    public IntegerProperty selectIndexProperty() {
        return selectedIndex;
    }
}