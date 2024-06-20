package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.ModifyTourLogViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyTourLogView implements Initializable {


    private final ModifyTourLogViewModel viewModel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField durationField;

    @FXML
    private TextField distanceField;

    @FXML
    private Button modifyTourLogButton;

    public ModifyTourLogView(ModifyTourLogViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        dateField.textProperty().bindBidirectional(viewModel.dateProperty());
        durationField.textProperty().bindBidirectional(viewModel.durationProperty());
        distanceField.textProperty().bindBidirectional(viewModel.distanceProperty());

        modifyTourLogButton.disableProperty().bind(viewModel.modifyTourLogButtonDisabledProperty());
    }

    @FXML
    public void modifyTourLog() {
        viewModel.modifyTourLog();
    }
}
