package at.technikum.firstui.view;

import at.technikum.firstui.viewmodel.MenuBarViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MenuBarView{
    @FXML
    private MenuItem menuFileItemClose;
    @FXML private MenuItem menuEditItemDelete;
    @FXML private MenuItem menuHelpItemAbout;

    private final MenuBarViewModel viewModel = new MenuBarViewModel();

    @FXML
    public void initialize() {
        menuFileItemClose.setOnAction(e -> viewModel.onClose());
        menuEditItemDelete.setOnAction(e -> viewModel.onDelete());
        menuHelpItemAbout.setOnAction(e -> viewModel.onAbout());
    }
}
