module com.example.issproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.controlsfx.controls;
    requires java.persistence;
    requires java.sql;
//    requires java.persistence;
//    requires org.hibernate.orm.core;
//    requires java.logging;
//    requires org.hibernate.orm.core;
//    requires java.persistence;
//    requires jakarta.persistence;
//    requires org.jboss.logging;
//    requires jakarta.transaction;

//    opens java.util.logging to org.jboss.logging;

    opens com.example.issproject to javafx.fxml;
    opens com.example.issproject.controllers to javafx.fxml;
//
////    opens java.logging to org.jboss.logging;
//    opens java.util.logging to org.hibernate.orm.core, org.jboss.logging;

    exports com.example.issproject;
}
//--add-opens java.base/java.util.logging=org.jboss.logging