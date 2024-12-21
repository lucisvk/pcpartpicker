package com.example.pcpartpicker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("PC Part Picker - Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#3498db"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setFont(Font.font("Arial", 14));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setFont(Font.font("Arial", 14));

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", 12));

        Button loginButton = createButton("Login", "#2ecc71");
        loginButton.setOnAction(e -> {
            if (Database.validateUser(usernameField.getText(), passwordField.getText())) {
                new HomePage(usernameField.getText()).showHomePage(primaryStage);
            } else {
                errorLabel.setText("Invalid credentials.");
            }
        });

        VBox layout = new VBox(10, titleLabel, usernameField, passwordField, loginButton, errorLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private Button createButton(String text, String colorHex) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.web(colorHex), CornerRadii.EMPTY, Insets.EMPTY)));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}