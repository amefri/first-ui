package at.technikum.firstui.services;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.repository.TourListRepository;
import at.technikum.firstui.repository.TourLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourLogServiceTest {

    private TourLogService tourLogService;
    private TourLogRepository tourLogRepository;
    private TourListRepository tourListRepository;

    @BeforeEach
    void setUp() {
        tourLogRepository = mock(TourLogRepository.class);
        tourListRepository = mock(TourListRepository.class);
        tourLogService = new TourLogService(tourLogRepository, tourListRepository);
    }

    @Test
    void testAddTourLog() {
        TourLog tourLog = new TourLog("Test Log", "2023-01-01", "2h", "10km");
        tourLogService.addTourLog(tourLog);
        verify(tourLogRepository, times(1)).save(tourLog);
    }

    @Test
    void testDeleteTourById() {
        Long tourLogId = 1L;
        TourLog tourLog = new TourLog("Test Log", "2023-01-01", "2h", "10km");
        when(tourLogRepository.findById(tourLogId)).thenReturn(tourLog);

        boolean result = tourLogService.deleteTourById(tourLogId);
        assertTrue(result);
        verify(tourLogRepository, times(1)).deleteTourLog(tourLog);

        when(tourLogRepository.findById(tourLogId)).thenReturn(null);
        result = tourLogService.deleteTourById(tourLogId);
        assertFalse(result);
    }

    @Test
    void testSetAndGetCurrentlySelected() {
        TourLog tourLog = new TourLog("Test Log", "2023-01-01", "2h", "10km");
        tourLogService.setCurrentlySelected(tourLog);
        assertEquals(tourLog, tourLogService.getCurrentlySelected());
    }

    @Test
    void testSetIsSelected() {
        tourLogService.setIsSelected(true);
        assertTrue(tourLogService.isSelected());

        tourLogService.setIsSelected(false);
        assertFalse(tourLogService.isSelected());
    }

    @Test
    void testGetTourLogsByTourName() {
        String tourName = "Test Tour";
        List<TourLog> tourLogs = Arrays.asList(
                new TourLog("Log1", "2023-01-01", "1h", "5km"),
                new TourLog("Log2", "2023-01-02", "2h", "10km")
        );
        when(tourLogRepository.findByTourName(tourName)).thenReturn(tourLogs);

        List<TourLog> result = tourLogService.getTourLogsByTourName(tourName);
        assertEquals(2, result.size());
        assertEquals("Log1", result.get(0).getName());
        assertEquals("Log2", result.get(1).getName());
    }

    @Test
    void testGetAllTourLogs() {
        List<TourLog> tourLogs = Arrays.asList(
                new TourLog("Log1", "2023-01-01", "1h", "5km"),
                new TourLog("Log2", "2023-01-02", "2h", "10km")
        );
        when(tourLogRepository.findAll()).thenReturn(tourLogs);

        List<TourLog> result = tourLogService.getAllTourLogs();
        assertEquals(2, result.size());
        assertEquals("Log1", result.get(0).getName());
        assertEquals("Log2", result.get(1).getName());
    }

    @Test
    void testGetTourLogsByTourId() {
        Long tourId = 1L;
        List<TourLog> tourLogs = Arrays.asList(
                new TourLog("Log1", "2023-01-01", "1h", "5km"),
                new TourLog("Log2", "2023-01-02", "2h", "10km")
        );
        when(tourLogRepository.findByTourId(tourId)).thenReturn(tourLogs);

        List<TourLog> result = tourLogService.getTourLogsByTourId(tourId);
        assertEquals(2, result.size());
        assertEquals("Log1", result.get(0).getName());
        assertEquals("Log2", result.get(1).getName());
    }

    @Test
    void testModifyTourLog() {
        TourLog tourLog = new TourLog("Test Log", "2023-01-01", "2h", "10km");
        tourLogService.modifyTourLog(tourLog);
        verify(tourLogRepository, times(1)).modify(tourLog);
    }
}
