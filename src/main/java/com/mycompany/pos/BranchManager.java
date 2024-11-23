package com.mycompany.pos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BranchManager extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Branch Manager Dashboard");

        // Header Section
        Label header = new Label("Branch Manager Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        // Cashier Management Section
        Label cashierLabel = new Label("Cashier Management");
        cashierLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        TextField cashierName = new TextField();
        cashierName.setPromptText("Cashier Name");
        cashierName.setMaxWidth(250);

        TextField cashierRole = new TextField();
        cashierRole.setPromptText("Role (Cashier)");
        cashierRole.setMaxWidth(250);

        Button addCashierButton = new Button("Add Cashier");
        Button resetCashierPasswordButton = new Button("Reset Password");

        VBox cashierSection = new VBox(10, cashierLabel, cashierName, cashierRole, addCashierButton, resetCashierPasswordButton);
        cashierSection.setAlignment(Pos.CENTER);
        cashierSection.setVisible(false); // Initially hidden

        // Data Entry Operator Management Section
        Label operatorLabel = new Label("Data Entry Operator Management");
        operatorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        TextField operatorName = new TextField();
        operatorName.setPromptText("Operator Name");
        operatorName.setMaxWidth(250);

        TextField operatorRole = new TextField();
        operatorRole.setPromptText("Role (Data Entry Operator)");
        operatorRole.setMaxWidth(250);

        Button addOperatorButton = new Button("Add Operator");
        Button resetOperatorPasswordButton = new Button("Reset Password");

        VBox operatorSection = new VBox(10, operatorLabel, operatorName, operatorRole, addOperatorButton, resetOperatorPasswordButton);
        operatorSection.setAlignment(Pos.CENTER);
        operatorSection.setVisible(false); // Initially hidden

        // StackPane to overlay the sections
        StackPane contentPane = new StackPane(cashierSection, operatorSection);
        contentPane.setAlignment(Pos.CENTER);
        contentPane.setPadding(new Insets(20));

        // Buttons for toggling sections
        Button cashierManagementButton = new Button("Cashier");
        Button operatorManagementButton = new Button("Data Entry Operator");

        cashierManagementButton.setOnAction(e -> {
            cashierSection.setVisible(true);
            operatorSection.setVisible(false);
        });

        operatorManagementButton.setOnAction(e -> {
            cashierSection.setVisible(false);
            operatorSection.setVisible(true);
        });

        HBox toggleButtons = new HBox(20, cashierManagementButton, operatorManagementButton);
        toggleButtons.setAlignment(Pos.CENTER);

        // Main Layout
        VBox layout = new VBox(20, header, toggleButtons, contentPane);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER); // Center everything at the top
        layout.setStyle("-fx-background-color: #FFFFFF;"); // White background color

        // Set up the scene and stage
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
