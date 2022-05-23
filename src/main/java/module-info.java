module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.gluonhq.charm.glisten;
    requires tornadofx.controls;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.presentation;
    opens org.example.presentation to javafx.fxml;
    exports org.example.businessLogic;
    opens org.example.businessLogic to javafx.fxml;
    exports org.example.dataAccess;
    opens org.example.dataAccess to javafx.fxml;
}
