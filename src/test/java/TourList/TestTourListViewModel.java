package TourList;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import at.technikum.firstui.viewmodel.TourListViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestTourListViewModel {
    private TourListViewModel viewModel;
    private TourListService tourListService;

    private TourLogService tourLogService;
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        tourListService = mock(TourListService.class);
        tourLogService= mock (TourLogService.class);
        publisher = mock(Publisher.class);
        viewModel = new TourListViewModel(publisher, tourListService, tourLogService);
        viewModel.setSelectedAddTourIndex(1);
    }


    @Test
    public void testAddToTourList() {
        // Arrange
        String tourName = "Tour1";
        int initialSize = viewModel.getTourList().size();

        // Act
        viewModel.addToTourList(tourName);

        // Assert
        int newSize = viewModel.getTourList().size();
        assertTrue(viewModel.getTourList().contains(tourName));
        assertEquals(initialSize + 1, newSize);
    }

    @Test
    public void testDeleteSelectedTour() {
        // Arrange
        String tourName = "TourToDelete";
        viewModel.addToTourList(tourName);
        int initialSize = viewModel.getTourList().size();
        viewModel.setSelectedAddTourIndex(initialSize - 1);
        when(tourListService.deleteTourByName(tourName)).thenReturn(true);

        // Act
        viewModel.deleteSelectedTour();

        // Assert
        int newSize = viewModel.getTourList().size();
        assertEquals(initialSize - 1, newSize);
        assertFalse(viewModel.getTourList().contains(tourName));
        verify(tourListService, times(1)).deleteTourByName(tourName);
    }
}