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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.Document;

public class SuperAdmin extends Application {

    private MongoDatabase database;

    @Override
    public void start(Stage primaryStage) {
        database = DatabaseConnection.getDatabase();

        primaryStage.setTitle("Super Admin Dashboard");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #000000; -fx-text-fill: white;");
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

        // Updated edit functionality for branches
        TableColumn<Document, Void> editBranchColumn = new TableColumn<>("Edit");
        editBranchColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document branch = getTableView().getItems().get(getIndex());
                    showEditDialog(branch, true); // true indicates branch
                    getTableView().refresh(); // Refresh the table after editing
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
                setAlignment(Pos.CENTER);
            }
        });

        branchTable.getColumns().addAll(branchNameColumn, branchCityColumn, editBranchColumn);

        showBranchesButton.setOnAction(e -> {
            branchTable.getItems().clear();
            MongoCollection<Document> branches = database.getCollection("Branches");
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

        // Updated edit functionality for managers
        TableColumn<Document, Void> editManagerColumn = new TableColumn<>("Edit");
        editManagerColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document manager = getTableView().getItems().get(getIndex());
                    showEditDialog(manager, false); // false indicates manager
                    getTableView().refresh(); // Refresh the table after editing
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

    private void showEditDialog(Document document, boolean isBranch) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(isBranch ? "Edit Branch" : "Edit Manager");

        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(20));
        dialogLayout.setAlignment(Pos.CENTER);

        TextField nameField = new TextField(document.getString(isBranch ? "branchName" : "managerName"));
        nameField.setPromptText(isBranch ? "Branch Name" : "Manager Name");

        ComboBox<String> cityDropdown;
        TextField emailField;
        PasswordField passwordField;

        if (isBranch) {
            passwordField = null;
            emailField = null;
            cityDropdown = new ComboBox<>();
            cityDropdown.getItems().addAll("Karachi", "Lahore", "Islamabad", "Peshawar", "Quetta");
            cityDropdown.setValue(document.getString("branchCity"));
            dialogLayout.getChildren().addAll(new Label("Branch Name:"), nameField, new Label("City:"), cityDropdown);
        } else {
            cityDropdown = null;
            emailField = new TextField(document.getString("managerEmail"));
            emailField.setPromptText("Manager Email");

            passwordField = new PasswordField();
            passwordField.setText(document.getString("managerPassword"));
            dialogLayout.getChildren().addAll(new Label("Manager Name:"), nameField, new Label("Email:"), emailField, new Label("Password:"), passwordField);
        }

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            if (isBranch) {
                String branchName = nameField.getText();
                String branchCity = cityDropdown.getValue();

                if (!branchName.isEmpty() && branchCity != null) {
                    document.put("branchName", branchName);
                    document.put("branchCity", branchCity);

                    MongoCollection<Document> branches = database.getCollection("Branches");
                    branches.replaceOne(new Document("_id", document.get("_id")), document);
                    dialog.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
                }
            } else {
                String managerName = nameField.getText();
                String managerEmail = emailField.getText();
                String managerPassword = passwordField.getText();

                if (!managerName.isEmpty() && !managerEmail.isEmpty() && !managerPassword.isEmpty()) {
                    document.put("managerName", managerName);
                    document.put("managerEmail", managerEmail);
                    document.put("managerPassword", managerPassword);

                    MongoCollection<Document> managers = database.getCollection("managers");
                    managers.replaceOne(new Document("_id", document.get("_id")), document);
                    dialog.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
                }
            }
        });

        dialogLayout.getChildren().add(saveButton);
        Scene dialogScene = new Scene(dialogLayout, 400, isBranch ? 250 : 300);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
