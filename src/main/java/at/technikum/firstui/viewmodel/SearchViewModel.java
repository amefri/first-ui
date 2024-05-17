package at.technikum.firstui.viewmodel;

import at.technikum.firstui.event.Event;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.SearchTermHistoryService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchViewModel {

    private final Publisher publisher;
    private final SearchTermHistoryService searchTermHistoryService;

    private final StringProperty searchText
            = new SimpleStringProperty("");
    private final BooleanProperty searchDisabled
            = new SimpleBooleanProperty(true);

    public SearchViewModel(
            Publisher publisher,
            SearchTermHistoryService searchTermHistoryService
    ) {
        this.publisher = publisher;
        this.searchTermHistoryService = searchTermHistoryService;

        // if search text is empty, disable search
        this.searchText.addListener(
                observable -> searchDisabled.set(searchText.get().isEmpty())
        );

        // on search term selected event, set the selected term as current search term
        publisher.subscribe(Event.SEARCH_TERM_SELECTED, searchText::set);
    }

    public void search() {
        if (searchDisabled.get()) {
            return;
        }

        searchTermHistoryService.add(searchText.get());
        publisher.publish(Event.SEARCH_TERM_SEARCHED, searchText.get());

        searchText.set("");
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    public String getSearchText() {
        return searchText.get();
    }

    public BooleanProperty searchDisabledProperty() {
        return searchDisabled;
    }

    public boolean getSearchDisabled() {
        return searchDisabled.get();
    }

    public boolean isSearchDisabled() {
        return searchDisabled.get();
    }
}