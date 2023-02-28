module com.example.examen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.examen to javafx.fxml;
    opens com.example.examen.controllers to javafx.fxml;
    opens com.example.examen.domain.entities to javafx.base;
    exports com.example.examen;
}