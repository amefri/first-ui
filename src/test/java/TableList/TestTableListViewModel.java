package TableList;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import at.technikum.firstui.viewmodel.TableListViewModel;
import at.technikum.firstui.viewmodel.TourListViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collection;
import java.util.Collections;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestTableListViewModel {
    private TableListViewModel viewModel;
    private TourLogService tourLogService;
    private TourListService tourListService;
    private Publisher publisher;


    @BeforeEach
    public void setUp() {
        tourLogService = mock(TourLogService.class); // Hier könnte auch ein Mock verwendet werden
        tourLogService= mock (TourLogService.class);
        publisher = mock(Publisher.class);
        viewModel = new TableListViewModel(publisher, tourLogService);

    }

    @Test
    public void testAddToTourLogs() {
        // Arrange
        TourLog tourLog = new TourLog("TestTour", "2024-05-12", "50 km", "2 hours");
        int initialSize = viewModel.getTourLogs().size();

        // Act
        viewModel.addToTourLogs(tourLog);

        // Assert
        int newSize = viewModel.getTourLogs().size();
        assertEquals(initialSize + 1, newSize);
        assertEquals(tourLog, viewModel.getTourLogs().get(newSize - 1)); // Überprüft, ob der hinzugefügte TourLog in der Liste enthalten ist
    }

    @Test
    public void testDeleteSelectedTour() {
        // Arrange
        TourLog tourLog = new TourLog("TestTour", "2024-05-12", "50 km", "2 hours");
        viewModel.addToTourLogs(tourLog);
        int initialSize = viewModel.getTourLogs().size();
        viewModel.selectedAddTourProperty().setValue(initialSize - 1);

        // Act
        viewModel.deleteSelectedTour();

        // Assert
        int newSize = viewModel.getTourLogs().size();
        assertEquals(initialSize - 1, newSize);
        verify(tourLogService).deleteTourByName(tourLog.getName());
    }


}

