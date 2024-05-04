package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.SearchHistoryViewModel;
import at.technikum.firstui.viewmodel.TourListViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListView implements Initializable {
    private final TourListViewModel viewModel;

    @FXML
    private ListView<String> NameListView;

    public TourListView(TourListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.NameListView
                .setItems(viewModel.getTourNames());
        this.viewModel.selectedSearchIndexProperty()
                .bind(NameListView.getSelectionModel().selectedIndexProperty());
    }
}
