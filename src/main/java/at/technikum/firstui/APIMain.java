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
    public void start(Stage primaryStage) throws Exception{
        showStage(primaryStage);
        hostServices = getHostServices();
    }

    public static Parent showStage(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(APIMain.class.getResource("map-view.fxml"));
        primaryStage.setTitle("Highscore");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setMinWidth(400);
        primaryStage.show();
        return root;
    }

    public static void showMapInDefaultBrowser(){
        String url = APIMain.class.getResource("/at/technikum/firstui/leaflet.html").toExternalForm();
        hostServices.showDocument(url);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
