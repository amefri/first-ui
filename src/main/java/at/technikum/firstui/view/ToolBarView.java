package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.ToolBarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ToolBarView {

    @FXML
    private Button buttonAdd;
    @FXML private Button buttonDelete;
    @FXML private Button buttonMore;

    private final ToolBarViewModel viewModel = new ToolBarViewModel();

    @FXML
    public void initialize() {
        buttonAdd.setOnAction(e -> viewModel.onAdd());
        buttonDelete.setOnAction(e -> viewModel.onDelete());
        buttonMore.setOnAction(e -> viewModel.onMore());
    }
}
