package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.NewStageService;

public class ToolBarTourLogViewModel
{


    private final NewStageService newStageService;

    public ToolBarTourLogViewModel(Publisher publisher) {
        this.newStageService = new NewStageService();
    }

    public void onAdd() {
        newStageService.loadFXML("add-route-log-view.fxml");
    }
    public void onModify() {
        newStageService.loadFXML("modify-tour-log-view.fxml");
    }
}
