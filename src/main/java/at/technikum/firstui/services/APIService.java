package at.technikum.firstui.services;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.event.Subscriber;
import at.technikum.firstui.repository.TourListRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class APIService implements ObjectSubscriber{

    static ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
    static String API_KEY = resourceBundle.getString("api_key");

    //private static final String API_KEY = "5b3ce3597851110001cf6248040d24d7f4794453983f28f9565e16d4";
    private static TourListService tourListService;

    private final Publisher publisher;

    public APIService(TourListService tourListService, Publisher publisher) {
        this.tourListService = tourListService;
        this.publisher = publisher;
        this.publisher.subscribe(Event.SELECTED_TOUR_CHANGED,  this);
    }

    public static List<double[]> geocodePlace(String place) throws IOException {
        String urlString = "https://api.openrouteservice.org/geocode/search?api_key=" + API_KEY + "&text=" + URLEncoder.encode(place, StandardCharsets.UTF_8);
        URL url = new URL(urlString);
        Tours tour = tourListService.getCurrentlySelected();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(conn.getInputStream());

        conn.disconnect();

        List<double[]> coordinates = new ArrayList<>();
        if (rootNode.has("features") && rootNode.get("features").isArray()) {
            for (JsonNode feature : rootNode.get("features")) {
                if (feature.has("geometry") && feature.get("geometry").has("coordinates")) {
                    double lon = feature.get("geometry").get("coordinates").get(0).asDouble();
                    double lat = feature.get("geometry").get("coordinates").get(1).asDouble();
                    coordinates.add(new double[]{lon, lat});
                }
            }
        }
        tourListService.setImageURL(tour,url);

        return coordinates;
    }

    public static JsonNode fetchRoute(List<double[]> coordinates) throws IOException {
        Tours tour = tourListService.getCurrentlySelected();
        String transportType = tour.getTransportType();

        String urlString = "https://api.openrouteservice.org/v2/directions/" + transportType + "/geojson";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", API_KEY);

        ObjectMapper mapper = new ObjectMapper();
        String body = "{\"coordinates\": " + mapper.writeValueAsString(coordinates) + ", \"format\": \"json\"}";
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        JsonNode rootNode = mapper.readTree(conn.getInputStream());

        conn.disconnect();
        double distance = 0;
        double duration = 0;
        if (rootNode.has("features") && rootNode.get("features").isArray()) {
            JsonNode features = rootNode.get("features").get(0);
            if (features.has("properties") && features.get("properties").has("segments") && features.get("properties").get("segments").isArray()) {
                JsonNode segment = features.get("properties").get("segments").get(0);
                if (segment.has("distance")) {
                    distance = segment.get("distance").asDouble();
                }
                if (segment.has("duration")) {
                    duration = segment.get("duration").asDouble();
                }
            }
        }

        // Convert distance to kilometers and duration to minutes
        double distanceInKm = distance / 1000.0;
        double durationInMinutes = duration / 60.0;



        // Update the tour with distance and duration
        tourListService.setDistance(tour, distanceInKm);
        tourListService.setDuration(tour, durationInMinutes);

        return rootNode;
    }

    @Override
    public void notify(Object message) {
    }}

