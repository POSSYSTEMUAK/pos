package com.mycompany.pos;

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

import java.util.Arrays;

public class ProductManagementApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Product List
        ObservableList<Product> products = FXCollections.observableArrayList();

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

        productTable.getColumns().addAll(nameColumn, priceColumn, categoryColumn, descriptionColumn, stockColumn, skuColumn);

        // Add Product Button Action
        addProductButton.setOnAction(e -> openAddProductForm(products));

        // Layout
        VBox layout = new VBox(10, addProductButton, productTable);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 800, 600);

        // CSS Styling
        scene.getStylesheets().add("style.css");

        primaryStage.setTitle("Product Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAddProductForm(ObservableList<Product> products) {
        Stage formStage = new Stage();
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.setTitle("Add New Product");

        // Form Fields
        TextField productNameField = new TextField();
        productNameField.setPromptText("Product Name");

        TextField productPriceField = new TextField();
        productPriceField.setPromptText("Price");

        ComboBox<String> productCategoryDropdown = new ComboBox<>();
        ObservableList<String> categories = FXCollections.observableArrayList(
                "Fruits", "Vegetables", "Snacks", "Beverages", "Dairy", "Meat", "Household Items", "Personal Care"
        );
        productCategoryDropdown.setItems(categories);
        productCategoryDropdown.setEditable(true);
        productCategoryDropdown.setPromptText("Category");

        TextArea productDescriptionField = new TextArea();
        productDescriptionField.setPromptText("Description");
        productDescriptionField.setWrapText(true);

        Label charCounter = new Label("0/100");
        charCounter.setStyle("-fx-font-size: 12px; -fx-text-fill: grey;");

        productDescriptionField.textProperty().addListener((obs, oldText, newText) -> {
            int charCount = newText.length();
            charCounter.setText(charCount + "/100");

            if (charCount > 100) {
                productDescriptionField.setText(oldText); // Revert to old text if limit exceeded
            }

            // Dynamically adjust height based on content
            productDescriptionField.setPrefHeight(20 + (charCount / 50) * 20);
        });

        TextField productStockField = new TextField();
        productStockField.setPromptText("Stock Quantity");

        TextField productSKUField = new TextField();
        productSKUField.setPromptText("SKU");

        Button saveButton = new Button("Save Product");
        styleButton(saveButton);

        // Save Button Action
        saveButton.setOnAction(e -> {
            String name = productNameField.getText();
            String price = productPriceField.getText();
            String category = productCategoryDropdown.getEditor().getText();
            String description = productDescriptionField.getText();
            String stock = productStockField.getText();
            String sku = productSKUField.getText();

            if (!name.isEmpty() && !price.isEmpty() && !category.isEmpty() &&
                    !description.isEmpty() && !stock.isEmpty() && !sku.isEmpty()) {

                // Price Validation
                if (!price.matches("\\d+(\\.\\d{1,2})?")) { // Regex for numeric input
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Price must be a numeric value.", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

                products.add(new Product(name, price + " PKR", category, description, stock, sku));
                formStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Form Layout
        VBox formLayout = new VBox(10,
                productNameField, productPriceField, productCategoryDropdown, productDescriptionField,
                charCounter, productStockField, productSKUField, saveButton);
        formLayout.setPadding(new Insets(10));

        Scene formScene = new Scene(formLayout, 400, 500);
        formScene.getStylesheets().add("style.css");
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

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(formatPrice(price)); // Ensure "PKR" is appended
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
