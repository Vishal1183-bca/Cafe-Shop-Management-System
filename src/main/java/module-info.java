module org.project.cafeshopmanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.project.cafeshopmanagmentsystem to javafx.fxml;
    exports org.project.cafeshopmanagmentsystem;
}