package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListViewModel {
    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);

    private final Publisher publisher;

    private final ObservableList<String> items
            = FXCollections.observableArrayList();
    private IntegerProperty selectedIndex
            = new SimpleIntegerProperty();
    //public ListViewModel(Publisher publisher){this.publisher = publisher;}
    public ListViewModel(Publisher publisher) {
        this.publisher = publisher;
        items.addAll("Item 1", "Item 2", "Item 3");
        // if item is selected, fill in search text
        this.selectedIndex.addListener(

                observable -> {logger.info("Listener triggered");
                    selectItems();}

        );

        // on search event, add term to history
        //publisher.subscribe(Event.SEARCH_TERM_SEARCHED, this::addToList);
    }

    public void selectItems() {
    int index = selectedIndex.get();
    logger.info("Index: " + index);
    if (index == -1) {
        // No item is selected in the ListView, so return without doing anything
        return;
    }

    String selectedItem = items.get(index);
        logger.info("Selected Item: " + selectedItem);

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