package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Publisher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListViewModelTest {

    @Mock
    private Publisher mockPublisher;

    private ListViewModel viewModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new ListViewModel(mockPublisher);
    }

    @Test
    void testSelectItems() {
        // Setup
        viewModel.getItems().addAll("Item 1", "Item 2", "Item 3");
        viewModel.selectIndexProperty().set(1); // Select "Item 2"

        // Call method under test
        viewModel.selectItems();

        // Verify selected item
        assertEquals("Item 2", viewModel.getItems().get(viewModel.selectIndexProperty().get()));
    }
}
