package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.SearchTermHistoryService;
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

public class SearchHistoryViewModelTest {

    @Mock
    private Publisher mockPublisher;

    @Mock
    private SearchTermHistoryService mockSearchTermHistoryService;

    private SearchHistoryViewModel viewModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new SearchHistoryViewModel(mockPublisher, mockSearchTermHistoryService);
    }

    @Test
    public void testAddSearchTerm() {
        // Mock setup
        String searchTerm = "Test Term";

        // Call method under test
        viewModel.addSearchTerm(searchTerm);

        // Verify interactions
        assertTrue(viewModel.getSearchHistory().contains(searchTerm));
    }

    @Test
    public void testSelectSearchHistory() {
        // Mock setup
        int selectedIndex = 0;
        Tours mockTerm = new Tours("Mock Term", "", "", "", "", "", "", "");
        viewModel.selectedSearchIndexProperty().set(selectedIndex);
        when(mockSearchTermHistoryService.getCurrentlySelectedSearchTerm()).thenReturn(mockTerm);

        // Call method under test
        viewModel.selectSearchHistory();

        // Verify interactions
        verify(mockSearchTermHistoryService).setCurrentlySelectedSearchTerm(mockTerm);
        verify(mockPublisher).publish(Event.SEARCH_TERM_SELECTED, mockTerm);
    }

    @Test
    public void testDeleteSelectedTerm() {
        // Mock setup
        int index = 0;
        String termToDelete = "Term to Delete";
        ObservableList<String> mockList = FXCollections.observableArrayList(termToDelete);
        viewModel.selectedSearchIndexProperty().set(index);
        when(mockSearchTermHistoryService.deleteTerm(termToDelete)).thenReturn(true);
        viewModel.getSearchHistory().addAll(mockList);

        // Call method under test
        viewModel.deleteSelectedTerm();

        // Verify interactions
        verify(mockSearchTermHistoryService).deleteTerm(termToDelete);
        assertTrue(viewModel.getSearchHistory().isEmpty());
    }

}
