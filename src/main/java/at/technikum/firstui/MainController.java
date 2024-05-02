package at.technikum.firstui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class
MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField nameInput;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(
                "Welcome  %s to JavaFX Application!".formatted(nameInput.getText()));
        nameInput.clear();
    }
}