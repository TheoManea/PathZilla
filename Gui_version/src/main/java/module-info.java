module com.example.gui_version {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.gui_version to javafx.fxml;
    exports com.example.gui_version;
}