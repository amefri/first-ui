package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchHistoryViewModel {

    private final Publisher publisher;

    private final ObservableList<String> searchHistory
            = FXCollections.observableArrayList();
    private final IntegerProperty selectedSearchIndex
            = new SimpleIntegerProperty();

    public SearchHistoryViewModel(Publisher publisher) {
        this.publisher = publisher;

        // if item is selected, fill in search text
        this.selectedSearchIndex.addListener(
                observable -> selectSearchHistory()
        );

        // on search event, add term to history
        publisher.subscribe(Event.SEARCH_TERM_SEARCHED, this::addToSearchHistory);
    }

    public void selectSearchHistory() {
        if (selectedSearchIndex.get() == -1) {
            return;
        }

        // TODO send history select event
    }

    private void addToSearchHistory(String searchTerm) {
        searchHistory.add(searchTerm);
    }

    public ObservableList<String> getSearchHistory() {
        return searchHistory;
    }

    public IntegerProperty selectedSearchIndexProperty() {
        return selectedSearchIndex;
    }
}