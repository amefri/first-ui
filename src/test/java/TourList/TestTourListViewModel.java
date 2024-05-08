package TourList;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
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
    private TourListService service;
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        service = mock(TourListService.class);
        publisher = mock(Publisher.class);
        viewModel = new TourListViewModel(publisher, service);
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
}