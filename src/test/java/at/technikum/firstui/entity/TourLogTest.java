package at.technikum.firstui.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.beans.property.StringProperty;

public class TourLogTest {

    private TourLog tourLog;
    private Tours mockTour;

    @BeforeEach
    public void setUp() {
        mockTour = mock(Tours.class);
        when(mockTour.getDistance()).thenReturn("10 km");
        when(mockTour.getEstimatedTime()).thenReturn("2 hours");

        tourLog = new TourLog("Test Name", "2023-06-28", "5", "Test Info");
        tourLog.setTour(mockTour);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("Test Name", tourLog.getName());
        assertEquals("2023-06-28", tourLog.getDate());
        assertEquals("5", tourLog.getRating());
        assertEquals("Test Info", tourLog.getInfo());

        tourLog.setName("New Name");
        assertEquals("New Name", tourLog.getName());

        tourLog.setDate("2023-06-29");
        assertEquals("2023-06-29", tourLog.getDate());

        tourLog.setRating("4");
        assertEquals("4", tourLog.getRating());

        tourLog.setInfo("New Info");
        assertEquals("New Info", tourLog.getInfo());
    }

    @Test
    public void testTourAssociation() {
        assertEquals(mockTour, tourLog.getTour());

        Tours newTour = new Tours();
        tourLog.setTour(newTour);
        assertEquals(newTour, tourLog.getTour());
    }

    @Test
    public void testTransientProperties() {
        assertEquals("10 km", tourLog.getDistance());
        assertEquals("2 hours", tourLog.getDuration());
    }

    @Test
    public void testStringProperties() {
        StringProperty nameProperty = tourLog.nameProperty();
        assertEquals("Test Name", nameProperty.get());

        StringProperty dateProperty = tourLog.dateProperty();
        assertEquals("2023-06-28", dateProperty.get());

        StringProperty ratingProperty = tourLog.ratingProperty();
        assertEquals("5", ratingProperty.get());

        StringProperty infoProperty = tourLog.infoProperty();
        assertEquals("Test Info", infoProperty.get());

        StringProperty distanceProperty = tourLog.distanceProperty();
        assertEquals("10 km", distanceProperty.get());

        StringProperty durationProperty = tourLog.durationProperty();
        assertEquals("2 hours", durationProperty.get());
    }

    @Test
    public void testConstructor() {
        TourLog newTourLog = new TourLog("New Tour", "2023-06-30", "3", "Info about tour");
        assertEquals("New Tour", newTourLog.getName());
        assertEquals("2023-06-30", newTourLog.getDate());
        assertEquals("3", newTourLog.getRating());
        assertEquals("Info about tour", newTourLog.getInfo());
    }
}
