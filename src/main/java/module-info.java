module at.technikum.firstui {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.technikum.firstui to javafx.fxml;
    exports at.technikum.firstui;
    exports at.technikum.firstui.view;
    opens at.technikum.firstui.view to javafx.fxml;
}