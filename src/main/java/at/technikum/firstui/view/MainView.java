package at.technikum.firstui.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainView {

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> col1, col2, col3, col4, col5;

    @FXML
    public void initialize() {
        double numColumns = 5.0;  // Total number of columns
        col1.prefWidthProperty().bind(tableView.widthProperty().divide(numColumns));
        col2.prefWidthProperty().bind(tableView.widthProperty().divide(numColumns));
        col3.prefWidthProperty().bind(tableView.widthProperty().divide(numColumns));
        col4.prefWidthProperty().bind(tableView.widthProperty().divide(numColumns));
        col5.prefWidthProperty().bind(tableView.widthProperty().divide(numColumns));
    }
}

// TODO: maybe in fxml file
