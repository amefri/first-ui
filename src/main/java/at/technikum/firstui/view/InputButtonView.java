package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.InputButtonViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InputButtonView implements Initializable {

    private final InputButtonViewModel viewModel;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    public InputButtonView() {
        this.viewModel = new InputButtonViewModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchField.textProperty()
                .bindBidirectional(viewModel.searchTextProperty());
        this.searchButton.disableProperty()
                .bind(viewModel.disabledSearchButtonProperty());
    }

    /*
    @FXML
    public void onSearchInput() {
        this.viewModel.updateSearchButton();
    }
    */
}