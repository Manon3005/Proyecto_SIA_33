module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    exports com.view;
    exports com.model.domain;             
    opens com.controller to javafx.fxml;
    opens com.model.domain to javafx.base;
}