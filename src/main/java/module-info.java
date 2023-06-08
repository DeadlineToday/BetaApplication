module com.example.betaapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.betaapplication to javafx.fxml;
    exports com.example.betaapplication;
}