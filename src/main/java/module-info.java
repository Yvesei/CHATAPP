module com.example.man {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.man to javafx.fxml;
    exports com.example.man;
}