module com.example.miniproject2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.miniproject2 to javafx.fxml;
    exports com.example.miniproject2;
}