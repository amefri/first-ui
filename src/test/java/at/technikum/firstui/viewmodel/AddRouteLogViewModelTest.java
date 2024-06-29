package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddRouteLogViewModelTest {

    @Mock
    private Publisher publisher;

    @Mock
    private TourLogService tourLogService;

    @Mock
    private TourListService tourListService;

    @InjectMocks
    private AddRouteLogViewModel viewModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTourLog_Success() {
        // Mock currently selected tour
        Tours selectedTour = new Tours("Tour 1", "Description", "From", "To", "Transport", "100 km", "2 hours", "image.jpg");
        when(tourListService.isSelected()).thenReturn(true);
        when(tourListService.getCurrentlySelected()).thenReturn(selectedTour);

        // Set up view model properties
        viewModel.nameProperty().set("Log 1");
        viewModel.dateProperty().set("2024-06-30");
        viewModel.durationProperty().set("2 hours");
        viewModel.distanceProperty().set("100 km");

        // Simulate adding a tour log
        viewModel.addTourLog();

        // Verify that tour log is added via tourLogService
        verify(tourLogService, times(1)).addTourLog(any(TourLog.class));

        // Verify that event is published
        verify(publisher, times(1)).publish(eq(Event.TOUR_LOG_ADDED), any(TourLog.class));

        // Assert that properties are cleared after adding
        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.dateProperty().get());
        assertEquals("", viewModel.durationProperty().get());
        assertEquals("", viewModel.distanceProperty().get());
    }


    @Test
    public void testAddTourLog_NoTourSelected() {
        // Mock that no tour is selected
        when(tourListService.isSelected()).thenReturn(false);

        // Set up view model properties
        viewModel.nameProperty().set("Log 1");
        viewModel.dateProperty().set("2024-06-30");
        viewModel.durationProperty().set("2 hours");
        viewModel.distanceProperty().set("100 km");

        // Simulate adding a tour log
        viewModel.addTourLog();

        // Verify that tour log service and publisher are not called
        verify(tourLogService, never()).addTourLog(any());
        verify(publisher, never()).publish(any(), any());

        // Assert that properties remain unchanged
        assertEquals("Log 1", viewModel.nameProperty().get());
        assertEquals("2024-06-30", viewModel.dateProperty().get());
        assertEquals("2 hours", viewModel.durationProperty().get());
        assertEquals("100 km", viewModel.distanceProperty().get());
    }

    @Test
    public void testAddTourLog_ButtonDisabled() {
        // Mock currently selected tour
        Tours selectedTour = new Tours("Tour 1", "Description", "From", "To", "Transport", "100 km", "2 hours", "image.jpg");
        when(tourListService.isSelected()).thenReturn(true);
        when(tourListService.getCurrentlySelected()).thenReturn(selectedTour);

        // Set up view model properties with empty values
        viewModel.nameProperty().set("");
        viewModel.dateProperty().set("");
        viewModel.durationProperty().set("");
        viewModel.distanceProperty().set("");

        // Simulate adding a tour log
        viewModel.addTourLog();

        // Verify that tour log service and publisher are not called
        verify(tourLogService, never()).addTourLog(any());
        verify(publisher, never()).publish(any(), any());

        // Assert that properties remain unchanged
        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.dateProperty().get());
        assertEquals("", viewModel.durationProperty().get());
        assertEquals("", viewModel.distanceProperty().get());
    }


}
