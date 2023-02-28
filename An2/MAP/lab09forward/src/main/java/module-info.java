module com.example.lab09forward {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
//    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql.rowset;

    opens com.example.lab09forward to javafx.fxml;
    opens com.example.lab09forward.Controllers to javafx.fxml;

    opens com.example.lab09forward.domain.Entities to javafx.base;
    exports com.example.lab09forward;
}