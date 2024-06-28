package com.example.secretsharingproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shamir's Secret Sharing Scheme");

        // Create a VBox for the main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.setStyle("-fx-alignment: center;");

        // Create header
        HBox header = createHeader();
        mainLayout.getChildren().add(header);

        // Shares Generator button
        Button sharesGeneratorButton = new Button("Shares Generator");
        sharesGeneratorButton.getStyleClass().addAll("btn", "btn-primary");
        sharesGeneratorButton.setPrefWidth(200);
        sharesGeneratorButton.setPrefHeight(50);
        sharesGeneratorButton.setOnAction(e -> new SharesGeneratorGUI().start(new Stage()));
        sharesGeneratorButton.setGraphic(new ImageView(new Image("file:shares.png", 20, 20, true, true)));

        // Secret Finder button
        Button secretFinderButton = new Button("Secret Finder");
        secretFinderButton.getStyleClass().addAll("btn", "btn-primary");
        secretFinderButton.setPrefWidth(200);
        secretFinderButton.setPrefHeight(50);
        secretFinderButton.setOnAction(e -> new SecretFinderGUI().start(new Stage()));
        secretFinderButton.setGraphic(new ImageView(new Image("file:secret.png", 20, 20, true, true)));

        // Add buttons to the main layout
        mainLayout.getChildren().addAll(sharesGeneratorButton, secretFinderButton);

        // Create footer
        HBox footer = createFooter();
        mainLayout.getChildren().add(footer);

        // Scene
        Scene scene = new Scene(mainLayout, 700, 550);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setPadding(new Insets(10, 10, 10, 10));
        header.setStyle("-fx-background-color: #2E8B57; -fx-alignment: center;");
        Label title = new Label("Shamir's Secret Sharing Scheme");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        ImageView logo = new ImageView(new Image("file:logo.png")); // Add your logo image file
        logo.setFitHeight(40);
        logo.setFitWidth(40);
        header.getChildren().addAll(logo, title);
        return header;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setPadding(new Insets(10, 10, 10, 10));
        footer.setStyle("-fx-background-color: #2E8B57; -fx-alignment: center;");
        Label footerLabel = new Label("Shamir's Secret Sharing Scheme © 2024");
        footerLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");
        footer.getChildren().add(footerLabel);
        return footer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
