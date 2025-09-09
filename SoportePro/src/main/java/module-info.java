module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 
    requires java.base;

    exports com.view;
    opens com.controller to javafx.fxml;
}
