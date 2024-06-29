package at.technikum.firstui.viewmodel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ModifyTourViewModelTest {

    @Mock
    private TourListService mockTourListService;

    @Mock
    private Publisher mockPublisher;

    private ModifyTourViewModel viewModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new ModifyTourViewModel(mockPublisher, mockTourListService);
    }

    @Test
    public void testModifyTourButtonDisabledInitially() {
        BooleanProperty buttonDisabled = viewModel.modifyTourButtonDisabledProperty();
        assertTrue(buttonDisabled.get(), "Button should be disabled initially");
    }

    @Test
    public void testModifyTourButtonEnabledAfterSettingFields() {
        BooleanProperty buttonDisabled = viewModel.modifyTourButtonDisabledProperty();

        // Simulate setting some fields
        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Description");
        viewModel.fromProperty().set("From");
        viewModel.toProperty().set("To");
        viewModel.transportTypeProperty().set("Transport");
        viewModel.distanceProperty().set("100 km");
        viewModel.estimatedTimeProperty().set("2 hours");

        assertFalse(buttonDisabled.get(), "Button should be enabled after setting all fields");
    }

    @Test
    public void testModifyTourWhenTourSelected() {
        // Prepare mock behavior
        Tours selectedTour = new Tours("Tour Name", "Description", "From", "To", "Transport", "100 km", "2 hours", "");
        when(mockTourListService.isSelected()).thenReturn(true);
        when(mockTourListService.getCurrentlySelected()).thenReturn(selectedTour);

        // Simulate setting fields
        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Description");
        viewModel.fromProperty().set("From");
        viewModel.toProperty().set("To");
        viewModel.transportTypeProperty().set("Transport");
        viewModel.distanceProperty().set("100 km");
        viewModel.estimatedTimeProperty().set("2 hours");

        // Call modifyTour
        viewModel.modifyTour();

        // Verify that modifyTourService was called with the correct Tour object
        ArgumentCaptor<Tours> captor = ArgumentCaptor.forClass(Tours.class);
        verify(mockTourListService).modifyTour(captor.capture());

        Tours modifiedTour = captor.getValue();
        assertEquals(selectedTour.getName(), modifiedTour.getName(), "Names should match");
        assertEquals(selectedTour.getId(), modifiedTour.getId(), "IDs should match");

        // Additional verification for logging, if necessary
        // verify(logger).warn("Expected log message");

        // Reset fields after modification
        assertEquals("", viewModel.nameProperty().get(), "Name should be reset");
        assertEquals("", viewModel.descriptionProperty().get(), "Description should be reset");
        assertEquals("", viewModel.fromProperty().get(), "From should be reset");
        assertEquals("", viewModel.toProperty().get(), "To should be reset");
        assertEquals("", viewModel.transportTypeProperty().get(), "Transport type should be reset");
        assertEquals("", viewModel.distanceProperty().get(), "Distance should be reset");
        assertEquals("", viewModel.estimatedTimeProperty().get(), "Estimated time should be reset");
    }

    // Add more tests as needed for edge cases, logging, etc.
}
