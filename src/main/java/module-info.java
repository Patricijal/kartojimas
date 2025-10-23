module org.example.kartojimas {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires mysql.connector.j;
    requires jakarta.persistence;
    requires javafx.graphics;

    opens org.example.kartojimas to javafx.fxml;
    opens org.example.kartojimas.model to org.hibernate.orm.core;

    exports org.example.kartojimas;
    exports org.example.kartojimas.model;
}