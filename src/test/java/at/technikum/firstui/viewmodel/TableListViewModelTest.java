package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourLogService;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableListViewModelTest {

    private TableListViewModel viewModel;
    private TourLogService tourLogService;
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        tourLogService = Mockito.mock(TourLogService.class);
        publisher = Mockito.mock(Publisher.class);
        viewModel = new TableListViewModel(publisher, tourLogService);
    }

    @Test
    void testInitialState() {
        assertTrue(viewModel.getTourLogs().isEmpty());
        assertEquals(0, viewModel.selectedAddTourProperty().get());
    }

    @Test
    void testLoadTourLogs() {
        List<TourLog> logs = Arrays.asList(
                new TourLog("Log1", "2023-06-25", "1h", "10km"),
                new TourLog("Log2", "2023-06-26", "2h", "20km")
        );
        when(tourLogService.getAllTourLogs()).thenReturn(logs);

        viewModel.getTourLogs().clear(); // clear any pre-existing logs
        viewModel.loadTourLogs();

        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertEquals(2, tourLogs.size());
        assertEquals("Log1", tourLogs.get(0).getName());
        assertEquals("Log2", tourLogs.get(1).getName());
    }

    @Test
    void testAddToTourLogs() {
        TourLog newLog = new TourLog("New Log", "2023-06-27", "3h", "30km");

        viewModel.addToTourLogs(newLog);

        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertEquals(1, tourLogs.size());
        assertEquals("New Log", tourLogs.get(0).getName());
    }

    @Test
    void testDeleteSelectedTour() {
        TourLog log1 = new TourLog("Log1", "2023-06-25", "1h", "10km");
        log1.setId(1L);
        TourLog log2 = new TourLog("Log2", "2023-06-26", "2h", "20km");
        log2.setId(2L);

        viewModel.getTourLogs().addAll(log1, log2);
        viewModel.selectedAddTourProperty().set(0);

        when(tourLogService.deleteTourById(1L)).thenReturn(true);

        viewModel.deleteSelectedTour();

        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertEquals(1, tourLogs.size());
        assertEquals("Log2", tourLogs.get(0).getName());
        verify(tourLogService).deleteTourById(1L);
    }

    @Test
    void testUpdateTourLogs() {
        Long tourId = 1L;
        List<TourLog> logs = Arrays.asList(
                new TourLog("Log1", "2023-06-25", "1h", "10km"),
                new TourLog("Log2", "2023-06-26", "2h", "20km")
        );

        when(tourLogService.getTourLogsByTourId(tourId)).thenReturn(logs);

        viewModel.updateTourLogs(tourId);

        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertEquals(2, tourLogs.size());
        assertEquals("Log1", tourLogs.get(0).getName());
        assertEquals("Log2", tourLogs.get(1).getName());
    }

    @Test
    void testUpdateTourLogsNoLogs() {
        Long tourId = 1L;

        when(tourLogService.getTourLogsByTourId(tourId)).thenReturn(Collections.emptyList());

        viewModel.updateTourLogs(tourId);

        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertTrue(tourLogs.isEmpty());
    }

    @Test
    void testSelectTourLogIndex() {
        TourLog log1 = new TourLog("Log1", "2023-06-25", "1h", "10km");
        log1.setId(1L);
        TourLog log2 = new TourLog("Log2", "2023-06-26", "2h", "20km");
        log2.setId(2L);

        viewModel.getTourLogs().addAll(log1, log2);

        viewModel.selectedAddTourProperty().set(1);

        verify(tourLogService).setCurrentlySelected(log2);
        verify(tourLogService).setIsSelected(true);
    }
}
