package com.mycompany.pos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.bson.Document;

public class SuperAdmin extends Application {

    private MongoDatabase database;

    @Override
    public void start(Stage primaryStage) {
        database = DatabaseConnection.getDatabase();

        primaryStage.setTitle("Super Admin Dashboard");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #d9534f; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            new LoginAdmin().start(new Stage());
            primaryStage.close();
        });

        HBox topBar = new HBox(backButton);
        topBar.setAlignment(Pos.TOP_LEFT);

        // Header Section
        Label header = new Label("Super Admin Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        HBox headerContainer = new HBox(header);
        headerContainer.setAlignment(Pos.CENTER);

        VBox topSection = new VBox(topBar, headerContainer);
        topSection.setAlignment(Pos.CENTER);
        topSection.setSpacing(10);

        // Branch Management Section
        Label branchLabel = new Label("Branch Management");
        branchLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ComboBox<String> branchCityDropdown = new ComboBox<>();
        branchCityDropdown.getItems().addAll("Karachi", "Lahore", "Islamabad", "Peshawar", "Quetta");
        branchCityDropdown.setPromptText("Select City");
        branchCityDropdown.setMaxWidth(250);

        TextField branchName = new TextField();
        branchName.setPromptText("Branch Name");
        branchName.setMaxWidth(250);

        Button addBranchButton = new Button("Add Branch");
        Button showBranchesButton = new Button("Show Branches");

        // Branch Table
        TableView<Document> branchTable = new TableView<>();
        branchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Document, String> branchNameColumn = new TableColumn<>("Branch Name");
        branchNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("branchName")));

        TableColumn<Document, String> branchCityColumn = new TableColumn<>("Branch City");
        branchCityColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("branchCity")));

        TableColumn<Document, Void> editBranchColumn = new TableColumn<>("Edit");
        editBranchColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document branch = getTableView().getItems().get(getIndex());
                    branchName.setText(branch.getString("branchName"));
                    branchCityDropdown.setValue(branch.getString("branchCity"));
                    System.out.println("Editing Branch: " + branch.toJson());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });

        branchTable.getColumns().addAll(branchNameColumn, branchCityColumn, editBranchColumn);

        showBranchesButton.setOnAction(e -> {
            branchTable.getItems().clear();
            MongoCollection<Document> branches = database.getCollection("branches");
            branches.find().forEach(branchTable.getItems()::add);
        });

        VBox branchSection = new VBox(10, branchLabel, branchCityDropdown, branchName, addBranchButton, showBranchesButton, branchTable);
        branchSection.setAlignment(Pos.CENTER);
        branchSection.setVisible(false);

        // Branch Manager Management Section
        Label branchManagerLabel = new Label("Branch Manager Management");
        branchManagerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField managerName = new TextField();
        managerName.setPromptText("Manager Name");
        managerName.setMaxWidth(250);

        TextField managerEmail = new TextField();
        managerEmail.setPromptText("Manager Email");
        managerEmail.setMaxWidth(250);

        PasswordField managerPassword = new PasswordField();
        managerPassword.setPromptText("Manager Password");
        managerPassword.setMaxWidth(250);

        Button addManagerButton = new Button("Add Manager");
        Button showManagersButton = new Button("Show Managers");

        // Manager Table
        TableView<Document> managerTable = new TableView<>();
        managerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Document, String> managerNameColumn = new TableColumn<>("Manager Name");
        managerNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("managerName")));

        TableColumn<Document, String> managerEmailColumn = new TableColumn<>("Manager Email");
        managerEmailColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("managerEmail")));

        TableColumn<Document, Void> editManagerColumn = new TableColumn<>("Edit");
        editManagerColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document manager = getTableView().getItems().get(getIndex());
                    managerName.setText(manager.getString("managerName"));
                    managerEmail.setText(manager.getString("managerEmail"));
                    managerPassword.setText(manager.getString("managerPassword"));
                    System.out.println("Editing Manager: " + manager.toJson());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });

        managerTable.getColumns().addAll(managerNameColumn, managerEmailColumn, editManagerColumn);

        showManagersButton.setOnAction(e -> {
            managerTable.getItems().clear();
            MongoCollection<Document> managers = database.getCollection("managers");
            managers.find().forEach(managerTable.getItems()::add);
        });

        VBox managerSection = new VBox(10, branchManagerLabel, managerName, managerEmail, managerPassword, addManagerButton, showManagersButton, managerTable);
        managerSection.setAlignment(Pos.CENTER);
        managerSection.setVisible(false);

        // Adding branches
        addBranchButton.setOnAction(e -> {
            String name = branchName.getText().trim();
            String city = branchCityDropdown.getValue();

            if (city != null && !name.isEmpty()) {
                MongoCollection<Document> branches = database.getCollection("Branches");
                Document branch = new Document("branchName", name)
                        .append("branchCity", city);
                branches.insertOne(branch);

                System.out.println("Branch added: " + branch.toJson());
                branchName.clear();
                branchCityDropdown.setValue(null);
            } else {
                System.out.println("All fields are required.");
            }
        });

        // Adding managers
        addManagerButton.setOnAction(e -> {
            String name = managerName.getText().trim();
            String email = managerEmail.getText().trim();
            String password = managerPassword.getText().trim();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                MongoCollection<Document> managers = database.getCollection("managers");
                Document manager = new Document("managerName", name)
                        .append("managerEmail", email)
                        .append("managerPassword", password);
                managers.insertOne(manager);

                System.out.println("Manager added: " + manager.toJson());
                managerName.clear();
                managerEmail.clear();
                managerPassword.clear();
            } else {
                System.out.println("All fields are required.");
            }
        });

        // Overlay sections
        StackPane contentPane = new StackPane(branchSection, managerSection);
        contentPane.setAlignment(Pos.CENTER);
        contentPane.setPadding(new Insets(20));

        Button branchManagementButton = new Button("Branch Management");
        Button branchManagerButton = new Button("Branch Manager Management");

        branchManagementButton.setOnAction(e -> {
            branchSection.setVisible(true);
            managerSection.setVisible(false);
        });

        branchManagerButton.setOnAction(e -> {
            managerSection.setVisible(true);
            branchSection.setVisible(false);
        });

        HBox toggleButtons = new HBox(20, branchManagementButton, branchManagerButton);
        toggleButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, topSection, toggleButtons, contentPane);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #FFFFFF;");

        Scene scene = new Scene(layout, 1440, 740);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
