package com.mycompany.pos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
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

    private MongoDatabase database;

    @Override
    public void start(Stage primaryStage) {
        // Connect to MongoDB using the DatabaseConnection class
        database = DatabaseConnection.getDatabase();

        primaryStage.setTitle("Super Admin Dashboard");

        // Header Section
        Label header = new Label("Super Admin Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        // Branch Management Section
        Label branchLabel = new Label("Branch Management");
        branchLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        TextField branchID = new TextField();
        branchID.setPromptText("Branch ID");
        branchID.setMaxWidth(250);

        TextField branchName = new TextField();
        branchName.setPromptText("Branch Name");
        branchName.setMaxWidth(250);

        TextField branchCity = new TextField();
        branchCity.setPromptText("City");
        branchCity.setMaxWidth(250);

        Button addBranchButton = new Button("Add Branch");
        Button editBranchButton = new Button("Edit Branch");

        // Add Branch functionality
        addBranchButton.setOnAction(e -> {
            String id = branchID.getText().trim();
            String name = branchName.getText().trim();
            String city = branchCity.getText().trim();

            if (!id.isEmpty() && !name.isEmpty() && !city.isEmpty()) {
                MongoCollection<Document> branches = database.getCollection("Branches");
                Document branch = new Document("branchID", id)
                        .append("branchName", name)
                        .append("branchCity", city);
                branches.insertOne(branch);

                System.out.println("Branch added: " + branch.toJson());
                clearTextFields(branchID, branchName, branchCity);
            } else {
                System.out.println("All fields are required.");
            }
        });

        // Edit Branch functionality (basic example, assumes matching by ID)
        editBranchButton.setOnAction(e -> {
            String id = branchID.getText().trim();
            String name = branchName.getText().trim();
            String city = branchCity.getText().trim();

            if (!id.isEmpty() && (!name.isEmpty() || !city.isEmpty())) {
                MongoCollection<Document> branches = database.getCollection("Branches");
                Document query = new Document("branchID", id);
                Document updateFields = new Document();
                if (!name.isEmpty()) updateFields.append("branchName", name);
                if (!city.isEmpty()) updateFields.append("branchCity", city);

                branches.updateOne(query, new Document("$set", updateFields));
                System.out.println("Branch updated: " + updateFields.toJson());
                clearTextFields(branchID, branchName, branchCity);
            } else {
                System.out.println("Branch ID and at least one other field are required.");
            }
        });

        VBox branchSection = new VBox(10, branchLabel, branchID, branchName, branchCity, addBranchButton, editBranchButton);
        branchSection.setAlignment(Pos.CENTER);
        branchSection.setVisible(false); // Initially hidden

        // Branch Manager Section
        Label managerLabel = new Label("Branch Manager Management");
        managerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        TextField managerName = new TextField();
        managerName.setPromptText("Manager Name");
        managerName.setMaxWidth(250);

        TextField managerBranchID = new TextField();
        managerBranchID.setPromptText("Branch ID");
        managerBranchID.setMaxWidth(250);

        Button assignManagerButton = new Button("Assign Manager");

        // Assign Manager functionality
        assignManagerButton.setOnAction(e -> {
            String name = managerName.getText().trim();
            String branchId = managerBranchID.getText().trim();

            if (!name.isEmpty() && !branchId.isEmpty()) {
                MongoCollection<Document> managers = database.getCollection("BranchManagers");
                Document manager = new Document("managerName", name)
                        .append("branchID", branchId);
                managers.insertOne(manager);

                System.out.println("Manager assigned: " + manager.toJson());
                clearTextFields(managerName, managerBranchID);
            } else {
                System.out.println("All fields are required.");
            }
        });

        VBox managerSection = new VBox(10, managerLabel, managerName, managerBranchID, assignManagerButton);
        managerSection.setAlignment(Pos.CENTER);
        managerSection.setVisible(false); // Initially hidden

        // StackPane to overlay the sections
        StackPane contentPane = new StackPane(branchSection, managerSection);
        contentPane.setAlignment(Pos.CENTER);
        contentPane.setPadding(new Insets(20));

        // Buttons for toggling sections
        Button branchManagementButton = new Button("Branch");
        Button branchManagerButton = new Button("Manager");

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
        VBox layout = new VBox(20, header, toggleButtons, contentPane);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #FFFFFF;");

        // Set up the scene and stage
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearTextFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
