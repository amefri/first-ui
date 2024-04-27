package at.technikum.firstui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListViewModel {

    
    private final ObservableList<String> listItems = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"); //TODO: load items from postgres db

    public ObservableList<String> getItems() {
        return listItems;
    }

    public void onItemClicked(String item) {
        System.out.println("Clicked on: " + item);
    }

}
