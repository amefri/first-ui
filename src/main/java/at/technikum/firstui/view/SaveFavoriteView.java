package at.technikum.firstui.view;

import at.technikum.firstui.entity.FavPlaces;
import at.technikum.firstui.viewmodel.SaveFavoriteViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SaveFavoriteView implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ListView<FavPlaces> favoritePlacesListView;

    private final SaveFavoriteViewModel viewModel;

    public SaveFavoriteView(SaveFavoriteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListViewItems();
        configureListViewCells();
    }

    private void setListViewItems() {
        favoritePlacesListView.setItems(viewModel.getFavoritePlaces());
    }

    private void configureListViewCells() {
        favoritePlacesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(FavPlaces item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNameFav() + " - " + item.getDescription());
                }
            }
        });
    }

    @FXML
    private void onSave() {
        viewModel.saveFavoritePlace(
                nameField.getText(),
                descriptionField.getText()
        );


        nameField.clear();
        descriptionField.clear();
    }
}
