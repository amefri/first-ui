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

    public void onDelete() {
        System.out.println("Delete action triggered");
    }

    public void onMore() {
        System.out.println("More options");
    }
}
