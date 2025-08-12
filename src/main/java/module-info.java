module org.project.cafeshopmanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.gluonhq.charm.glisten;


    opens org.project.cafeshopmanagmentsystem to javafx.fxml;
    exports org.project.cafeshopmanagmentsystem;
}