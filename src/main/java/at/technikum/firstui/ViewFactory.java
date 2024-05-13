package at.technikum.firstui;

import at.technikum.firstui.event.ObjectSubscriber;
import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import at.technikum.firstui.view.*;
import at.technikum.firstui.viewmodel.*;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final TourListService tourListService;




    private final SearchViewModel searchViewModel;
    private final SearchHistoryViewModel searchHistoryViewModel;
    private final ListViewModel listViewModel;



    private final ToolBarViewModel toolBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final AddStageViewModel addStageViewModel;


    //-------------------
    private final TourLogService tourLogService;
    private final ToolBarTourLogViewModel toolBarTourLogViewModel;
    private final TableListViewModel tableListViewModel;
    private final AddRouteLogViewModel addRouteLogViewModel;





    private ViewFactory() {
        publisher = new Publisher();
        tourListService = new TourListService();
        tourLogService = new TourLogService();




        searchViewModel = new SearchViewModel(publisher);
        searchHistoryViewModel = new SearchHistoryViewModel(publisher);
        listViewModel = new ListViewModel(publisher);

        toolBarViewModel = new ToolBarViewModel(publisher);
        tourListViewModel = new TourListViewModel(publisher,tourListService,tourLogService);
        addStageViewModel = new AddStageViewModel(publisher,tourListService);



        toolBarTourLogViewModel = new ToolBarTourLogViewModel(publisher);
        tableListViewModel = new TableListViewModel(publisher, tourLogService);
        addRouteLogViewModel = new AddRouteLogViewModel(publisher, tourLogService);


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

        if(AddRouteLogView.class.equals(viewClass)) {
            return new AddRouteLogView(addRouteLogViewModel);
        }
        if(ToolBarTourLogView.class.equals(viewClass)) {
            return new ToolBarTourLogView(toolBarTourLogViewModel);
        }
        if(TableListView.class.equals(viewClass)) {
            return new TableListView(tableListViewModel);
        }


        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }

}
