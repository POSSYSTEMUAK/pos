package com.mycompany.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginBranch extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: lightpink;"); // Set light pink background color

        // Left Section: Add an image
        Image image = new Image("file:C:/Users/talat/IdeaProjects/pos/app.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300); // Set a preferred width for the image
        VBox leftBox = new VBox(imageView);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(20));
        mainLayout.setLeft(leftBox);

        // Right Section: Create a grid layout for the login form
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(20, 20, 20, 20));

        // Add Title
        Label titleLabel = new Label("Branch Manager");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        loginGrid.add(titleLabel, 0, 0, 2, 1);

        // Add Username Field
        Label usernameLabel = new Label("Username:");
        loginGrid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        loginGrid.add(usernameField, 1, 1);

        // Add Password Field
        Label passwordLabel = new Label("Password:");
        loginGrid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        loginGrid.add(passwordField, 1, 2);

        // Add Login Button
        Button loginButton = new Button("Login");
        loginGrid.add(loginButton, 1, 3);

        // Add Message Label for Feedback
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        loginGrid.add(messageLabel, 0, 4, 2, 1);

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

        mainLayout.setCenter(loginGrid);

        // Set up the scene and stage
        Scene scene = new Scene(mainLayout, 900, 600);
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
