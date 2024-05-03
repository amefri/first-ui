package at.technikum.firstui.view;

import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.viewmodel.ListViewModel;
import at.technikum.firstui.viewmodel.SearchViewModel;
import at.technikum.firstui.viewmodel.TableViewModel;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class ListView implements Initializable {

    @FXML

    private javafx.scene.control.ListView<String> listView;

    private final ListViewModel listViewModel = new ListViewModel();


    /*public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(viewModel.getItems());

        // Listen for selection changes and update the ViewModel
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> { //TODO der Listener muss hier raus
            viewModel.setSelectedItem(newVal);
        });
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.setItems(listViewModel.getItems());

        // Bind the selectedIndex property to the selected index of the ListView
        listViewModel.selectIndexProperty().bind(listView.getSelectionModel().selectedIndexProperty());



    }
}





