package at.technikum.firstui.services;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.repository.TourListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TourListServiceTest {

    @Mock
    private TourListRepository tourRepository;

    @InjectMocks
    private TourListService tourListService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTour() {
        Tours tour = new Tours("Tour 1", "Description", "From", "To", "Car", "100 km", "2 hours", "/path/to/image");

        tourListService.addTour(tour);

        verify(tourRepository, times(1)).save(tour);
    }

    @Test
    public void testModifyTour() {
        Tours tour = new Tours("Tour 1", "Description", "From", "To", "Car", "100 km", "2 hours", "/path/to/image");

        tourListService.modifyTour(tour);

        verify(tourRepository, times(1)).modify(tour);
    }

    @Test
    public void testGetTours() {
        List<Tours> tours = List.of(new Tours("Tour 1", "Desc 1", "From 1", "To 1", "Car", "100 km", "2 hours", "/image1"),
                new Tours("Tour 2", "Desc 2", "From 2", "To 2", "Bus", "150 km", "3 hours", "/image2"));
        when(tourRepository.findAll()).thenReturn(tours);

        List<Tours> result = tourListService.getTours();

        assertEquals(2, result.size());
        assertEquals("Tour 1", result.get(0).getName());
        assertEquals("Tour 2", result.get(1).getName());
    }

    @Test
    public void testFindToursBySearchTerm() {
        List<Tours> tours = List.of(new Tours("Tour 1", "Desc 1", "From 1", "To 1", "Car", "100 km", "2 hours", "/image1"),
                new Tours("Tour 2", "Desc 2", "From 2", "To 2", "Bus", "150 km", "3 hours", "/image2"));
        when(tourRepository.findAll()).thenReturn(tours);

        List<Tours> result = tourListService.findToursBySearchTerm("tour");

        assertEquals(2, result.size());
    }

    @Test
    public void testDeleteTourByNameExisting() {
        String tourName = "Tour 1";
        when(tourRepository.findByName(tourName)).thenReturn(Optional.of(new Tours()));

        boolean result = tourListService.deleteTourByName(tourName);

        assertTrue(result);
        verify(tourRepository, times(1)).deleteByName(tourName);
    }

    @Test
    public void testDeleteTourByNameNonExisting() {
        String tourName = "Non-existent Tour";
        when(tourRepository.findByName(tourName)).thenReturn(Optional.empty());

        boolean result = tourListService.deleteTourByName(tourName);

        assertFalse(result);
        verify(tourRepository, never()).deleteByName(tourName);
    }

}
