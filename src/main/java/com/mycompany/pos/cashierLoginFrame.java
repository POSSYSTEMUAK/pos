package com.mycompany.pos;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

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

public class cashierLoginFrame extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main layout using BorderPane
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #D8BFD8;");

        // Back Button in the Top-Left
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #000000; -fx-text-fill: #D8BFD8;");
        backButton.setOnAction(e -> {
            new LoginForm().start(new Stage()); // Navigate back to LoginFrame
            primaryStage.close(); // Close the current stage
        });

        HBox topBar = new HBox(backButton);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);
        mainLayout.setTop(topBar);

        // Center Section: Image and Login Form
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(20);

        // Image
        Image image = new Image("file:images/ca.png/");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(450);
        imageView.setFitHeight(300);
        centerBox.getChildren().add(imageView);

        // Login Grid
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);

        // Add Title
        Label titleLabel = new Label("Cashier");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        loginGrid.add(titleLabel, 0, 0, 2, 1);

        // Add Username Field
        Label usernameLabel = new Label("Name:");
        usernameLabel.setStyle("-fx-font-size: 20px;");
        loginGrid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-font-size: 10px; -fx-padding: 10;");
        usernameField.setPromptText("Enter username");
        loginGrid.add(usernameField, 1, 1);

        // Add Password Field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 20px;");
        loginGrid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-font-size: 10px; -fx-padding: 10;");
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

            if (validateCashier(username, password)) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Login successful!");

                // Open Cashier page after successful login
                new cashier().start(new Stage());
                primaryStage.close(); // Close the current window
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Invalid username, password, or role.");
            }
        });

        centerBox.getChildren().add(loginGrid);
        mainLayout.setCenter(centerBox);

        // Set up the scene and stage
        Scene scene = new Scene(mainLayout, 1440, 740);
        primaryStage.setTitle("Cashier Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // MongoDB validation method
    public boolean validateCashier(String username, String password) {
        try {
            // Connect to the MongoDB employee collection
            DatabaseConnection dbConnection = new DatabaseConnection();
            MongoCollection<Document> employeeCollection = dbConnection.getEmployeeCollection();

            // Query to check credentials and role (Cashier)
            Document query = new Document("name", username)
                    .append("password", password)
                    .append("role", "Cashier");

            Document result = employeeCollection.find(query).first();

            return result != null; // Return true if a match is found
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of any exception
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
