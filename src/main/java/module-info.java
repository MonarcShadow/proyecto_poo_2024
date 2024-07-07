module proyecto.tienda {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens proyecto.tienda to javafx.fxml;
    exports proyecto.tienda;
}