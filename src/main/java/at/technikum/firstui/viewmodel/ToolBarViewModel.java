package at.technikum.firstui.viewmodel;

import at.technikum.firstui.services.NewStageService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ToolBarViewModel {

    private final NewStageService newStageService;

    public ToolBarViewModel() {
        this.newStageService = new NewStageService();
    }

    public void onAdd() {
        newStageService.loadFXML("/at/technikum/firstui/add-stage-view.fxml");
    }

    public void onDelete() {
        System.out.println("Delete action triggered");
    }

    public void onMore() {
        System.out.println("More options");
    }
}
