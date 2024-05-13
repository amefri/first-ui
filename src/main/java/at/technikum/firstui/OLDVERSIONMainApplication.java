package at.technikum.firstui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OLDVERSIONMainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OLDVERSIONMainApplication.class.getResource("list-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show(); //TODO: localisation as unique feature, add a button to switch between languages (German and English) how to do check repository from class
    }

    public static void main(String[] args) {
        launch();
    }
}