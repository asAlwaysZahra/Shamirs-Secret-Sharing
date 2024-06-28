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
import java.util.List;

public class SharesGeneratorGUI extends Application {

    private TextField secretField;
    private TextField primeField;
    private TextField sharesField;
    private TextField thresholdField;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shamir's Shares Generator");

        // Create a GridPane for input fields
        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(20, 20, 20, 20));
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        // Secret label and field
        Label secretLabel = new Label("Secret (s):");
        GridPane.setConstraints(secretLabel, 0, 0);
        secretField = new TextField();
        secretField.setPromptText("Enter the secret");
        secretField.setTooltip(new Tooltip("The secret to be shared"));
        GridPane.setConstraints(secretField, 1, 0);

        // Prime number label and field
        Label primeLabel = new Label("Prime number (p):");
        GridPane.setConstraints(primeLabel, 0, 1);
        primeField = new TextField();
        primeField.setPromptText("Enter a prime number");
        primeField.setTooltip(new Tooltip("Prime number used in calculations"));
        GridPane.setConstraints(primeField, 1, 1);

        // Number of shares label and field
        Label sharesLabel = new Label("Number of shares (n):");
        GridPane.setConstraints(sharesLabel, 0, 2);
        sharesField = new TextField();
        sharesField.setPromptText("Enter the number of shares");
        sharesField.setTooltip(new Tooltip("Number of shares to generate"));
        GridPane.setConstraints(sharesField, 1, 2);

        // Threshold label and field
        Label thresholdLabel = new Label("Threshold (t):");
        GridPane.setConstraints(thresholdLabel, 0, 3);
        thresholdField = new TextField();
        thresholdField.setPromptText("Enter the threshold value");
        thresholdField.setTooltip(new Tooltip("Threshold number of shares needed to reconstruct the secret"));
        GridPane.setConstraints(thresholdField, 1, 3);

        // Generate button
        Button generateButton = new Button("Generate Shares");
        generateButton.getStyleClass().addAll("btn", "btn-primary");
        generateButton.setOnAction(e -> generateShares());
        GridPane.setConstraints(generateButton, 1, 4);

        // Add input elements to the grid
        inputGrid.getChildren().addAll(secretLabel, secretField, primeLabel, primeField, sharesLabel, sharesField, thresholdLabel, thresholdField, generateButton);

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
        Label title = new Label("Shamir's Shares Generator");
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

    private void generateShares() {
        try {
            BigInteger secret = new BigInteger(secretField.getText());
            BigInteger prime = new BigInteger(primeField.getText());
            int n = Integer.parseInt(sharesField.getText());
            int t = Integer.parseInt(thresholdField.getText());

            List<Share> shares = SharesGenerator.generateShares(n, t, secret, prime);
            StringBuilder output = new StringBuilder();
            StringBuilder forCopy = new StringBuilder();

            for (Share share : shares) {
                output.append(share).append("\n");
                forCopy.append(share.x).append(',').append(share.y).append(',');
            }

            outputArea.setText(output + "\n\nShares to copy: " + forCopy.substring(0, forCopy.length() - 1));

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
