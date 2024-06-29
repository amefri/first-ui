package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import at.technikum.firstui.viewmodel.ModifyTourLogViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModifyTourLogViewModelTest {

    @Mock
    private Publisher publisher;

    @Mock
    private TourLogService tourLogService;

    @Mock
    private TourListService tourListService;

    @InjectMocks
    private ModifyTourLogViewModel viewModel;

    private final StringProperty name = new SimpleStringProperty("Tour Name");
    private final StringProperty date = new SimpleStringProperty("2023-06-29");
    private final StringProperty rating = new SimpleStringProperty("4");
    private final StringProperty info = new SimpleStringProperty("Beautiful tour");

    private final BooleanProperty modifyTourLogButtonDisabled = new SimpleBooleanProperty(false);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new ModifyTourLogViewModel(publisher, tourLogService, tourListService);
        viewModel.nameProperty().bind(name);
        viewModel.dateProperty().bind(date);
        viewModel.ratingProperty().bind(rating);
        viewModel.infoProperty().bind(info);
        viewModel.modifyTourLogButtonDisabledProperty().bind(modifyTourLogButtonDisabled);
    }

    @Test
    public void testModifyTourLog() {
        // Mock selected tour log
        TourLog selectedTourLog = new TourLog("Old Tour Name", "2022-01-01", "3", "Old info");
        selectedTourLog.setId(1L);
        when(tourLogService.isSelected()).thenReturn(true);
        when(tourLogService.getCurrentlySelected()).thenReturn(selectedTourLog);

        // Call modifyTourLog
        viewModel.modifyTourLog();

        // Verify that modifyTourLogService was called with the updated tour log
        ArgumentCaptor<TourLog> captor = ArgumentCaptor.forClass(TourLog.class);
        verify(tourLogService).modifyTourLog(captor.capture());

        TourLog modifiedTourLog = captor.getValue();
        assertNotNull(modifiedTourLog);
        assertEquals("Tour Name", modifiedTourLog.getName());
        assertEquals("2023-06-29", modifiedTourLog.getDate());
        assertEquals("4", modifiedTourLog.getRating());
        assertEquals("Beautiful tour", modifiedTourLog.getInfo());

        // Verify that publisher.publish was called with MODIFY_TOUR event and the modified tour log
        verify(publisher).publish(Event.MODIFY_TOUR, modifiedTourLog);
    }

    @Test
    public void testModifyTourLogNoSelection() {
        // Mock no tour log selected
        when(tourLogService.isSelected()).thenReturn(false);

        // Call modifyTourLog
        viewModel.modifyTourLog();

        // Verify that tourLogService.modifyTourLog and publisher.publish were not called
        verify(tourLogService, never()).modifyTourLog(any());
        verify(publisher, never()).publish(any(), any());
    }

    @Test
    public void testModifyTourLogButtonDisabled() {
        // Set properties such that modifyTourLogButtonDisabled should be true
        name.set("");
        date.set("");
        rating.set("");
        info.set("");

        // Call updateAddTourLogButtonDisabled manually to update modifyTourLogButtonDisabled
        viewModel.modifyTourLog();

        // Verify that tourLogService.modifyTourLog and publisher.publish were not called
        verify(tourLogService, never()).modifyTourLog(any());
        verify(publisher, never()).publish(any(), any());
    }
}
