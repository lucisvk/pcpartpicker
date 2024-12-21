package com.example.pcpartpicker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HomePage {
    private final String username;

    public HomePage(String username) {
        this.username = username;
    }

    public void showHomePage(Stage stage) {
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeLabel.setTextFill(Color.web("#2c3e50"));

        Button newBuildButton = createButton("Create New Build", "#3498db");
        Button loadBuildButton = createButton("Load Existing Build", "#3498db");

        newBuildButton.setOnAction(e -> new PCBuildPage(username).showBuildPage(stage, null));
        loadBuildButton.setOnAction(e -> new LoadBuildPage(username).showLoadPage(stage));

        VBox layout = new VBox(20, welcomeLabel, newBuildButton, loadBuildButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Home");
    }

    private Button createButton(String text, String colorHex) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.web(colorHex), CornerRadii.EMPTY, Insets.EMPTY)));
        return button;
    }
}