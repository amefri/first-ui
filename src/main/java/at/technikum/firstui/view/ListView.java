package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.ListViewModel;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;

public class ListView implements Initializable {

    @FXML
    private javafx.scene.control.ListView<String> listView;


    private final ListViewModel viewModel = new ListViewModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(viewModel.getItems());
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                viewModel.onItemClicked(newSelection);
            }
        });
}


    }

