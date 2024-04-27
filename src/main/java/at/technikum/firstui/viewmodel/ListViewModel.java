package at.technikum.firstui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListViewModel {

    private final ObservableList<String> items;

    public ListViewModel() {
        items = FXCollections.observableArrayList();
        items.addAll("Item 1", "Item 2", "Item 3");
    }

    public ObservableList<String> getItems() {
        return items;
    }
}
