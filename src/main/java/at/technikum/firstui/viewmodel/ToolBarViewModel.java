package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.NewStageService;
import at.technikum.firstui.services.TourListService;

public class ToolBarViewModel {

    private final NewStageService newStageService;
    private final PDFViewModel pdfViewModel;

    public ToolBarViewModel(Publisher publisher, TourListService tourListService) {
        this.newStageService = new NewStageService();
        this.pdfViewModel = new PDFViewModel(tourListService);
    }

    public void onAdd() {
        newStageService.loadFXML("add-stage-view.fxml");
    }

    public void onModify() {
        newStageService.loadFXML("modify-tour-view.fxml");
    }

    public void onGeneratePdf() {
        pdfViewModel.generatePdf();
    }

    public void onSaveFavoritePlace() {
        newStageService.loadFXML("save-favorite-view.fxml");
    }
}
