package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.NewStageService;
import javafx.scene.control.ListView;

public class ToolBarViewModel {

    private final NewStageService newStageService;


    public ToolBarViewModel(Publisher publisher) {
        this.newStageService = new NewStageService();

    }

    public void onAdd() {
        newStageService.loadFXML("add-stage-view.fxml");
    }
    public void onModify() {
        newStageService.loadFXML("modify-tour-view.fxml");
    }
}
