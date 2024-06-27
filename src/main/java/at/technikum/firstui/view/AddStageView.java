package at.technikum.firstui.view;

import at.technikum.firstui.entity.FavPlaces;
import at.technikum.firstui.viewmodel.AddStageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

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
    private ComboBox<FavPlaces> favPlacesComboBox; // ComboBox für Lieblingsorte

    @FXML
    private ComboBox<String> transportTypeField;

    @FXML
    private Button addTourButton;

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
        transportTypeField.valueProperty().bindBidirectional(viewModel.transportTypeProperty());

        // Set items for favorite places ComboBox
        favPlacesComboBox.setItems(viewModel.getFavoritePlaces());

        // Set a StringConverter to display the name of the favorite place
        favPlacesComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(FavPlaces favPlaces) {
                return favPlaces != null ? favPlaces.getNameFav() : "";
            }

            @Override
            public FavPlaces fromString(String string) {
                return null; // No conversion from String to FavPlaces needed
            }
        });

        // Bind button disable property
        addTourButton.disableProperty().bind(viewModel.addTourButtonDisabledProperty());

        // Add listener for favorite places ComboBox
        favPlacesComboBox.setOnAction(event -> handleFavPlaceSelection());
    }

    @FXML
    private void handleFavPlaceSelection() {
        FavPlaces selectedPlace = favPlacesComboBox.getSelectionModel().getSelectedItem();
        if (selectedPlace != null) {
            toField.setText(selectedPlace.getNameFav());
        }
    }

    @FXML
    public void addTour() {
        viewModel.addTour();
    }
}
