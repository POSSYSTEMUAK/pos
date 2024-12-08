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

    private static final int SPLASH_DURATION = 2500;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: white;");

        ImageView logo = new ImageView(new Image("file:images/metro icon.png/"));
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);

        Label welcomeText = new Label("WELCOME TO METRO CASH AND CARRY");
        welcomeText.setFont(new Font("Sans-Serif", 20));
        welcomeText.setTextFill(Color.BLACK);

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressBar.setStyle("-fx-accent: #FFCC00;");

        layout.getChildren().addAll(welcomeText, logo, progressBar);

        Scene scene = new Scene(layout, 500, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

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