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

    public void onDelete() {
        System.out.println("Delete action triggered");
    }

    public void onMore() {
        System.out.println("More options");
    }
}
