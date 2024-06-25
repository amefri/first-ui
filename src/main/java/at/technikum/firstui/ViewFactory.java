package at.technikum.firstui;

import at.technikum.firstui.event.Publisher;
import at.technikum.firstui.repository.*;
import at.technikum.firstui.services.SearchTermHistoryService;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import at.technikum.firstui.view.*;
import at.technikum.firstui.viewmodel.*;
import at.technikum.firstui.services.*;

public class ViewFactory {

    private static ViewFactory instance;
    private final Publisher publisher;

    //-------------------SERVICES----------------------
    private final TourListService tourListService;
    private final TourLogService tourLogService;
    private final SearchTermHistoryService searchTermHistoryService;

    //-------------------VIEW MODEL----------------------
    private final SearchViewModel searchViewModel;
    private final SearchHistoryViewModel searchHistoryViewModel;
    private final ListViewModel listViewModel;



    private final ToolBarViewModel toolBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final AddStageViewModel addStageViewModel;

    private final ToolBarTourLogViewModel toolBarTourLogViewModel;
    private final TableListViewModel tableListViewModel;
    private final AddRouteLogViewModel addRouteLogViewModel;
    private final ModifyTourLogViewModel modifyTourLogViewModel;
    private final ModifyTourViewModel modifyTourViewModel;
    private final APIService apiService;
    private final APIController apiController;




    //-------------------REPOSITORIES----------------------
    private final SearchTermRepository searchTermRepository;
    private final TourListRepository tourListRepository;
    private final TourLogRepository tourLogRepository;



    private ViewFactory() {
        publisher = new Publisher();


        //Repository
        searchTermRepository = new SearchTermDatabaseRepository();
        tourListRepository = new TourListDatabaseRepository();
        tourLogRepository = new TourLogDatabaseRepository();

        //Service
        searchTermHistoryService = new SearchTermHistoryService(searchTermRepository);
        tourListService = new TourListService(tourListRepository);
        tourLogService = new TourLogService(tourLogRepository, tourListRepository);
        apiService = new APIService(tourListService, publisher);

        //ViewModel
        searchViewModel = new SearchViewModel(publisher, searchTermHistoryService);
        searchHistoryViewModel = new SearchHistoryViewModel(publisher, searchTermHistoryService);
        listViewModel = new ListViewModel(publisher);

        toolBarViewModel = new ToolBarViewModel(publisher, tourListService);
        tourListViewModel = new TourListViewModel(publisher,tourListService,tourLogService);
        addStageViewModel = new AddStageViewModel(publisher,tourListService);
        apiController = new APIController(tourListService,publisher,apiService);



        toolBarTourLogViewModel = new ToolBarTourLogViewModel(publisher, tourLogService);
        tableListViewModel = new TableListViewModel(publisher, tourLogService);
        addRouteLogViewModel = new AddRouteLogViewModel(publisher, tourLogService, tourListService);
        modifyTourLogViewModel = new ModifyTourLogViewModel(publisher,tourLogService, tourListService);
        modifyTourViewModel = new ModifyTourViewModel(publisher, tourListService);

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
        if(ModifyTourLogView.class.equals(viewClass)) {
            return new ModifyTourLogView(modifyTourLogViewModel);
        }
        if(ModifyTourView.class.equals(viewClass)) {
            return new ModifyTourView(modifyTourViewModel);
        }
        if(APIController.class.equals(viewClass)) {
            return new APIController(tourListService,publisher,apiService);
        }
        if(APIService.class.equals(viewClass)) {
            return new APIService(tourListService, publisher);
        }


        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }

}
