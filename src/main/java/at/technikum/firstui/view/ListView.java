package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.ListViewModel;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.util.ResourceBundle;

public class ListView implements Initializable {

    @FXML

    private javafx.scene.control.ListView<String> listView;

    private final ListViewModel listViewModel;

    public ListView(ListViewModel listViewModel) { this.listViewModel = listViewModel;}







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(listViewModel.getItems());


        // Bind the selectedIndex property to the selected index of the ListView
        listViewModel.selectIndexProperty().bind(listView.getSelectionModel().selectedIndexProperty());
    }
}




