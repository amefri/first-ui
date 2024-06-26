package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddRouteLogViewModelTest {

    private Publisher publisher;
    private TourLogService tourLogService;
    private TourListService tourListService;
    private AddRouteLogViewModel viewModel;

    @BeforeEach
    void setUp() {
        publisher = mock(Publisher.class);
        tourLogService = mock(TourLogService.class);
        tourListService = mock(TourListService.class);
        viewModel = new AddRouteLogViewModel(publisher, tourLogService, tourListService);
    }

    @Test
    void addTourLog() {
        // Mock data and behavior
        Tours mockTour = mock(Tours.class);
        when(tourListService.isSelected()).thenReturn(true);
        when(tourListService.getCurrentlySelected()).thenReturn(mockTour);

        // Set properties
        viewModel.nameProperty().set("Test Tour");
        viewModel.dateProperty().set("2024-06-24");
        viewModel.durationProperty().set("2 hours");
        viewModel.distanceProperty().set("10 km");

        // Call the method
        viewModel.addTourLog();

        // Verify interaction with mocks
        ArgumentCaptor<TourLog> captor = ArgumentCaptor.forClass(TourLog.class);
        verify(tourLogService).addTourLog(captor.capture());
        verify(publisher).publish(eq(Event.TOUR_LOG_ADDED), any(TourLog.class));

        // Assert the values of the TourLog
        TourLog addedTourLog = captor.getValue();
        assertEquals("Test Tour", addedTourLog.getName());
        assertEquals("2024-06-24", addedTourLog.getDate());
        assertEquals("2 hours", addedTourLog.getDuration());
        assertEquals("10 km", addedTourLog.getDistance());
        assertEquals(mockTour, addedTourLog.getTour());

        // Assert fields are cleared
        assertTrue(viewModel.nameProperty().get().isEmpty());
        assertTrue(viewModel.dateProperty().get().isEmpty());
        assertTrue(viewModel.durationProperty().get().isEmpty());
        assertTrue(viewModel.distanceProperty().get().isEmpty());
    }

    @Test
    void addTourLogButtonDisabledWhenFieldsEmpty() {
        // Initially, the button should be disabled
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get());

        // Set one field
        viewModel.nameProperty().set("Test Tour");
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get());

        // Set all fields
        viewModel.nameProperty().set("Test Tour");
        viewModel.dateProperty().set("2024-06-24");
        viewModel.durationProperty().set("2 hours");
        viewModel.distanceProperty().set("10 km");
        assertFalse(viewModel.addTourLogButtonDisabledProperty().get());

        // Clear one field
        viewModel.nameProperty().set("");
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get());
    }
}
