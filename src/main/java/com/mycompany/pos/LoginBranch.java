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

public class LoginBranch extends Application {
    private DatabaseConnection dbConnection = new DatabaseConnection();

    @Override
    public void start(Stage primaryStage) {
        HBox mainLayout = new HBox();
        mainLayout.setSpacing(10);
        mainLayout.setStyle("-fx-background-color: #D8BFD8;");
        mainLayout.setAlignment(Pos.CENTER);

        Image image = new Image("file:C:/Users/talat/IdeaProjects/pos/app.png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(450);
        VBox leftBox = new VBox(imageView);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(0));
        mainLayout.getChildren().add(leftBox);

        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);

        Label titleLabel = new Label("Branch Manager");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        loginGrid.add(titleLabel, 0, 0, 2, 1);

        Label usernameLabel = new Label("Name:");
        usernameLabel.setStyle("-fx-font-size: 20px;");
        loginGrid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        loginGrid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 20px;");
        loginGrid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
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

            if (validateBranchManager(username, password)) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Login successful!");
                new BranchManager().start(new Stage()); // Opens BranchManager after successful login
                primaryStage.close();
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Invalid username or password.");
            }
        });

        mainLayout.getChildren().add(loginGrid);

        StackPane root = new StackPane(mainLayout);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1440, 740);
        primaryStage.setTitle("Branch Manager Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean validateBranchManager(String username, String password) {
        MongoCollection<Document> collection = dbConnection.getEmployeeCollection();
        Document query = new Document("username", username).append("password", password);
        Document result = collection.find(query).first();
        return result != null; // Returns true if a matching document is found
    }

    public static void main(String[] args) {
        launch(args);
    }
}
