package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModifyTourViewModelTest {

    private ModifyTourViewModel viewModel;
    private TourListService tourListService;
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        tourListService = Mockito.mock(TourListService.class);
        publisher = Mockito.mock(Publisher.class);
        viewModel = new ModifyTourViewModel(publisher, tourListService);
    }

    @Test
    void testInitialState() {
        assertTrue(viewModel.modifyTourButtonDisabledProperty().get());
        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.descriptionProperty().get());
        assertEquals("", viewModel.fromProperty().get());
        assertEquals("", viewModel.toProperty().get());
        assertEquals("", viewModel.transportTypeProperty().get());
        assertEquals("", viewModel.distanceProperty().get());
        assertEquals("", viewModel.estimatedTimeProperty().get());
    }

    @Test
    void testModifyTourButtonDisabled() {
        StringProperty name = viewModel.nameProperty();
        StringProperty description = viewModel.descriptionProperty();
        StringProperty from = viewModel.fromProperty();
        StringProperty to = viewModel.toProperty();
        StringProperty transportType = viewModel.transportTypeProperty();
        StringProperty distance = viewModel.distanceProperty();
        StringProperty estimatedTime = viewModel.estimatedTimeProperty();

        name.set("Test Tour");
        description.set("Description");
        from.set("From Location");
        to.set("To Location");
        transportType.set("Car");
        distance.set("100 km");
        estimatedTime.set("1 hour");

        assertFalse(viewModel.modifyTourButtonDisabledProperty().get());

        estimatedTime.set("");
        assertTrue(viewModel.modifyTourButtonDisabledProperty().get());
    }

    @Test
    void testModifyTourNoSelection() {
        when(tourListService.isSelected()).thenReturn(false);
        viewModel.modifyTour();
        verify(tourListService, never()).modifyTour(any(Tours.class));
    }

    @Test
    void testModifyTour() {
        Tours selectedTour = new Tours("Test Tour", "Old Description", "Old From", "Old To", "Old Transport", "Old Distance", "Old Time", "Old Path");
        selectedTour.setId(1L);

        when(tourListService.isSelected()).thenReturn(true);
        when(tourListService.getCurrentlySelected()).thenReturn(selectedTour);

        viewModel.nameProperty().set("Test Tour");
        viewModel.descriptionProperty().set("New Description");
        viewModel.fromProperty().set("New From");
        viewModel.toProperty().set("New To");
        viewModel.transportTypeProperty().set("New Transport");
        viewModel.distanceProperty().set("New Distance");
        viewModel.estimatedTimeProperty().set("New Time");


        viewModel.modifyTour();

        verify(tourListService).modifyTour(argThat(tour ->
                tour.getName().equals("Test Tour") &&
                        tour.getDescription().equals("New Description") &&
                        tour.getFrom().equals("New From") &&
                        tour.getTo().equals("New To") &&
                        tour.getTransportType().equals("New Transport") &&
                        tour.getDistance().equals("New Distance") &&
                        tour.getEstimatedTime().equals("New Time") &&
                        tour.getId().equals(1L)
        ));

        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.descriptionProperty().get());
        assertEquals("", viewModel.fromProperty().get());
        assertEquals("", viewModel.toProperty().get());
        assertEquals("", viewModel.transportTypeProperty().get());
        assertEquals("", viewModel.distanceProperty().get());
        assertEquals("", viewModel.estimatedTimeProperty().get());
    }
}
