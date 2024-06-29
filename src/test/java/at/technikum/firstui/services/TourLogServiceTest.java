package at.technikum.firstui.services;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.repository.TourListRepository;
import at.technikum.firstui.repository.TourLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TourLogServiceTest {

    @Mock
    private TourLogRepository tourLogRepository;

    @Mock
    private TourListRepository tourListRepository;

    @InjectMocks
    private TourLogService tourLogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTourLog() {
        TourLog tourLog = new TourLog("Log 1", "2024-06-30", "5", "Description");

        tourLogService.addTourLog(tourLog);

        verify(tourLogRepository, times(1)).save(tourLog);
    }

    @Test
    public void testDeleteTourByIdExisting() {
        Long tourLogId = 1L;
        TourLog tourLog = new TourLog("Log 1", "2024-06-30", "5", "Description");
        when(tourLogRepository.findById(tourLogId)).thenReturn(tourLog);

        boolean result = tourLogService.deleteTourById(tourLogId);

        assertTrue(result);
        verify(tourLogRepository, times(1)).deleteTourLog(tourLog);
    }

    @Test
    public void testDeleteTourByIdNonExisting() {
        Long tourLogId = 1L;
        when(tourLogRepository.findById(tourLogId)).thenReturn(null);

        boolean result = tourLogService.deleteTourById(tourLogId);

        assertFalse(result);
        verify(tourLogRepository, never()).deleteTourLog(any());
    }

    @Test
    public void testModifyTourLog() {
        TourLog tourLog = new TourLog("Log 1", "2024-06-30", "5", "Description");

        tourLogService.modifyTourLog(tourLog);

        verify(tourLogRepository, times(1)).modify(tourLog);
    }

    @Test
    public void testGetTourLogsByTourName() {
        String tourName = "Tour 1";
        List<TourLog> expectedLogs = List.of(new TourLog("Log 1", "2024-06-30", "5", "Description"));

        when(tourLogRepository.findByTourName(tourName)).thenReturn(expectedLogs);

        List<TourLog> result = tourLogService.getTourLogsByTourName(tourName);

        assertEquals(1, result.size());
        assertEquals("Log 1", result.get(0).getName());
    }

    @Test
    public void testGetAllTourLogs() {
        List<TourLog> expectedLogs = List.of(new TourLog("Log 1", "2024-06-30", "5", "Description"),
                new TourLog("Log 2", "2024-07-01", "4", "Another description"));

        when(tourLogRepository.findAll()).thenReturn(expectedLogs);

        List<TourLog> result = tourLogService.getAllTourLogs();

        assertEquals(2, result.size());
        assertEquals("Log 1", result.get(0).getName());
        assertEquals("Log 2", result.get(1).getName());
    }

    @Test
    public void testGetTourLogsByTourId() {
        Long tourId = 1L;
        List<TourLog> expectedLogs = List.of(new TourLog("Log 1", "2024-06-30", "5", "Description"));

        when(tourLogRepository.findByTourId(tourId)).thenReturn(expectedLogs);

        List<TourLog> result = tourLogService.getTourLogsByTourId(tourId);

        assertEquals(1, result.size());
        assertEquals("Log 1", result.get(0).getName());
    }

    @Test
    public void testSetAndGetCurrentlySelected() {
        TourLog tourLog = new TourLog("Log 1", "2024-06-30", "5", "Description");

        tourLogService.setCurrentlySelected(tourLog);

        assertEquals(tourLog, tourLogService.getCurrentlySelected());
    }

}
