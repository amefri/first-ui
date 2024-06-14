package at.technikum.firstui.view;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.viewmodel.TableListViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;

public class TableListView implements Initializable {

    private final TableListViewModel viewModel;

    @FXML
    private TableView<TourLog> tableView;
    @FXML
    private TableColumn<TourLog, String> nameColumn;
    @FXML
    private TableColumn<TourLog, String> dateColumn;
    @FXML
    private TableColumn<TourLog, String> distanceColumn;
    @FXML
    private TableColumn<TourLog, String> durationColumn;

    public TableListView(TableListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("TableListView initialized");

        // Bind columns to model properties
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().distanceProperty());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());



        // Set table items
        this.tableView
                .setItems(viewModel.getTourLogs());

        // Add listener to update ViewModel's selected index property
        this.viewModel.selectedAddTourProperty()
                .bind(tableView.getSelectionModel().selectedIndexProperty());

        // Handle double-click event
        tableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                System.out.println("Double-click detected");
                viewModel.deleteSelectedTour();
            }
        });
    }
}
