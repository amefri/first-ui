module at.technikum.firstui {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.technikum.firstui to javafx.fxml;
    exports at.technikum.firstui;
}