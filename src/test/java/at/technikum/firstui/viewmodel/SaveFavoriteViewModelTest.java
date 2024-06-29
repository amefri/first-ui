package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.FavPlaces;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.repository.FavPlaceDatabaseRepository;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaveFavoriteViewModelTest {

    @Mock
    private Publisher mockPublisher;

    @Mock
    private FavPlaceDatabaseRepository mockFavPlaceService;

    private SaveFavoriteViewModel viewModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new SaveFavoriteViewModel(mockPublisher, mockFavPlaceService);
    }

    @Test
    public void testSaveFavoritePlace() {
        // Mock setup
        String name = "Test Place";
        String description = "Test Description";
        FavPlaces mockPlace = new FavPlaces(name, description, "", ""); // adjust parameters if needed
        doNothing().when(mockFavPlaceService).save(mockPlace);

        // Call method under test
        viewModel.saveFavoritePlace(name, description);

        // Verify interactions
        ArgumentCaptor<FavPlaces> captor = ArgumentCaptor.forClass(FavPlaces.class);
        verify(mockFavPlaceService).save(captor.capture());
        FavPlaces savedPlace = captor.getValue();
        assertEquals(name, savedPlace.getNameFav());
        assertEquals(description, savedPlace.getDescription());
        verify(mockPublisher).publish(Event.ADD_FAV, savedPlace);
    }

    @Test
    public void testDelete() {
        // Mock setup
        int index = 0;
        String name = "Test Place";
        ObservableList<FavPlaces> mockList = FXCollections.observableArrayList(
                new FavPlaces(name, "Test Description", "", "")
        );
        when(mockFavPlaceService.deleteTerm(name)).thenReturn(true);
        viewModel.selectedIndexProperty().set(index);
        viewModel.getFavoritePlaces().addAll(mockList);

        // Call method under test
        viewModel.delete();

        // Verify interactions
        verify(mockFavPlaceService).deleteTerm(name);
        assertTrue(viewModel.getFavoritePlaces().isEmpty());
    }

    @Test
    public void testNotify() {
        // Mock setup
        FavPlaces mockPlace = new FavPlaces("Mock Place", "Mock Description", "", "");

        // Call method under test
        viewModel.notify(mockPlace);

        // Verify interactions
        assertTrue(viewModel.getFavoritePlaces().contains(mockPlace));
    }

    // Add more tests as needed for edge cases, logging, etc.
}
