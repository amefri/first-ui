package at.technikum.firstui;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.view.SearchHistoryView;
import at.technikum.firstui.view.SearchView;
import at.technikum.firstui.view.ToolBarView;
import at.technikum.firstui.view.TourListView;
import at.technikum.firstui.viewmodel.SearchHistoryViewModel;
import at.technikum.firstui.viewmodel.SearchViewModel;
import at.technikum.firstui.viewmodel.ToolBarViewModel;
import at.technikum.firstui.viewmodel.TourListViewModel;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final SearchViewModel searchViewModel;
    private final SearchHistoryViewModel searchHistoryViewModel;
    private final ToolBarViewModel toolBarViewModel;
    private final TourListViewModel tourListViewModel;

    private ViewFactory() {
        publisher = new Publisher();

        searchViewModel = new SearchViewModel(publisher);
        searchHistoryViewModel = new SearchHistoryViewModel(publisher);
        toolBarViewModel = new ToolBarViewModel(publisher);
        tourListViewModel = new TourListViewModel(publisher);
    }

    public static ViewFactory getInstance() {
        if (null == instance) {
            instance = new ViewFactory();
        }

        return instance;
    }

    public Object create(Class<?> viewClass) {
        if (SearchView.class == viewClass) {
            return new SearchView(searchViewModel);
        }

        if (SearchHistoryView.class == viewClass) {
            return new SearchHistoryView(searchHistoryViewModel);
        }
        if (ToolBarView.class.equals(viewClass)) {
            return new ToolBarView(toolBarViewModel);
        }

        if (TourListView.class.equals(viewClass)) {
            return new TourListView(tourListViewModel);
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }

}
