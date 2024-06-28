package com.example.secretsharingproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SecretFinderGUI extends Application {

    private TextField thresholdField;
    private TextField primeField;
    private TextArea sharesArea;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shamir's Secret Reconstruction");

        // Create a GridPane for input fields
        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(20, 20, 20, 20));
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        // Threshold label and field
        Label thresholdLabel = new Label("Threshold (t):");
        GridPane.setConstraints(thresholdLabel, 0, 0);
        thresholdField = new TextField();
        thresholdField.setPromptText("Enter threshold value");
        thresholdField.setTooltip(new Tooltip("Threshold number of shares needed to reconstruct the secret"));
        GridPane.setConstraints(thresholdField, 1, 0);

        // Prime number label and field
        Label primeLabel = new Label("Prime number (p):");
        GridPane.setConstraints(primeLabel, 0, 1);
        primeField = new TextField();
        primeField.setPromptText("Enter a prime number");
        primeField.setTooltip(new Tooltip("Prime number used in calculations"));
        GridPane.setConstraints(primeField, 1, 1);

        // Shares label and area
        Label sharesLabel = new Label("Shares (x1,y1,x2,y2,...):");
        GridPane.setConstraints(sharesLabel, 0, 2);
        sharesArea = new TextArea();
        sharesArea.setPromptText("Enter shares in x,y pairs separated by commas");
        sharesArea.setTooltip(new Tooltip("Enter shares as comma-separated x,y pairs"));
        sharesArea.setPrefHeight(100);
        GridPane.setConstraints(sharesArea, 1, 2);

        // Reconstruct button
        Button reconstructButton = new Button("Reconstruct Secret");
        reconstructButton.getStyleClass().addAll("btn", "btn-primary");
        reconstructButton.setOnAction(e -> reconstructSecret());
        GridPane.setConstraints(reconstructButton, 1, 3);

        // Add input elements to the grid
        inputGrid.getChildren().addAll(thresholdLabel, thresholdField, primeLabel, primeField, sharesLabel, sharesArea, reconstructButton);

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(150);
        outputArea.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        // Main layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(createHeader(), inputGrid, outputArea, createFooter());

        // Scene setup
        Scene scene = new Scene(layout, 700, 550);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setPadding(new Insets(10, 10, 10, 10));
        header.setStyle("-fx-background-color: #2E8B57; -fx-alignment: center;");
        Label title = new Label("Shamir's Secret Reconstruction");
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

    private void reconstructSecret() {
        try {
            int t = Integer.parseInt(thresholdField.getText());
            BigInteger p = new BigInteger(primeField.getText());

            String[] shareStrings = sharesArea.getText().split(",");
            if (shareStrings.length % 2 != 0) {
                throw new IllegalArgumentException("Shares input must be in pairs of x,y values.");
            }

            List<Share> sharesToReconstruct = new ArrayList<>();
            for (int i = 0; i < shareStrings.length; i += 2) {
                BigInteger x = new BigInteger(shareStrings[i].trim());
                BigInteger y = new BigInteger(shareStrings[i + 1].trim());
                sharesToReconstruct.add(new Share(x, y));
            }

            if (sharesToReconstruct.size() < t) {
                throw new IllegalArgumentException("Number of shares provided is less than the threshold.");
            }

            BigInteger reconstructedSecret = SecretFinder.reconstructSecret(sharesToReconstruct, p);
            outputArea.setText("Reconstructed secret: " + reconstructedSecret);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
