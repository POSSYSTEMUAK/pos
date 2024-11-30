package com.mycompany.pos;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.Document;

public class ProductManagementApp extends Application {
    private final String MONGO_URI = "mongodb://localhost:27017";
    private final String DATABASE_NAME = "POS";
    private final String COLLECTION_NAME = "products";

    private MongoClient mongoClient;
    private MongoCollection<Document> productCollection;

    @Override
    public void start(Stage primaryStage) {
        // Initialize MongoDB connection
        mongoClient = MongoClients.create(MONGO_URI);
        productCollection = mongoClient.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME);

        // Product List
        ObservableList<Product> products = FXCollections.observableArrayList();

        // Load existing products from MongoDB
        loadProductsFromDatabase(products);

        // Add Product Button
        Button addProductButton = new Button("Add Product");
        styleButton(addProductButton);

        // TableView
        TableView<Product> productTable = new TableView<>(products);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());

        TableColumn<Product, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

        TableColumn<Product, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(data -> data.getValue().stockProperty());

        TableColumn<Product, String> skuColumn = new TableColumn<>("SKU");
        skuColumn.setCellValueFactory(data -> data.getValue().skuProperty());

        // Edit Column
        TableColumn<Product, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                styleButton(editButton);
                editButton.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    openEditProductForm(products, product);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        productTable.getColumns().addAll(nameColumn, priceColumn, categoryColumn, descriptionColumn, stockColumn, skuColumn, editColumn);

        // Add Product Button Action
        addProductButton.setOnAction(e -> openAddProductForm(products));

        // Layout
        VBox layout = new VBox(10, addProductButton, productTable);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 900, 600);
        scene.getStylesheets().add("style.css");

        primaryStage.setTitle("Product Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadProductsFromDatabase(ObservableList<Product> products) {
        productCollection.find().forEach(doc -> {
            products.add(new Product(
                    doc.getString("name"),
                    doc.getString("price"),
                    doc.getString("category"),
                    doc.getString("description"),
                    doc.getString("stock"),
                    doc.getString("sku")
            ));
        });
    }

    private void openAddProductForm(ObservableList<Product> products) {
        Stage formStage = new Stage();
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.setTitle("Add New Product");

        TextField productNameField = new TextField();
        productNameField.setPromptText("Product Name");

        TextField productPriceField = new TextField();
        productPriceField.setPromptText("Price");

        ComboBox<String> productCategoryDropdown = new ComboBox<>(FXCollections.observableArrayList(
                "Fruits", "Vegetables", "Snacks", "Beverages", "Dairy", "Meat", "Household Items", "Personal Care"
        ));
        productCategoryDropdown.setEditable(true);
        productCategoryDropdown.setPromptText("Category");

        TextArea productDescriptionField = new TextArea();
        productDescriptionField.setPromptText("Description");

        TextField productStockField = new TextField();
        productStockField.setPromptText("Stock Quantity");

        TextField productSKUField = new TextField();
        productSKUField.setPromptText("SKU");

        Button saveButton = new Button("Save Product");
        styleButton(saveButton);

        saveButton.setOnAction(e -> {
            String name = productNameField.getText();
            String price = productPriceField.getText();
            String category = productCategoryDropdown.getEditor().getText();
            String description = productDescriptionField.getText();
            String stock = productStockField.getText();
            String sku = productSKUField.getText();

            if (!name.isEmpty() && !price.isEmpty() && !category.isEmpty() &&
                    !description.isEmpty() && !stock.isEmpty() && !sku.isEmpty()) {

                Product product = new Product(name, price + " PKR", category, description, stock, sku);
                products.add(product);

                Document doc = new Document("name", name)
                        .append("price", price + " PKR")
                        .append("category", category)
                        .append("description", description)
                        .append("stock", stock)
                        .append("sku", sku);
                productCollection.insertOne(doc);

                formStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        VBox formLayout = new VBox(10,
                productNameField, productPriceField, productCategoryDropdown, productDescriptionField,
                productStockField, productSKUField, saveButton);
        formLayout.setPadding(new Insets(10));

        Scene formScene = new Scene(formLayout, 400, 400);
        formStage.setScene(formScene);
        formStage.show();
    }

    private void openEditProductForm(ObservableList<Product> products, Product product) {
        Stage formStage = new Stage();
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.setTitle("Edit Product");

        TextField productNameField = new TextField(product.getName());
        TextField productPriceField = new TextField(product.getPrice().replace(" PKR", ""));
        ComboBox<String> productCategoryDropdown = new ComboBox<>(FXCollections.observableArrayList(
                "Fruits", "Vegetables", "Snacks", "Beverages", "Dairy", "Meat", "Household Items", "Personal Care"
        ));
        productCategoryDropdown.setValue(product.getCategory());
        TextArea productDescriptionField = new TextArea(product.getDescription());
        TextField productStockField = new TextField(product.getStock());
        TextField productSKUField = new TextField(product.getSku());

        Button saveButton = new Button("Save Changes");
        styleButton(saveButton);

        saveButton.setOnAction(e -> {
            String name = productNameField.getText();
            String price = productPriceField.getText();
            String category = productCategoryDropdown.getValue();
            String description = productDescriptionField.getText();
            String stock = productStockField.getText();
            String sku = productSKUField.getText();

            if (!name.isEmpty() && !price.isEmpty() && !category.isEmpty() &&
                    !description.isEmpty() && !stock.isEmpty() && !sku.isEmpty()) {

                product.setName(name);
                product.setPrice(price + " PKR");
                product.setCategory(category);
                product.setDescription(description);
                product.setStock(stock);
                product.setSku(sku);

                products.set(products.indexOf(product), product);

                productCollection.updateOne(Filters.eq("sku", product.getSku()),
                        new Document("$set", new Document("name", name)
                                .append("price", price + " PKR")
                                .append("category", category)
                                .append("description", description)
                                .append("stock", stock)
                                .append("sku", sku)));

                formStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        VBox formLayout = new VBox(10,
                productNameField, productPriceField, productCategoryDropdown, productDescriptionField,
                productStockField, productSKUField, saveButton);
        formLayout.setPadding(new Insets(10));

        Scene formScene = new Scene(formLayout, 400, 400);
        formStage.setScene(formScene);
        formStage.show();
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-font-size: 14;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-size: 14;"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Product Class
class Product {
    private final StringProperty name;
    private final StringProperty price;
    private final StringProperty category;
    private final StringProperty description;
    private final StringProperty stock;
    private final StringProperty sku;

    public Product(String name, String price, String category, String description, String stock, String sku) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(formatPrice(price));
        this.category = new SimpleStringProperty(category);
        this.description = new SimpleStringProperty(description);
        this.stock = new SimpleStringProperty(stock);
        this.sku = new SimpleStringProperty(sku);
    }

    private String formatPrice(String price) {
        if (!price.endsWith("PKR")) {
            return price + " PKR";
        }
        return price;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(formatPrice(price)); // Ensure "PKR" is appended
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getStock() {
        return stock.get();
    }

    public void setStock(String stock) {
        this.stock.set(stock);
    }

    public String getSku() {
        return sku.get();
    }

    public void setSku(String sku) {
        this.sku.set(sku);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty stockProperty() {
        return stock;
    }

    public StringProperty skuProperty() {
        return sku;
    }
}