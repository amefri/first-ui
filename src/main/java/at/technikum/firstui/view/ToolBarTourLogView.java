package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.ToolBarTourLogViewModel;
import at.technikum.firstui.viewmodel.ToolBarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ToolBarTourLogView {

    @FXML private Button buttonAdd;

    @FXML private Button buttonModify;
    @FXML private Button buttonPDF;

    private final ToolBarTourLogViewModel viewModel;

    public ToolBarTourLogView(ToolBarTourLogViewModel toolBarTourLogViewModel) {
        this.viewModel = toolBarTourLogViewModel;
    }

    @FXML
    public void initialize() {
        buttonAdd.setOnAction(e -> viewModel.onAdd());
        buttonModify.setOnAction(e -> viewModel.onModify());
        buttonPDF.setOnAction(e -> viewModel.onGenerateTourLogsPdf());
    }
}
