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
        productNameColumn.setEditable(true);

        TableColumn<Document, String> productPriceColumn = new TableColumn<>("Price");
        productPriceColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("price")));
        productPriceColumn.setEditable(true);

        TableColumn<Document, String> productCategoryColumn = new TableColumn<>("Category");
        productCategoryColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("category")));
        productCategoryColumn.setEditable(true);

        TableColumn<Document, String> productDescriptionColumn = new TableColumn<>("Description");
        productDescriptionColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("description")));
        productDescriptionColumn.setEditable(true);

        TableColumn<Document, String> productStockColumn = new TableColumn<>("Stock");
        productStockColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("stock")));
        productStockColumn.setEditable(true);

        TableColumn<Document, String> productSKUColumn = new TableColumn<>("SKU");
        productSKUColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("sku")));
        productSKUColumn.setEditable(true);

        TableColumn<Document, Void> editProductColumn = new TableColumn<>("Edit");
        editProductColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document product = getTableView().getItems().get(getIndex());
                    // Create the Edit dialog
                    showEditDialog(product, productTable, true);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
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
        vendorNameColumn.setEditable(true);

        TableColumn<Document, String> vendorContactColumn = new TableColumn<>("Contact");
        vendorContactColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getString("contact")));
        vendorContactColumn.setEditable(true);

        TableColumn<Document, Void> editVendorColumn = new TableColumn<>("Edit");
        editVendorColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Document vendor = getTableView().getItems().get(getIndex());
                    // Create the Edit dialog
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
        VBox layout = new VBox(20, header, toggleButtons, contentPane);
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
            }
        });
    }

    private void showEditDialog(Document document, TableView<Document> table, boolean isProduct) {
        Stage editStage = new Stage();
        editStage.setTitle(isProduct ? "Edit Product" : "Edit Vendor");

        // Form fields based on whether it's a product or vendor
        TextField nameField = new TextField(document.getString("name"));
        TextField priceField = new TextField(isProduct ? document.getString("price") : "");
        TextField categoryField = new TextField(isProduct ? document.getString("category") : "");
        TextField descriptionField = new TextField(isProduct ? document.getString("description") : "");
        TextField stockField = new TextField(isProduct ? document.getString("stock") : "");
        TextField skuField = new TextField(isProduct ? document.getString("sku") : "");
        TextField contactField = new TextField(isProduct ? "" : document.getString("contact"));

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Save the edited document back to MongoDB
            if (isProduct) {
                document.put("name", nameField.getText());
                document.put("price", priceField.getText());
                document.put("category", categoryField.getText());
                document.put("description", descriptionField.getText());
                document.put("stock", stockField.getText());
                document.put("sku", skuField.getText());
                productCollection.replaceOne(new Document("_id", document.get("_id")), document);
            } else {
                document.put("name", nameField.getText());
                document.put("contact", contactField.getText());
                vendorCollection.replaceOne(new Document("_id", document.get("_id")), document);
            }

            table.refresh();
            editStage.close();
        });

        VBox dialogLayout = new VBox(10, nameField, priceField, categoryField, descriptionField, stockField, skuField, contactField, saveButton);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogLayout, 300, 300);
        editStage.setScene(dialogScene);
        editStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}