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
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DataEntry extends Application {

    private final MongoCollection<Document> productCollection;
    private final MongoCollection<Document> vendorCollection;

    public DataEntry() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("POS");
        this.productCollection = database.getCollection("products");
        this.vendorCollection = database.getCollection("vendors");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data Entry Dashboard");

        // Header Section
        Label header = new Label("Data Entry Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        // Back Button at the Top-Left
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #000000; -fx-text-fill: #ADD8E6;");
        backButton.setOnAction(e -> {
            new dataEntryOperatorLoginFrame().start(new Stage()); // Go back to DataEntryOperatorLoginFrame
            primaryStage.close(); // Close the current DataEntry window
        });

        HBox topBar = new HBox(backButton);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setStyle("-fx-background-color: #ADD8E6;");

        // Product Management Section
        Label productLabel = new Label("Product Management");
        productLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        // Product Form
        TextField productName = new TextField();
        productName.setPromptText("Product Name");
        productName.setMaxWidth(250);
        TextField productPrice = new TextField();
        productPrice.setPromptText("Price");
        productPrice.setMaxWidth(250);
        TextField productCategory = new TextField();
        productCategory.setPromptText("Category");
        productCategory.setMaxWidth(250);
        TextField productDescription = new TextField();
        productDescription.setPromptText("Description");
        productDescription.setMaxWidth(250);
        TextField productStock = new TextField();
        productStock.setPromptText("Stock");
        productStock.setMaxWidth(250);
        TextField productSKU = new TextField();
        productSKU.setPromptText("SKU");
        productSKU.setMaxWidth(250);
        Button addProductButton = new Button("Add Product");
        Button showProductsButton = new Button("Show Products");

        // Product Table
        TableView<Document> productTable = new TableView<>();
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Document, String> productNameColumn = new TableColumn<>("Name");
        productNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("name")));

        TableColumn<Document, String> productPriceColumn = new TableColumn<>("Price");
        productPriceColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("price")));

        TableColumn<Document, String> productCategoryColumn = new TableColumn<>("Category");
        productCategoryColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("category")));

        TableColumn<Document, String> productDescriptionColumn = new TableColumn<>("Description");
        productDescriptionColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("description")));

        TableColumn<Document, String> productStockColumn = new TableColumn<>("Stock");
        productStockColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("stock")));

        TableColumn<Document, String> productSKUColumn = new TableColumn<>("SKU");
        productSKUColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("sku")));

        TableColumn<Document, Void> editProductColumn = new TableColumn<>("Edit");
        editProductColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document product = getTableView().getItems().get(getIndex());
                    showEditDialog(product, productTable, true);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
                setAlignment(Pos.CENTER);
            }
        });

        productTable.getColumns().addAll(productNameColumn, productPriceColumn, productCategoryColumn, productDescriptionColumn, productStockColumn, productSKUColumn, editProductColumn);

        showProductsButton.setOnAction(e -> {
            productTable.getItems().clear();
            productCollection.find().forEach(productTable.getItems()::add);
        });

        VBox productSection = new VBox(10, productLabel, productName, productPrice, productCategory, productDescription, productStock, productSKU, addProductButton, showProductsButton, productTable);
        productSection.setAlignment(Pos.CENTER);
        productSection.setVisible(false);

        // Vendor Management Section
        Label vendorLabel = new Label("Vendor Management");
        vendorLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        // Vendor Form
        TextField vendorName = new TextField();
        vendorName.setPromptText("Vendor Name");
        vendorName.setMaxWidth(250);
        TextField vendorContact = new TextField();
        vendorContact.setPromptText("Contact");
        vendorContact.setMaxWidth(250);
        Button addVendorButton = new Button("Add Vendor");
        Button showVendorsButton = new Button("Show Vendors");

        // Vendor Table
        TableView<Document> vendorTable = new TableView<>();
        vendorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Document, String> vendorNameColumn = new TableColumn<>("Name");
        vendorNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("name")));

        TableColumn<Document, String> vendorContactColumn = new TableColumn<>("Contact");
        vendorContactColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("contact")));

        TableColumn<Document, Void> editVendorColumn = new TableColumn<>("Edit");
        editVendorColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document vendor = getTableView().getItems().get(getIndex());
                    showEditDialog(vendor, vendorTable, false);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });

        vendorTable.getColumns().addAll(vendorNameColumn, vendorContactColumn, editVendorColumn);

        showVendorsButton.setOnAction(e -> {
            vendorTable.getItems().clear();
            vendorCollection.find().forEach(vendorTable.getItems()::add);
        });

        VBox vendorSection = new VBox(10, vendorLabel, vendorName, vendorContact, addVendorButton, showVendorsButton, vendorTable);
        vendorSection.setAlignment(Pos.CENTER);
        vendorSection.setVisible(false);

        // StackPane to overlay the sections
        StackPane contentPane = new StackPane(productSection, vendorSection);
        contentPane.setAlignment(Pos.CENTER);
        contentPane.setPadding(new Insets(20));

        // Buttons for toggling sections
        Button productManagementButton = new Button("Product");
        Button vendorManagementButton = new Button("Vendor");

        productManagementButton.setOnAction(e -> {
            productSection.setVisible(true);
            vendorSection.setVisible(false);
        });

        vendorManagementButton.setOnAction(e -> {
            productSection.setVisible(false);
            vendorSection.setVisible(true);
        });

        HBox toggleButtons = new HBox(20, productManagementButton, vendorManagementButton);
        toggleButtons.setAlignment(Pos.CENTER);

        // Main Layout
        VBox layout = new VBox(20, topBar, header, toggleButtons, contentPane);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #ADD8E6;");

        // Set up the scene and stage
        Scene scene = new Scene(layout, 1440, 740);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add Product functionality
        addProductButton.setOnAction(e -> {
            String name = productName.getText();
            String price = productPrice.getText();
            String category = productCategory.getText();
            String description = productDescription.getText();
            String stock = productStock.getText();
            String sku = productSKU.getText();

            if (!name.isEmpty() && !price.isEmpty() && !category.isEmpty() && !description.isEmpty() && !stock.isEmpty() && !sku.isEmpty()) {
                Document product = new Document("name", name)
                        .append("price", price)
                        .append("category", category)
                        .append("description", description)
                        .append("stock", stock)
                        .append("sku", sku);
                productCollection.insertOne(product);
                productName.clear();
                productPrice.clear();
                productCategory.clear();
                productDescription.clear();
                productStock.clear();
                productSKU.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all the fields");
            }
        });

        // Add Vendor functionality
        addVendorButton.setOnAction(e -> {
            String name = vendorName.getText();
            String contact = vendorContact.getText();

            if (!name.isEmpty() && !contact.isEmpty()) {
                Document vendor = new Document("name", name)
                        .append("contact", contact);
                vendorCollection.insertOne(vendor);
                vendorName.clear();
                vendorContact.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all the fields");
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showEditDialog(Document document, TableView<Document> table, boolean isProduct) {
        Stage editStage = new Stage();
        editStage.setTitle("Edit " + (isProduct ? "Product" : "Vendor"));

        VBox editLayout = new VBox(10);
        editLayout.setPadding(new Insets(20));
        editLayout.setAlignment(Pos.CENTER);

        // Editable Fields
        TextField nameField = new TextField(document.getString("name"));
        nameField.setPromptText("Name");

        TextField contactOrPriceField = new TextField(document.getString(isProduct ? "price" : "contact"));
        contactOrPriceField.setPromptText(isProduct ? "Price" : "Contact");

        TextField categoryField = isProduct ? new TextField(document.getString("category")) : null;
        if (isProduct) categoryField.setPromptText("Category");

        TextField descriptionField = isProduct ? new TextField(document.getString("description")) : null;
        if (isProduct) descriptionField.setPromptText("Description");

        TextField stockField = isProduct ? new TextField(document.getString("stock")) : null;
        if (isProduct) stockField.setPromptText("Stock");

        TextField skuField = isProduct ? new TextField(document.getString("sku")) : null;
        if (isProduct) skuField.setPromptText("SKU");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Update document with new values
            document.put("name", nameField.getText());
            if (isProduct) {
                document.put("price", contactOrPriceField.getText());
                document.put("category", categoryField.getText());
                document.put("description", descriptionField.getText());
                document.put("stock", stockField.getText());
                document.put("sku", skuField.getText());
                productCollection.replaceOne(new Document("_id", document.get("_id")), document);
            } else {
                document.put("contact", contactOrPriceField.getText());
                vendorCollection.replaceOne(new Document("_id", document.get("_id")), document);
            }

            // Refresh Table
            table.getItems().clear();
            (isProduct ? productCollection : vendorCollection).find().forEach(table.getItems()::add);

            editStage.close();
        });

        // Add fields to the layout
        editLayout.getChildren().add(nameField);
        editLayout.getChildren().add(contactOrPriceField);
        if (isProduct) {
            editLayout.getChildren().addAll(categoryField, descriptionField, stockField, skuField);
        }
        editLayout.getChildren().add(saveButton);

        // Scene and Stage Setup
        Scene scene = new Scene(editLayout, 400, isProduct ? 400 : 200);
        editStage.setScene(scene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
