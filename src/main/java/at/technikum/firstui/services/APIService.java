package at.technikum.firstui.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class APIService {

    private static final String API_KEY = "5b3ce3597851110001cf6248040d24d7f4794453983f28f9565e16d4";

    public static List<double[]> geocodePlace(String place) throws IOException {
        String urlString = "https://api.openrouteservice.org/geocode/search?api_key=" + API_KEY + "&text=" + URLEncoder.encode(place, StandardCharsets.UTF_8);
        URL url = new URL(urlString);
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

        return coordinates;
    }

    public static JsonNode fetchRoute(List<double[]> coordinates) throws IOException {
        String urlString = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";
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

        return rootNode;
    }

    public void saveURL(URL url){};
}
