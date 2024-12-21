package com.example.pcpartpicker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class LoadBuildPage {
    private final String username;

    public LoadBuildPage(String username) {
        this.username = username;
    }

    public void showLoadPage(Stage stage) {
        Label title = new Label("Load a Build");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#2c3e50"));

        List<String> buildNames = Database.getUserBuildNames(username);
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(buildNames);
        listView.setPrefHeight(200);

        Button editButton = createButton("Edit Build", "#2ecc71");
        editButton.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                BuildData data = Database.loadBuild(username, selected);
                new PCBuildPage(username).showBuildPage(stage, data);
            }
        });

        Button backButton = createButton("Back Home", "#e74c3c");
        backButton.setOnAction(e -> new HomePage(username).showHomePage(stage));

        VBox layout = new VBox(20, title, listView, editButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Load Build");
    }

    private Button createButton(String text, String colorHex) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.web(colorHex), CornerRadii.EMPTY, Insets.EMPTY)));
        return button;
    }
}