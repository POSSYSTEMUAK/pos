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

public class SuperAdmin extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Super Admin Dashboard");

        // Header Section
        Label header = new Label("Super Admin Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4B0082;");

        // Branch Management Section
        Label branchLabel = new Label("Branch Management");
        branchLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        TextField branchID = new TextField();
        branchID.setPromptText("Branch ID");
        branchID.setMaxWidth(250); // Restrict width

        TextField branchName = new TextField();
        branchName.setPromptText("Branch Name");
        branchName.setMaxWidth(250); // Restrict width

        TextField branchCity = new TextField();
        branchCity.setPromptText("City");
        branchCity.setMaxWidth(250); // Restrict width

        Button addBranchButton = new Button("Add Branch");
        Button editBranchButton = new Button("Edit Branch");
        HBox branchButtons = new HBox(10, addBranchButton, editBranchButton);
        branchButtons.setAlignment(Pos.CENTER);

        VBox branchSection = new VBox(10, branchLabel, branchID, branchName, branchCity, branchButtons);
        branchSection.setPadding(new Insets(10));
        branchSection.setAlignment(Pos.CENTER); // Center the contents
        branchSection.setVisible(false);

        // Branch Manager Section
        Label managerLabel = new Label("Branch Manager Management");
        managerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        TextField managerName = new TextField();
        managerName.setPromptText("Manager Name");
        managerName.setMaxWidth(250); // Restrict width

        TextField managerBranchID = new TextField();
        managerBranchID.setPromptText("Branch ID");
        managerBranchID.setMaxWidth(250); // Restrict width

        Button assignManagerButton = new Button("Assign Manager");

        VBox managerSection = new VBox(10, managerLabel, managerName, managerBranchID, assignManagerButton);
        managerSection.setPadding(new Insets(10));
        managerSection.setAlignment(Pos.CENTER); // Center the contents
        managerSection.setVisible(false);

        // Buttons for toggling sections
        Button branchManagementButton = new Button("Branch Management");
        Button branchManagerButton = new Button("Branch Manager Management");

        branchManagementButton.setOnAction(e -> {
            branchSection.setVisible(true);
            managerSection.setVisible(false);
        });

        branchManagerButton.setOnAction(e -> {
            branchSection.setVisible(false);
            managerSection.setVisible(true);
        });

        HBox toggleButtons = new HBox(20, branchManagementButton, branchManagerButton);
        toggleButtons.setAlignment(Pos.CENTER);

        // Main Layout
        VBox layout = new VBox(20, header, toggleButtons, branchSection, managerSection);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #F5F5F5;");

        // Set up the scene and stage
        Scene scene = new Scene(layout, 800, 600); // Adjusted size for better view
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
