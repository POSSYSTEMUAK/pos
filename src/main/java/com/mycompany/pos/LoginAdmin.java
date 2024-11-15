package com.mycompany.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginAdmin extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a grid layout for the login form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Add Title
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        grid.add(titleLabel, 0, 0, 2, 1);

        // Add Username Field
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        grid.add(usernameField, 1, 1);

        // Add Password Field
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        grid.add(passwordField, 1, 2);

        // Add Login Button
        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 3);

        // Add Message Label for Feedback
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        grid.add(messageLabel, 0, 4, 2, 1);

        // Set Login Button Action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validateCredentials(username, password)) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Login successful!");
                // Navigate to the respective dashboard (to be implemented)
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Invalid username or password.");
            }
        });

        // Set up the scene and stage
        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Mock validation method
    private boolean validateCredentials(String username, String password) {
        // Replace with actual validation logic (e.g., check a database)
        return username.equals("superadmin") && password.equals("admin123") ||
                username.equals("branchmanager") && password.equals("manager123");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

