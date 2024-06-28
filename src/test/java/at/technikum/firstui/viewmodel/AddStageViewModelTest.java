package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddStageViewModelTest {

    private AddStageViewModel viewModel;
    private TourListService tourListService;
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        tourListService = mock(TourListService.class);
        publisher = mock(Publisher.class);
        viewModel = new AddStageViewModel(publisher, tourListService);
    }

    @Test
    void addTourButtonShouldBeDisabledWhenFieldsAreEmpty() {
        assertTrue(viewModel.addTourButtonDisabledProperty().get());

        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Description");
        viewModel.fromProperty().set("From");
        viewModel.toProperty().set("To");
        viewModel.transportTypeProperty().set("Transport Type");
        viewModel.distanceProperty().set("10");
        viewModel.estimatedTimeProperty().set("60");

        assertFalse(viewModel.addTourButtonDisabledProperty().get());

        viewModel.imagePathProperty().set("image/path");
        assertFalse(viewModel.addTourButtonDisabledProperty().get());

        viewModel.estimatedTimeProperty().set("");
        assertTrue(viewModel.addTourButtonDisabledProperty().get());
    }

    @Test
    void addTourShouldPublishEventAndClearFields() {
        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Description");
        viewModel.fromProperty().set("From");
        viewModel.toProperty().set("To");
        viewModel.transportTypeProperty().set("Transport Type");
        viewModel.distanceProperty().set("10");
        viewModel.estimatedTimeProperty().set("60");
        viewModel.imagePathProperty().set("image/path");

        viewModel.addTour();
        verify(tourListService, times(1)).addTour(any(Tours.class));
        verify(publisher, times(1)).publish(eq(Event.TOUR_ADDED), any(Tours.class));

        assertTrue(viewModel.nameProperty().get().isEmpty());
        assertTrue(viewModel.descriptionProperty().get().isEmpty());
        assertTrue(viewModel.fromProperty().get().isEmpty());
        assertTrue(viewModel.toProperty().get().isEmpty());
        assertTrue(viewModel.transportTypeProperty().get().isEmpty());
        assertTrue(viewModel.distanceProperty().get().isEmpty());
        assertTrue(viewModel.estimatedTimeProperty().get().isEmpty());
        assertTrue(viewModel.imagePathProperty().get().isEmpty());
    }

    @Test
    void addTourShouldNotPublishEventWhenFieldsAreEmpty() {
        viewModel.addTour();
        verify(tourListService, times(0)).addTour(any(Tours.class));
        verify(publisher, times(0)).publish(any(Event.class), any(Tours.class));
    }
}
