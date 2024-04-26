package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.ToolBarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ToolBarView {

    @FXML
    private Button btnAdd;
    @FXML private Button btnDelete;
    @FXML private Button btnMore;

    private final ToolBarViewModel viewModel = new ToolBarViewModel();

    @FXML
    public void initialize() {
        btnAdd.setOnAction(e -> viewModel.onAdd());
        btnDelete.setOnAction(e -> viewModel.onDelete());
        btnMore.setOnAction(e -> viewModel.onMore());
    }
}
