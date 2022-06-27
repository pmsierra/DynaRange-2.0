module com.example.dynarange2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires com.jfoenix;

    opens com.example.dynarange2 to javafx.fxml;
    exports com.example.dynarange2;
}