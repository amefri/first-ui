package at.technikum.firstui.viewmodel;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.SearchTermHistoryService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchHistoryViewModel {
    private final Logger logger = LogManager.getLogger(SearchHistoryViewModel.class);
    private final Publisher publisher;
    private final SearchTermHistoryService searchTermHistoryService;

    private final ObservableList<String> searchHistory = FXCollections.observableArrayList();
    private final IntegerProperty selectedSearchIndex = new SimpleIntegerProperty();

    public SearchHistoryViewModel(Publisher publisher, SearchTermHistoryService searchTermHistoryService) {
        this.publisher = publisher;
        this.searchTermHistoryService = searchTermHistoryService;

        searchHistory.setAll(searchTermHistoryService.findAll());

        // Add listener to handle selection changes
        this.selectedSearchIndex.addListener((observable, oldVal, newVal) -> selectSearchHistory());

        // on search event, update terms in history
        publisher.subscribe(Event.SEARCH_TERM_SEARCHED, (ObjectSubscriber) this::updateSearchHistory);
    }

    public void addSearchTerm(String term) {
        searchHistory.add(term);
    }

    public void selectSearchHistory() {
        if (selectedSearchIndex.get() == -1) {
            return;
        }
        Tours term = searchTermHistoryService.getCurrentlySelectedSearchTerm();

        logger.info("\"%s\" selected in history".formatted(term));

        // send history select event
        searchTermHistoryService.setCurrentlySelectedSearchTerm(term);
        publisher.publish(Event.SEARCH_TERM_SELECTED, term);
    }

    private void updateSearchHistory(Object message) {
        searchHistory.setAll(searchTermHistoryService.findAll());
    }

    public ObservableList<String> getSearchHistory() {
        return searchHistory;
    }

    public IntegerProperty selectedSearchIndexProperty() {
        return selectedSearchIndex;
    }

    public Tours getCurrentlySelectedSearchTerm() {
        System.out.println("currently selected search term: " + searchTermHistoryService.getCurrentlySelectedSearchTerm());
        return searchTermHistoryService.getCurrentlySelectedSearchTerm();

    }
}
