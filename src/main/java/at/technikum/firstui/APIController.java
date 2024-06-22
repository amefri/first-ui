package at.technikum.firstui;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.APIService;
import at.technikum.firstui.services.TourListService;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIController {

    @FXML
    private TextField placesInput;

    @FXML
    private WebView webView;


    private TourListService tourListService;
    private Publisher publisher;
    private APIService apiService;

    public APIController(TourListService tourListService,Publisher publisher, APIService apiService) {
        this.tourListService = tourListService;
        this.apiService = new APIService(tourListService, publisher);
        this.publisher = publisher;
    }

    @FXML
    private void showBrowser(ActionEvent event) {
        Tours tour = tourListService.getCurrentlySelected();
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
        //TODO: save url in database through service method

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                String directionsJson = directions.toString();
                directionsJson = directionsJson.replaceAll("\\[(\\d+\\.\\d+), (\\d+\\.\\d+)\\]", "[$2, $1]");
                webEngine.executeScript("displayRoute(" + directionsJson + ");");
            }
        });
    }
}
