package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.ToolBarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ToolBarView {

    @FXML
    private Button buttonAdd;

    @FXML private Button buttonModify;
    @FXML private Button generatePdf;
    @FXML private Button buttonSaveFavorite;
    private final ToolBarViewModel viewModel;

    public ToolBarView(ToolBarViewModel toolBarViewModel) {
        this.viewModel = toolBarViewModel;
    }

    @FXML
    public void initialize() {
        buttonAdd.setOnAction(e -> viewModel.onAdd());
        buttonModify.setOnAction(e -> viewModel.onModify());
        generatePdf.setOnAction(e -> viewModel.onGeneratePdf());
        buttonSaveFavorite.setOnAction(e -> viewModel.onSaveFavoritePlace());
    }
}
