package at.technikum.firstui.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ToursTest {

    private Tours tour;
    private TourLog tourLog1;
    private TourLog tourLog2;

    @BeforeEach
    public void setUp() {
        tour = new Tours("Tour 1", "Description 1", "From 1", "To 1", "Car", "100 km", "2 hours", "/path/to/image");
        tourLog1 = new TourLog("Log 1", "2023-06-28", "5", "Info 1");
        tourLog2 = new TourLog("Log 2", "2023-06-29", "4", "Info 2");
    }

    @Test
    public void testGettersAndSetters() {
        // Test initial values
        assertEquals("Tour 1", tour.getName());
        assertEquals("Description 1", tour.getDescription());
        assertEquals("From 1", tour.getFrom());
        assertEquals("To 1", tour.getTo());
        assertEquals("Car", tour.getTransportType());
        assertEquals("100 km", tour.getDistance());
        assertEquals("2 hours", tour.getEstimatedTime());
        assertEquals("/path/to/image", tour.getImagePath());

        // Test setting new values
        tour.setName("New Tour");
        assertEquals("New Tour", tour.getName());

        tour.setDescription("New Description");
        assertEquals("New Description", tour.getDescription());

        tour.setFrom("New From");
        assertEquals("New From", tour.getFrom());

        tour.setTo("New To");
        assertEquals("New To", tour.getTo());

        tour.setTransportType("Bike");
        assertEquals("Bike", tour.getTransportType());

        tour.setDistance("200 km");
        assertEquals("200 km", tour.getDistance());

        tour.setEstimatedTime("4 hours");
        assertEquals("4 hours", tour.getEstimatedTime());

        tour.setImagePath("/new/path/to/image");
        assertEquals("/new/path/to/image", tour.getImagePath());
    }

    @Test
    public void testTourLogs() {
        // Test adding tour logs
        tour.addTourLog(tourLog1);
        assertEquals(1, tour.getTourLogs().size());
        assertEquals(tour, tourLog1.getTour());

        tour.addTourLog(tourLog2);
        assertEquals(2, tour.getTourLogs().size());
        assertEquals(tour, tourLog2.getTour());

        // Test removing tour logs
        tour.removeTourLog(tourLog1);
        assertEquals(1, tour.getTourLogs().size());
        assertNull(tourLog1.getTour());

        tour.removeTourLog(tourLog2);
        assertEquals(0, tour.getTourLogs().size());
        assertNull(tourLog2.getTour());
    }

    @Test
    public void testConstructor() {
        Tours newTour = new Tours("Tour 2", "Description 2", "From 2", "To 2", "Train", "150 km", "3 hours", "/path/to/another/image");
        assertEquals("Tour 2", newTour.getName());
        assertEquals("Description 2", newTour.getDescription());
        assertEquals("From 2", newTour.getFrom());
        assertEquals("To 2", newTour.getTo());
        assertEquals("Train", newTour.getTransportType());
        assertEquals("150 km", newTour.getDistance());
        assertEquals("3 hours", newTour.getEstimatedTime());
        assertEquals("/path/to/another/image", newTour.getImagePath());
    }

    @Test
    public void testDefaultConstructor() {
        Tours newTour = new Tours();
        assertNull(newTour.getName());
        assertNull(newTour.getDescription());
        assertNull(newTour.getFrom());
        assertNull(newTour.getTo());
        assertNull(newTour.getTransportType());
        assertNull(newTour.getDistance());
        assertNull(newTour.getEstimatedTime());
        assertNull(newTour.getImagePath());
        assertTrue(newTour.getTourLogs().isEmpty());
    }

    @Test
    public void testGetFieldsAsList() {
        List<Object> fields = tour.getFieldsAsList();
        assertEquals(8, fields.size());
        assertEquals("Tour 1", fields.get(0));
        assertEquals("Description 1", fields.get(1));
        assertEquals("From 1", fields.get(2));
        assertEquals("To 1", fields.get(3));
        assertEquals("Car", fields.get(4));
        assertEquals("100 km", fields.get(5));
        assertEquals("2 hours", fields.get(6));
        assertEquals("/path/to/image", fields.get(7));
    }
}
