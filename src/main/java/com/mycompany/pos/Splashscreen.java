package com.mycompany.pos;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Splashscreen extends Application {

    private static final int SPLASH_DURATION = 5000; // Duration in milliseconds

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED); // No window decorations

        // Layout for splash screen
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: white;");

        // Image for splash screen
        ImageView logo = new ImageView(new Image("file:metro icon.png"));
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);

        // Welcome text
        Label welcomeText = new Label("WELCOME TO METRO CASH AND CARRY");
        welcomeText.setFont(new Font("Sans-Serif", 20));
        welcomeText.setTextFill(Color.BLACK);

        // Progress bar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);

        // Add components to layout
        layout.getChildren().addAll(welcomeText, logo, progressBar);

        // Scene and stage setup
        Scene scene = new Scene(layout, 500, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Update progress bar and switch to LoginForm after SPLASH_DURATION
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            for (int i = 1; i <= 100; i++) {
                int progress = i;
                try {
                    Thread.sleep(SPLASH_DURATION / 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                javafx.application.Platform.runLater(() -> progressBar.setProgress(progress / 100.0));
            }

            // After splash screen, show the LoginForm
            javafx.application.Platform.runLater(() -> {
                primaryStage.close(); // Close splash screen
                try {
                    new LoginForm().start(new Stage()); // Launch LoginForm
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        executor.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}