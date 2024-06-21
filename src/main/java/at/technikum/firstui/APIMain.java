package at.technikum.firstui;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class APIMain extends Application {

    private static HostServices hostServices;

    @Override
    public void start(Stage primaryStage) throws Exception {
        hostServices = getHostServices();
        showStage(primaryStage);
    }

    public static void showStage(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(APIMain.class.getResource("/at/technikum/firstui/map-view.fxml"));
        primaryStage.setTitle("Route");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static HostServices getHostServicesInstance() {
        return hostServices;
    }
}
