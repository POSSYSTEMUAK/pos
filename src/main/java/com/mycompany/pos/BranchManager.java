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

public abstract class BranchManager extends Application {

    private final MongoCollection<Document> employeeCollection;

    public BranchManager() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        this.employeeCollection = dbConnection.getEmployeeCollection(); // Connect to Employee Collection
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Branch Manager Dashboard");

        // Header Section
        Label header = new Label("Branch Manager Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        // Back Button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #000000; -fx-text-fill: #78f686;");
        backButton.setOnAction(e -> {
            new LoginBranch().start(new Stage()); // Go back to LoginBranch
            primaryStage.close();
        });

        HBox topBar = new HBox(backButton);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setStyle("-fx-background-color: #78f686;");

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

        TableView<Document> cashierTable = createEmployeeTable("Cashier");

        showCashiersButton.setOnAction(e -> {
            cashierTable.getItems().clear();
            employeeCollection.find(new Document("role", "Cashier")).forEach(cashierTable.getItems()::add);
        });

        VBox cashierSection = new VBox(10, cashierLabel, cashierName, cashierPassword, addCashierButton, showCashiersButton, cashierTable);
        cashierSection.setAlignment(Pos.CENTER);

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

        TableView<Document> operatorTable = createEmployeeTable("Data Entry Operator");

        showOperatorsButton.setOnAction(e -> {
            operatorTable.getItems().clear();
            employeeCollection.find(new Document("role", "Data Entry Operator")).forEach(operatorTable.getItems()::add);
        });

        VBox operatorSection = new VBox(10, operatorLabel, operatorName, operatorPassword, addOperatorButton, showOperatorsButton, operatorTable);
        operatorSection.setAlignment(Pos.CENTER);

        // Toggle Buttons
        Button cashierManagementButton = new Button("Cashier");
        Button operatorManagementButton = new Button("Data Entry Operator");

        cashierSection.setVisible(true);
        operatorSection.setVisible(false);

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

        // Layout
        StackPane contentPane = new StackPane(cashierSection, operatorSection);
        contentPane.setPadding(new Insets(20));

        VBox mainContent = new VBox(20, header, toggleButtons, contentPane);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: #78f686;");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topBar);
        mainLayout.setCenter(mainContent);

        // Scene
        Scene scene = new Scene(mainLayout, 1440, 740);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add Employee functionality
        addCashierButton.setOnAction(e -> addEmployee(cashierName, cashierPassword, "Cashier"));
        addOperatorButton.setOnAction(e -> addEmployee(operatorName, operatorPassword, "Data Entry Operator"));
    }

    public TableView<Document> createEmployeeTable(String role) {
        TableView<Document> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Document, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("name")));

        TableColumn<Document, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("role")));

        TableColumn<Document, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("password")));

        TableColumn<Document, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document employee = getTableView().getItems().get(getIndex());
                    showEditDialog(employee, role);
                    getTableView().refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
                setAlignment(Pos.CENTER);
            }
        });

        table.getColumns().addAll(nameColumn, roleColumn, passwordColumn, editColumn);
        return table;
    }

    void addEmployee(TextField nameField, PasswordField passwordField, String role) {
        String name = nameField.getText();
        String password = passwordField.getText();

        if (!name.isEmpty() && !password.isEmpty()) {
            Document employee = new Document("name", name)
                    .append("role", role)
                    .append("password", password)
                    .append("branchId", "branch_id"); // Replace with the actual branch ID
            employeeCollection.insertOne(employee);
            System.out.println(role + " added: " + name);
            nameField.clear();
            passwordField.clear();
        } else {
            System.out.println("Name and password are required.");
        }
    }

    void showEditDialog(Document employee, String role) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit " + role);

        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(20));
        dialogLayout.setAlignment(Pos.CENTER);

        TextField nameField = new TextField(employee.getString("name"));
        nameField.setPromptText("Name");

        PasswordField passwordField = new PasswordField();
        passwordField.setText(employee.getString("password"));
        passwordField.setPromptText("Password");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String newName = nameField.getText();
            String newPassword = passwordField.getText();

            if (!newName.isEmpty() && !newPassword.isEmpty()) {
                employee.put("name", newName);
                employee.put("password", newPassword);
                employeeCollection.replaceOne(new Document("_id", employee.get("_id")), employee);
                System.out.println(role + " updated: " + newName);
                dialog.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Name and password cannot be empty.");
            }
        });

        dialogLayout.getChildren().addAll(new Label("Name:"), nameField, new Label("Password:"), passwordField, saveButton);
        Scene dialogScene = new Scene(dialogLayout, 300, 200);
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

    public abstract MongoCollection<Document> getEmployeeCollection();
}
