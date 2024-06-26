package at.technikum.firstui.entity;

import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourLogTest {

    @Test
    void testDefaultConstructor() {
        TourLog tourLog = new TourLog();
        assertNull(tourLog.getId());
        assertNull(tourLog.getName());
        assertNull(tourLog.getDate());
        assertNull(tourLog.getDuration());
        assertNull(tourLog.getDistance());
        assertNull(tourLog.getTour());
    }

    @Test
    void testParameterizedConstructor() {
        String name = "Tour1";
        String date = "2024-06-26";
        String duration = "2 hours";
        String distance = "5 km";

        TourLog tourLog = new TourLog(name, date, duration, distance);

        assertNull(tourLog.getId());
        assertEquals(name, tourLog.getName());
        assertEquals(date, tourLog.getDate());
        assertEquals(duration, tourLog.getDuration());
        assertEquals(distance, tourLog.getDistance());
        assertNull(tourLog.getTour());
    }

    @Test
    void testGetAndSetId() {
        TourLog tourLog = new TourLog();
        Long id = 1L;
        tourLog.setId(id);
        assertEquals(id, tourLog.getId());
    }

    @Test
    void testGetAndSetName() {
        TourLog tourLog = new TourLog();
        String name = "New Tour";
        tourLog.setName(name);
        assertEquals(name, tourLog.getName());
    }

    @Test
    void testGetAndSetDate() {
        TourLog tourLog = new TourLog();
        String date = "2024-06-26";
        tourLog.setDate(date);
        assertEquals(date, tourLog.getDate());
    }

    @Test
    void testGetAndSetDuration() {
        TourLog tourLog = new TourLog();
        String duration = "2 hours";
        tourLog.setDuration(duration);
        assertEquals(duration, tourLog.getDuration());
    }

    @Test
    void testGetAndSetDistance() {
        TourLog tourLog = new TourLog();
        String distance = "10 km";
        tourLog.setDistance(distance);
        assertEquals(distance, tourLog.getDistance());
    }

    @Test
    void testGetAndSetTour() {
        TourLog tourLog = new TourLog();
        Tours tour = new Tours(); // Assuming Tours is another entity with a default constructor
        tourLog.setTour(tour);
        assertEquals(tour, tourLog.getTour());
    }

    @Test
    void testNameProperty() {
        TourLog tourLog = new TourLog();
        String name = "Property Tour";
        tourLog.setName(name);
        StringProperty nameProperty = tourLog.nameProperty();
        assertEquals(name, nameProperty.get());
    }

    @Test
    void testDateProperty() {
        TourLog tourLog = new TourLog();
        String date = "2024-06-26";
        tourLog.setDate(date);
        StringProperty dateProperty = tourLog.dateProperty();
        assertEquals(date, dateProperty.get());
    }

    @Test
    void testDurationProperty() {
        TourLog tourLog = new TourLog();
        String duration = "3 hours";
        tourLog.setDuration(duration);
        StringProperty durationProperty = tourLog.durationProperty();
        assertEquals(duration, durationProperty.get());
    }

    @Test
    void testDistanceProperty() {
        TourLog tourLog = new TourLog();
        String distance = "15 km";
        tourLog.setDistance(distance);
        StringProperty distanceProperty = tourLog.distanceProperty();
        assertEquals(distance, distanceProperty.get());
    }
}
