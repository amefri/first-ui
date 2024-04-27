package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.TableViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import java.net.URL;
import java.util.ResourceBundle;


public class TableView implements Initializable {

    @FXML
    private javafx.scene.control.TableView<String> tableView;

    @FXML
    private TableColumn<String, String> col1;

    @FXML
    private TableColumn<String, String> col2;

    @FXML
    private TableColumn<String, String> col3;

    @FXML
    private TableColumn<String, String> col4;

    @FXML
    private TableColumn<String, String> col5;

    private final TableViewModel viewModel = new TableViewModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verknüpfe die TableView mit den Daten aus dem ViewModel
        tableView.setItems(viewModel.getItems());

        // Fülle die TableColumn-Objekte mit Daten
        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        col2.setCellValueFactory(cellData -> new SimpleStringProperty("Data for Column 2")); // Beispiel für statische Daten
        col3.setCellValueFactory(cellData -> new SimpleStringProperty("Data for Column 3")); // Beispiel für statische Daten
        col4.setCellValueFactory(cellData -> new SimpleStringProperty("Data for Column 4")); // Beispiel für statische Daten
        col5.setCellValueFactory(cellData -> new SimpleStringProperty("Data for Column 5")); // Beispiel für statische Daten
    }
}
