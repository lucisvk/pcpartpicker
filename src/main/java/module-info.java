module com.example.pcpartpicker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pcpartpicker to javafx.fxml;
    exports com.example.pcpartpicker;
}