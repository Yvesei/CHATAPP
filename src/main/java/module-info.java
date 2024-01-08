module com.example.man {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.man.DB.DAO.entities;

    opens com.example.man to javafx.fxml;
    exports com.example.man;
}