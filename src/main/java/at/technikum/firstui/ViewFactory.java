package at.technikum.firstui;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.view.*;
import at.technikum.firstui.viewmodel.*;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final SearchViewModel searchViewModel;
    private final SearchHistoryViewModel searchHistoryViewModel;
    private final ToolBarViewModel toolBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final ListViewModel listViewModel;
    private final AddStageViewModel addStageViewModel;
    private final AddRouteLogViewModel addRouteLogViewModel;

    private ViewFactory() {
        publisher = new Publisher();

        searchViewModel = new SearchViewModel(publisher);
        searchHistoryViewModel = new SearchHistoryViewModel(publisher);
        toolBarViewModel = new ToolBarViewModel(publisher);
        tourListViewModel = new TourListViewModel(publisher);
        listViewModel = new ListViewModel(publisher);
        addStageViewModel = new AddStageViewModel(publisher);
        addRouteLogViewModel = new AddRouteLogViewModel(publisher);
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

        if (ListViewModel.class.equals(viewClass)) {
            return new ListView(listViewModel);
        }
        if(AddStageView.class.equals(viewClass)) {
            return new AddStageView(addStageViewModel);
        }

        if(AddStageView.class.equals(viewClass)) {
            return new AddRouteLogView(addRouteLogViewModel);
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }

}
