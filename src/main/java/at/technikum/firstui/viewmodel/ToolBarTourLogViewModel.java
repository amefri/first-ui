package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.NewStageService;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;

public class ToolBarTourLogViewModel
{


    private final NewStageService newStageService;
    private final TourLogPDFViewModel tourLogPdfViewModel;
    private final TourListService tourListService;

    public ToolBarTourLogViewModel(Publisher publisher, TourLogService tourLogService, TourListService tourListService) {
        this.newStageService = new NewStageService();
        this.tourListService=tourListService;
        this.tourLogPdfViewModel = new TourLogPDFViewModel(tourLogService, tourListService);
    }

    public void onAdd() {
        newStageService.loadFXML("add-route-log-view.fxml");
    }
    public void onModify() {
        newStageService.loadFXML("modify-tour-log-view.fxml");
    }
    public void onGenerateTourLogsPdf() {
        tourLogPdfViewModel.generatePdf();
    }
}
