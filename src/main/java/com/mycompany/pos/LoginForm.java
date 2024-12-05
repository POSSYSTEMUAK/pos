package com.mycompany.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("METRO POINT OF SALES SYSTEM");
        primaryStage.getIcons().add(new Image("file:metro icon.png")); // Set the window icon

        // Main layout
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #FFFFFF;");

        // Add image
        Image image = new Image("file:metro icon.png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300); // Adjust image size if needed

        // Buttons with custom style
        Button superAdminBtn = createRoundedButton("SUPER ADMIN");
        Button branchManagerBtn = createRoundedButton("BRANCH MANAGER");
        Button dataEntryOperatorBtn = createRoundedButton("DATA ENTRY OPERATOR");
        Button cashierBtn = createRoundedButton("CASHIER");

        // Button actions
        superAdminBtn.setOnAction(e -> openFrame(new LoginAdmin(), primaryStage));
        branchManagerBtn.setOnAction(e -> openFrame(new LoginBranch(), primaryStage));
        dataEntryOperatorBtn.setOnAction(e -> openFrame(new dataEntryOperatorLoginFrame(), primaryStage));
        cashierBtn.setOnAction(e -> openFrame(new cashierLoginFrame(), primaryStage));

        // Add components to layout
        mainLayout.getChildren().addAll(imageView, superAdminBtn, branchManagerBtn, dataEntryOperatorBtn, cashierBtn);

        // Scene setup
        Scene scene = new Scene(mainLayout, 1440, 740);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to create styled rounded buttons
    private Button createRoundedButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #FFCC00; -fx-text-fill: black; -fx-font-size: 16px; " +
                "-fx-font-weight: bold; -fx-background-radius: 20px;");
        button.setPrefSize(250, 40); // Button dimensions
        return button;
    }

    // Helper method to open a new frame
    private void openFrame(Application app, Stage currentStage) {
        try {
            Stage newStage = new Stage();
            app.start(newStage);
            currentStage.close(); // Close the current stage
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}