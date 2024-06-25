package at.technikum.firstui;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.event.Subscriber;
import at.technikum.firstui.services.APIService;
import at.technikum.firstui.services.TourListService;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.animation.PauseTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIController implements Subscriber {

    @FXML
    private TextField placesInput;

    @FXML
    private WebView webView;

    private TourListService tourListService;
    private Publisher publisher;
    private APIService apiService;

    public APIController(TourListService tourListService, Publisher publisher, APIService apiService) {
        this.tourListService = tourListService;
        this.apiService = apiService;
        this.publisher = publisher;
        this.publisher.subscribe(Event.SELECTED_TOUR_CHANGED, this);
    }

    @FXML
    private void showBrowser(ActionEvent event) {
        showRouteForCurrentTour();
    }

    @Override
    public void notify(String message) {
        showRouteForCurrentTour();
    }

    private void showRouteForCurrentTour() {
        Tours tour = tourListService.getCurrentlySelected();
        if (tour == null) {
            System.out.println("No tour selected.");
            return;
        }

        String places = tour.getFrom() + ";" + tour.getTo();
        String[] placesArray = places.split(";");

        List<double[]> coordinates = new ArrayList<>();

        for (String place : placesArray) {
            try {
                List<double[]> coords = apiService.geocodePlace(place);
                if (!coords.isEmpty()) {
                    coordinates.add(coords.get(0));
                } else {
                    System.out.println("Could not find coordinates for " + place);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            JsonNode directions = apiService.fetchRoute(coordinates);
            displayRoute(directions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayRoute(JsonNode directions) {
        WebEngine webEngine = webView.getEngine();
        URL url = getClass().getResource("/at/technikum/firstui/leaflet.html");
        webEngine.load(url.toString());

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                // Bind Java method to be called from JavaScript
                webEngine.executeScript("window.javaApp = { saveMap: function() { app.saveMap(); } }");

                String directionsJson = directions.toString();
                directionsJson = directionsJson.replaceAll("\\[(\\d+\\.\\d+), (\\d+\\.\\d+)\\]", "[$2, $1]");
                webEngine.executeScript("displayRoute(" + directionsJson + ");");
                PauseTransition pause = new PauseTransition(Duration.seconds(4));
                pause.setOnFinished(event -> saveMap());
                pause.play();
            }
        });
    }

    public void saveMap() {
        Tours tour = tourListService.getCurrentlySelected();
        if (tour == null) {
            System.out.println("No tour selected.");
            return;
        }

        WritableImage snapshot = webView.snapshot(null, null);
        String tourName = tour.getName().replaceAll("\\s+", "_"); // Replace spaces with underscores
        File file = new File("src/main/java/at/technikum/firstui/images/" + tourName + "_map_snapshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
            System.out.println("Map saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
