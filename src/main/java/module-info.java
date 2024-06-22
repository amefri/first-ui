module at.technikum.firstui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires jakarta.persistence;

    requires java.sql;
    requires org.hibernate.orm.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.web;

    requires org.apache.logging.log4j;


    opens at.technikum.firstui.entity to org.hibernate.orm.core;


    opens at.technikum.firstui to javafx.fxml;
    exports at.technikum.firstui;
    exports at.technikum.firstui.view;
    opens at.technikum.firstui.view to javafx.fxml;
}