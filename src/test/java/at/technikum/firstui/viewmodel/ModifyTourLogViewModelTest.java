package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModifyTourLogViewModelTest {

    private ModifyTourLogViewModel viewModel;
    private TourLogService tourLogService;
    private TourListService tourListService;
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        tourLogService = mock(TourLogService.class);
        tourListService = mock(TourListService.class);
        publisher = mock(Publisher.class);
        viewModel = new ModifyTourLogViewModel(publisher, tourLogService, tourListService);
    }

    @Test
    void modifyTourLogButtonShouldBeDisabledWhenFieldsAreEmpty() {
        assertTrue(viewModel.modifyTourLogButtonDisabledProperty().get());

        viewModel.nameProperty().set("Log Name");
        viewModel.dateProperty().set("2023-06-24");
        viewModel.durationProperty().set("120");
        viewModel.distanceProperty().set("10");

        assertFalse(viewModel.modifyTourLogButtonDisabledProperty().get());

        viewModel.distanceProperty().set("");
        assertTrue(viewModel.modifyTourLogButtonDisabledProperty().get());
    }


    @Test
    void modifyTourLogShouldUpdateTourLogWhenSelected() {
        TourLog selectedTourLog = new TourLog("Log Name", "2023-06-24", "120", "10");
        selectedTourLog.setId(1L);

        when(tourLogService.isSelected()).thenReturn(true);
        when(tourLogService.getCurrentlySelected()).thenReturn(selectedTourLog);

        viewModel.nameProperty().set("Updated Log Name");
        viewModel.dateProperty().set("2023-07-01");
        viewModel.durationProperty().set("150");
        viewModel.distanceProperty().set("15");

        viewModel.modifyTourLog();

        ArgumentCaptor<TourLog> captor = ArgumentCaptor.forClass(TourLog.class);
        verify(tourLogService).modifyTourLog(captor.capture());
        TourLog modifiedTourLog = captor.getValue();

        assertEquals("Updated Log Name", modifiedTourLog.getName());
        assertEquals("2023-07-01", modifiedTourLog.getDate());
        assertEquals("150", modifiedTourLog.getDuration());
        assertEquals("15", modifiedTourLog.getDistance());
    }

    @Test
    void modifyTourLogShouldNotUpdateTourLogWhenNotSelected() {
        when(tourLogService.isSelected()).thenReturn(false);

        viewModel.nameProperty().set("Updated Log Name");
        viewModel.dateProperty().set("2023-07-01");
        viewModel.durationProperty().set("150");
        viewModel.distanceProperty().set("15");

        viewModel.modifyTourLog();
        verify(tourLogService, times(0)).modifyTourLog(any(TourLog.class));
    }
}
