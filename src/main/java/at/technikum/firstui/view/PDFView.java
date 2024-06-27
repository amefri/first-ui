package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.PDFViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PDFView {

    private Button generatePdfButton;
    private PDFViewModel viewModel;
    @FXML
    private void initialize() {
        generatePdfButton.setOnAction(this::generatePdf);
    }

    @FXML
    private void generatePdf(ActionEvent event) {
        viewModel.generatePdf();
    }
}
