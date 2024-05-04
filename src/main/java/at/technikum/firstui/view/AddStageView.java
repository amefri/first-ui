package at.technikum.firstui.view;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.viewmodel.AddStageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStageView implements Initializable {

    private final AddStageViewModel viewModel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    private TextField transportTypeField;

    @FXML
    private TextField distanceField;

    @FXML
    private TextField estimatedTimeField;

    @FXML
    private Button addButton;

    public AddStageView() {
        this.viewModel = new AddStageViewModel(new Publisher());
    }

    public AddStageView(AddStageViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind properties to text fields
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        descriptionField.textProperty().bindBidirectional(viewModel.descriptionProperty());
        fromField.textProperty().bindBidirectional(viewModel.fromProperty());
        toField.textProperty().bindBidirectional(viewModel.toProperty());
        transportTypeField.textProperty().bindBidirectional(viewModel.transportTypeProperty());
        distanceField.textProperty().bindBidirectional(viewModel.distanceProperty());
        estimatedTimeField.textProperty().bindBidirectional(viewModel.estimatedTimeProperty());

        // Bind button disable property
        addButton.disableProperty().bind(viewModel.addButtonDisabledProperty());
    }

    @FXML
    public void addTour() {
        viewModel.addTour();
    }
}
