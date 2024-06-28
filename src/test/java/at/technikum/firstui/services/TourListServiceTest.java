package at.technikum.firstui.services;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.TourListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourListServiceTest {

    private TourListService tourListService;
    private TourListRepository tourRepository;

    @BeforeEach
    void setUp() {
        tourRepository = mock(TourListRepository.class);
        tourListService = new TourListService(tourRepository);
    }

    @Test
    void testAddTour() {
        Tours tour = new Tours("Test Tour", "Description", "From", "To", "Transport", "Distance", "Time", "Path");
        tourListService.addTour(tour);
        verify(tourRepository, times(1)).save(tour);
    }

    @Test
    void testModifyTour() {
        Tours tour = new Tours("Test Tour", "Description", "From", "To", "Transport", "Distance", "Time", "Path");
        tourListService.modifyTour(tour);
        verify(tourRepository, times(1)).modify(tour);
    }

    @Test
    void testGetTours() {
        List<Tours> toursList = Arrays.asList(
                new Tours("Tour1", "Desc1", "From1", "To1", "Car", "100km", "2hrs", "Path1"),
                new Tours("Tour2", "Desc2", "From2", "To2", "Bike", "50km", "1hr", "Path2")
        );
        when(tourRepository.findAll()).thenReturn(toursList);

        List<Tours> result = tourListService.getTours();
        assertEquals(2, result.size());
        assertEquals("Tour1", result.get(0).getName());
        assertEquals("Tour2", result.get(1).getName());
    }

    @Test
    void testDeleteTourByName() {
        String tourName = "TourToDelete";
        when(tourRepository.findByName(tourName)).thenReturn(Optional.of(new Tours()));

        boolean result = tourListService.deleteTourByName(tourName);
        assertTrue(result);
        verify(tourRepository, times(1)).deleteByName(tourName);

        when(tourRepository.findByName(tourName)).thenReturn(Optional.empty());
        result = tourListService.deleteTourByName(tourName);
        assertFalse(result);
    }

    @Test
    void testSetAndGetCurrentlySelected() {
        Tours tour = new Tours("Test Tour", "Description", "From", "To", "Transport", "Distance", "Time", "Path");
        tourListService.setCurrentlySelected(tour);
        assertEquals(tour, tourListService.getCurrentlySelected());
    }

    @Test
    void testSetIsSelected() {
        tourListService.setIsSelected(true);
        assertTrue(tourListService.isSelected());

        tourListService.setIsSelected(false);
        assertFalse(tourListService.isSelected());
    }

    @Test
    void testSetImageURL() throws Exception {
        Tours tour = new Tours("Test Tour", "Description", "From", "To", "Transport", "Distance", "Time", "Path");
        URL url = new URL("http://example.com/image.jpg");
        tourListService.setImageURL(tour, url);
        verify(tourRepository, times(1)).saveTourURL(tour, url);
    }

    @Test
    void testSaveTourImage() {
        Tours tour = new Tours("Test Tour", "Description", "From", "To", "Transport", "Distance", "Time", "Path");
        byte[] imageBytes = {1, 2, 3, 4};
        tourListService.saveTourImage(tour, imageBytes);

        ArgumentCaptor<Tours> captor = ArgumentCaptor.forClass(Tours.class);
        verify(tourRepository, times(1)).save(captor.capture());

        assertArrayEquals(imageBytes, captor.getValue().getTourImage());
    }

    @Test
    void testSetDistance() {
        Tours tour = new Tours("Test Tour", "Description", "From", "To", "Transport", "Distance", "Time", "Path");
        tourListService.setDistance(tour, 150.5);

        verify(tourRepository, times(1)).saveTourDistance(tour, 150.5);
    }

    @Test
    void testSetDuration() {
        Tours tour = new Tours("Test Tour", "Description", "From", "To", "Transport", "Distance", "Time", "Path");
        tourListService.setDuration(tour, 3.5);

        verify(tourRepository, times(1)).saveTourDuration(tour, 3.5);
    }

    @Test
    void testGetTourByName() {
        String tourName = "TourByName";
        Tours tour = new Tours(tourName, "Description", "From", "To", "Transport", "Distance", "Time", "Path");
        when(tourRepository.findByName(tourName)).thenReturn(Optional.of(tour));

        Tours result = tourListService.getTourByName(tourName);
        assertNotNull(result);
        assertEquals(tourName, result.getName());

        when(tourRepository.findByName(tourName)).thenReturn(Optional.empty());
        result = tourListService.getTourByName(tourName);
        assertNull(result);
    }
}
