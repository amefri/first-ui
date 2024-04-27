package at.technikum.firstui.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableViewModel {

    private final ObservableList<String> items;

    public TableViewModel() {
        items = FXCollections.observableArrayList();
        // Hier kannst du die Daten für die TableView hinzufügen
        items.addAll("Data 1", "Data 2", "Data 3");
    }

    public ObservableList<String> getItems() {
        return items;
    }
}
