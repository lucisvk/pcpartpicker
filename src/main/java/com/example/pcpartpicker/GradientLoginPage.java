package com.example.pcpartpicker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GradientLoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("PC Part Picker - Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        // Username Field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        styleTextField(usernameField);

        // Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        styleTextField(passwordField);

        // Login Button
        Button loginButton = new Button("Login");
        styleButton(loginButton);

        // Layout
        VBox formBox = new VBox(15, titleLabel, usernameField, passwordField, loginButton);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(40));

        // Create a vertical gradient background: dark blue at top, purple at bottom
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#1c3d5a")), // dark blue top
                new Stop(1, Color.web("#4b2c5e"))  // purple bottom
        };
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);

        Background gradientBackground = new Background(
                new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)
        );

        formBox.setBackground(gradientBackground);

        // Scene Setup
        Scene scene = new Scene(formBox, 500, 300);
        primaryStage.setTitle("PC Part Picker - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void styleTextField(TextField field) {
        field.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        field.setPadding(new Insets(10));
        // White text on dark background might be too stark; use a light text color and white background
        field.setBackground(new Background(new BackgroundFill(Color.web("#f0f0f0"), new CornerRadii(15), Insets.EMPTY)));
        field.setPrefWidth(250);

        // Optional: To mimic a "glow," add a subtle border.
        field.setBorder(new Border(new BorderStroke(Color.web("#a0a0ff"), BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(2))));
        field.setAlignment(Pos.CENTER_LEFT);
    }

    private void styleButton(Button button) {
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(
                new BackgroundFill(Color.web("#2ecc71"), new CornerRadii(15), Insets.EMPTY)
        ));
        button.setPadding(new Insets(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        launch(args);
    }
}