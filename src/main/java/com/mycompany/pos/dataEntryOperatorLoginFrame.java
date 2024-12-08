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

public class dataEntryOperatorLoginFrame extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #ADD8E6;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #000000; -fx-text-fill: #ADD8E6;");
        backButton.setOnAction(e -> {
            new LoginForm().start(new Stage());
            primaryStage.close();
        });

        HBox topBar = new HBox(backButton);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);
        mainLayout.setTop(topBar);

        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(20);

        Image image = new Image("file:images/de.png/");
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

        Label titleLabel = new Label("Data Entry Operator");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        loginGrid.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(titleLabel, javafx.geometry.VPos.CENTER);

        Label usernameLabel = new Label("Name:");
        usernameLabel.setStyle("-fx-font-size: 20px;");
        loginGrid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-font-size: 10px; -fx-padding: 10;");
        usernameField.setPromptText("Enter username");
        loginGrid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 20px;");
        loginGrid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-font-size: 10px; -fx-padding: 10;");
        passwordField.setPromptText("Enter password");
        loginGrid.add(passwordField, 1, 2);

        Button loginButton = new Button("Login");
        loginGrid.add(loginButton, 1, 3);

        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        loginGrid.add(messageLabel, 0, 4, 2, 1);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validateCredentials(username, password)) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Login successful!");


                new DataEntry().start(new Stage());
                primaryStage.close();
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Invalid username or password.");
            }
        });


        centerBox.getChildren().add(loginGrid);
        mainLayout.setCenter(centerBox);

        Scene scene = new Scene(mainLayout, 1440, 740);
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean validateCredentials(String username, String password) {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            MongoCollection<Document> employeeCollection = dbConnection.getEmployeeCollection();

            Document query = new Document("name", username)
                    .append("password", password)
                    .append("role", "Data Entry Operator");

            Document result = employeeCollection.find(query).first();

            return result != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}