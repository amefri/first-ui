package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.TourLogPDFViewModel;
import javafx.fxml.FXML;

public class TourLogPDFView {

    private TourLogPDFViewModel viewModel;

    public TourLogPDFView(TourLogPDFViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onGeneratePdf() {
        viewModel.generatePdf();
    }
}
