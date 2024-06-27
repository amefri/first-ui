package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.AddRouteLogViewModel;
import at.technikum.firstui.viewmodel.AddStageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRouteLogView implements Initializable {

    private final AddRouteLogViewModel viewModel;


    @FXML
    private TextField nameField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextField infoField;

    @FXML
    private Button addTourLogButton;

    public AddRouteLogView(AddRouteLogViewModel viewModel) {
        this.viewModel = viewModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind properties to text fields
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        dateField.textProperty().bindBidirectional(viewModel.dateProperty());
        ratingField.textProperty().bindBidirectional(viewModel.durationProperty());
        infoField.textProperty().bindBidirectional(viewModel.distanceProperty());

        // Bind button disable property
        addTourLogButton.disableProperty().bind(viewModel.addTourLogButtonDisabledProperty());
    }

    @FXML
    public void addTourLog() {
        viewModel.addTourLog();
    }

}