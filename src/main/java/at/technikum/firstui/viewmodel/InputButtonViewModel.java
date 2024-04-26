package at.technikum.firstui.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InputButtonViewModel {

    private final StringProperty searchText
            = new SimpleStringProperty("");
    private final BooleanProperty disabledSearchButton
            = new SimpleBooleanProperty(true);


    public InputButtonViewModel() {
        this.searchText.addListener(observable -> updateSearchButton());
    }

    public void updateSearchButton() {
        disabledSearchButton.set(searchText.get().isEmpty());
    }

    public String getSearchText() {
        return searchText.get();
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText.set(searchText);
    }

    public boolean isDisabledSearchButton() {
        return disabledSearchButton.get();
    }

    public BooleanProperty disabledSearchButtonProperty() {
        return disabledSearchButton;
    }

    public void setDisabledSearchButton(boolean disabledSearchButton) {
        this.disabledSearchButton.set(disabledSearchButton);
    }
}