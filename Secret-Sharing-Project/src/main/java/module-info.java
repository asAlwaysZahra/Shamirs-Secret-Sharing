module com.example.secretsharingproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.secretsharingproject to javafx.fxml;
    exports com.example.secretsharingproject;
}