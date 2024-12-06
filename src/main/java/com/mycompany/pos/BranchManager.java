package com.mycompany.pos;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class BranchManager extends Application {

    private final MongoCollection<Document> employeeCollection;

    public BranchManager() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        this.employeeCollection = dbConnection.getEmployeeCollection(); // Assuming getEmployeeCollection() returns the collection
    }

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

        PasswordField cashierPassword = new PasswordField();
        cashierPassword.setPromptText("Cashier Password");
        cashierPassword.setMaxWidth(250);

        Button addCashierButton = new Button("Add Cashier");
        Button showCashiersButton = new Button("Show Cashiers");

        // Cashier Table
        TableView<Document> cashierTable = new TableView<>();
        cashierTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Document, String> cashierNameColumn = new TableColumn<>("Name");
        cashierNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("name")));
        cashierNameColumn.setEditable(true); // Make the name editable

        TableColumn<Document, String> cashierRoleColumn = new TableColumn<>("Role");
        cashierRoleColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("role")));
        cashierRoleColumn.setEditable(false); // Make the role uneditable

        TableColumn<Document, String> cashierPasswordColumn = new TableColumn<>("Password");
        cashierPasswordColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("password")));
        cashierPasswordColumn.setEditable(true); // Make the password editable

        TableColumn<Document, Void> editCashierColumn = new TableColumn<>("Edit");
        editCashierColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document cashier = getTableView().getItems().get(getIndex());
                    // Create the Edit dialog
                    showEditDialog(cashier, cashierTable);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });

        cashierTable.getColumns().addAll(cashierNameColumn, cashierRoleColumn, cashierPasswordColumn, editCashierColumn);

        showCashiersButton.setOnAction(e -> {
            cashierTable.getItems().clear();
            employeeCollection.find(new Document("role", "Cashier")).forEach(cashierTable.getItems()::add);
        });

        VBox cashierSection = new VBox(10, cashierLabel, cashierName, cashierPassword, addCashierButton, showCashiersButton, cashierTable);
        cashierSection.setAlignment(Pos.CENTER);
        cashierSection.setVisible(false);

        // Data Entry Operator Management Section
        Label operatorLabel = new Label("Data Entry Operator Management");
        operatorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        TextField operatorName = new TextField();
        operatorName.setPromptText("Operator Name");
        operatorName.setMaxWidth(250);

        PasswordField operatorPassword = new PasswordField();
        operatorPassword.setPromptText("Operator Password");
        operatorPassword.setMaxWidth(250);

        Button addOperatorButton = new Button("Add Operator");
        Button showOperatorsButton = new Button("Show Operators");

        // Operator Table
        TableView<Document> operatorTable = new TableView<>();
        operatorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Document, String> operatorNameColumn = new TableColumn<>("Name");
        operatorNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("name")));
        operatorNameColumn.setEditable(true); // Make the name editable

        TableColumn<Document, String> operatorRoleColumn = new TableColumn<>("Role");
        operatorRoleColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("role")));
        operatorRoleColumn.setEditable(false); // Make the role uneditable

        TableColumn<Document, String> operatorPasswordColumn = new TableColumn<>("Password");
        operatorPasswordColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("password")));
        operatorPasswordColumn.setEditable(true); // Make the password editable

        TableColumn<Document, Void> editOperatorColumn = new TableColumn<>("Edit");
        editOperatorColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document operator = getTableView().getItems().get(getIndex());
                    // Create the Edit dialog
                    showEditDialog(operator, operatorTable);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });

        operatorTable.getColumns().addAll(operatorNameColumn, operatorRoleColumn, operatorPasswordColumn, editOperatorColumn);

        showOperatorsButton.setOnAction(e -> {
            operatorTable.getItems().clear();
            employeeCollection.find(new Document("role", "Data Entry Operator")).forEach(operatorTable.getItems()::add);
        });

        VBox operatorSection = new VBox(10, operatorLabel, operatorName, operatorPassword, addOperatorButton, showOperatorsButton, operatorTable);
        operatorSection.setAlignment(Pos.CENTER);
        operatorSection.setVisible(false);

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
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #58b89c;");

        // Set up the scene and stage
        Scene scene = new Scene(layout, 1440, 740);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add Cashier functionality
        addCashierButton.setOnAction(e -> {
            String name = cashierName.getText();
            String password = cashierPassword.getText();
            String role = "Cashier";  // Automatically set role as Cashier

            if (!name.isEmpty() && !password.isEmpty()) {
                Document employee = new Document("name", name)
                        .append("role", role)
                        .append("password", password)
                        .append("branchId", "branch_id"); // Replace with actual branch ID
                employeeCollection.insertOne(employee);
                System.out.println("Cashier added: " + name + " with role " + role);
                cashierName.clear();
                cashierPassword.clear();
            } else {
                System.out.println("Name and password are required.");
            }
        });

        // Add Operator functionality
        addOperatorButton.setOnAction(e -> {
            String name = operatorName.getText();
            String password = operatorPassword.getText();
            String role = "Data Entry Operator";  // Automatically set role as Data Entry Operator

            if (!name.isEmpty() && !password.isEmpty()) {
                Document employee = new Document("name", name)
                        .append("role", role)
                        .append("password", password)
                        .append("branchId", "branch_id"); // Replace with actual branch ID
                employeeCollection.insertOne(employee);
                System.out.println("Operator added: " + name + " with role " + role);
                operatorName.clear();
                operatorPassword.clear();
            } else {
                System.out.println("Name and password are required.");
            }
        });
    }

    private void showEditDialog(Document employee, TableView<Document> table) {
        Stage editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.setTitle("Edit Employee");

        TextField editName = new TextField(employee.getString("name"));
        editName.setPromptText("Name");

        PasswordField editPassword = new PasswordField();
        editPassword.setPromptText("Password");
        editPassword.setText(employee.getString("password"));

        Button saveButton = new Button("Save");

        saveButton.setOnAction(e -> {
            String newName = editName.getText();
            String newPassword = editPassword.getText();

            if (!newName.isEmpty() && !newPassword.isEmpty()) {
                employee.put("name", newName);
                employee.put("password", newPassword);
                employeeCollection.updateOne(
                        new Document("_id", employee.get("_id")),
                        new Document("$set", employee)
                );
                table.refresh();
                editStage.close();
                System.out.println("Employee updated: " + newName);
            } else {
                System.out.println("Name and password are required.");
            }
        });

        VBox dialogLayout = new VBox(10, editName, editPassword, saveButton);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogLayout, 1440, 740);
        editStage.setScene(dialogScene);
        editStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
