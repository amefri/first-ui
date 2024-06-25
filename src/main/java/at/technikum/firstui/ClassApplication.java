package at.technikum.firstui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class
ClassApplication extends Application {
    Locale locale = Locale.ENGLISH;
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainView = FXMLDependencyInjector.load("example-main-view.fxml", locale);
        Scene scene = new Scene(mainView);
        stage.setTitle("Very Fancy Tour Planner");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    }


