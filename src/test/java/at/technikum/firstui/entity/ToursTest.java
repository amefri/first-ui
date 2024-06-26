package at.technikum.firstui.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ToursTest {

    @Test
    void testDefaultConstructor() {
        Tours tour = new Tours();
        assertNull(tour.getId());
        assertNull(tour.getName());
        assertNull(tour.getDescription());
        assertNull(tour.getFrom());
        assertNull(tour.getTo());
        assertNull(tour.getTransportType());
        assertNull(tour.getDistance());
        assertNull(tour.getEstimatedTime());
        assertNull(tour.getImagePath());
        assertNotNull(tour.getTourLogs());
        assertTrue(tour.getTourLogs().isEmpty());
        assertNull(tour.getTourImage());
    }

    @Test
    void testParameterizedConstructor() {
        String name = "Tour Name";
        String description = "Tour Description";
        String from = "Start Location";
        String to = "End Location";
        String transportType = "Car";
        String distance = "100 km";
        String estimatedTime = "2 hours";
        String imagePath = "/path/to/image";

        Tours tour = new Tours(name, description, from, to, transportType, distance, estimatedTime, imagePath);

        assertNull(tour.getId());
        assertEquals(name, tour.getName());
        assertEquals(description, tour.getDescription());
        assertEquals(from, tour.getFrom());
        assertEquals(to, tour.getTo());
        assertEquals(transportType, tour.getTransportType());
        assertEquals(distance, tour.getDistance());
        assertEquals(estimatedTime, tour.getEstimatedTime());
        assertEquals(imagePath, tour.getImagePath());
        assertNotNull(tour.getTourLogs());
        assertTrue(tour.getTourLogs().isEmpty());
        assertNull(tour.getTourImage());
    }

    @Test
    void testGetAndSetId() {
        Tours tour = new Tours();
        Long id = 1L;
        tour.setId(id);
        assertEquals(id, tour.getId());
    }

    @Test
    void testGetAndSetName() {
        Tours tour = new Tours();
        String name = "New Tour Name";
        tour.setName(name);
        assertEquals(name, tour.getName());
    }

    @Test
    void testGetAndSetDescription() {
        Tours tour = new Tours();
        String description = "New Tour Description";
        tour.setDescription(description);
        assertEquals(description, tour.getDescription());
    }

    @Test
    void testGetAndSetFrom() {
        Tours tour = new Tours();
        String from = "New Start Location";
        tour.setFrom(from);
        assertEquals(from, tour.getFrom());
    }

    @Test
    void testGetAndSetTo() {
        Tours tour = new Tours();
        String to = "New End Location";
        tour.setTo(to);
        assertEquals(to, tour.getTo());
    }

    @Test
    void testGetAndSetTransportType() {
        Tours tour = new Tours();
        String transportType = "Bike";
        tour.setTransportType(transportType);
        assertEquals(transportType, tour.getTransportType());
    }

    @Test
    void testGetAndSetDistance() {
        Tours tour = new Tours();
        String distance = "50 km";
        tour.setDistance(distance);
        assertEquals(distance, tour.getDistance());
    }

    @Test
    void testGetAndSetEstimatedTime() {
        Tours tour = new Tours();
        String estimatedTime = "1 hour";
        tour.setEstimatedTime(estimatedTime);
        assertEquals(estimatedTime, tour.getEstimatedTime());
    }

    @Test
    void testGetAndSetImagePath() {
        Tours tour = new Tours();
        String imagePath = "/new/path/to/image";
        tour.setImagePath(imagePath);
        assertEquals(imagePath, tour.getImagePath());
    }

    @Test
    void testGetAndSetTourImage() {
        Tours tour = new Tours();
        byte[] tourImage = {1, 2, 3};
        tour.setTourImage(tourImage);
        assertArrayEquals(tourImage, tour.getTourImage());
    }

    @Test
    void testAddAndRemoveTourLog() {
        Tours tour = new Tours();
        TourLog tourLog = new TourLog("Log1", "2024-06-26", "2 hours", "10 km");

        tour.addTourLog(tourLog);
        assertEquals(1, tour.getTourLogs().size());
        assertEquals(tour, tourLog.getTour());

        tour.removeTourLog(tourLog);
        assertTrue(tour.getTourLogs().isEmpty());
        assertNull(tourLog.getTour());
    }

    @Test
    void testGetFieldsAsList() {
        String name = "Tour Name";
        String description = "Tour Description";
        String from = "Start Location";
        String to = "End Location";
        String transportType = "Car";
        String distance = "100 km";
        String estimatedTime = "2 hours";
        String imagePath = "/path/to/image";

        Tours tour = new Tours(name, description, from, to, transportType, distance, estimatedTime, imagePath);
        List<Object> fields = tour.getFieldsAsList();

        assertEquals(8, fields.size());
        assertEquals(name, fields.get(0));
        assertEquals(description, fields.get(1));
        assertEquals(from, fields.get(2));
        assertEquals(to, fields.get(3));
        assertEquals(transportType, fields.get(4));
        assertEquals(distance, fields.get(5));
        assertEquals(estimatedTime, fields.get(6));
        assertEquals(imagePath, fields.get(7));
    }
}
