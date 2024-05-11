package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.TableListViewModel;
import at.technikum.firstui.viewmodel.TourListViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TableListView implements Initializable {

    private final TableListViewModel viewModel;

    @FXML
    private ListView<String> TableListView;

    public TableListView(TableListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("TourListView initialized");
        this.TableListView
                .setItems(viewModel.getTourLogs());
        this.viewModel.selectedAddTourProperty()
                .bind(TableListView.getSelectionModel().selectedIndexProperty());

        TableListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Check for double-click
                viewModel.deleteSelectedTour();
            }
        });
    }
}
