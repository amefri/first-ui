package at.technikum.firstui.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.FocusModel;

public class ListViewModel {
    private ObservableList<String> items = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3");
    private ObjectProperty<String> selectedItem = new SimpleObjectProperty<>();

    public ListViewModel() {
        // Listen for changes to the selected item and handle them within the ViewModel
        selectedItem.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) { // Logic moved from the view to here
                onItemClicked(newVal);
            }
        });
    }

    public void setSelectedItem(String item) {
        selectedItem.set(item); // Directly setting the value from the controller
    }
    
    private final ObservableList<String> listItems = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"); //TODO: load items from postgres db

    public ObservableList<String> getItems() {
        return listItems;
    }

    public void onItemClicked(String item) {
        System.out.println("Clicked on: " + item);
    }

}
