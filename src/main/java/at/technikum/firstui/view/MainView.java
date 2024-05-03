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
        at.technikum.firstui.view.TableView.keepColumnsBoundTogether(numColumns, col1, tableView, col2, col3, col4);
    }
}

// TODO: maybe in fxml file
