package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.SearchHistoryViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchHistoryView implements Initializable {

    private final SearchHistoryViewModel viewModel;

    @FXML
    private ListView<String> searchHistoryList;

    public SearchHistoryView(SearchHistoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchHistoryList.setItems(viewModel.getSearchHistory());
        this.viewModel.selectedSearchIndexProperty()
                .bind(searchHistoryList.getSelectionModel().selectedIndexProperty());

        searchHistoryList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Check for double-click
                viewModel.getCurrentlySelectedSearchTerm();
            }

        });




}}