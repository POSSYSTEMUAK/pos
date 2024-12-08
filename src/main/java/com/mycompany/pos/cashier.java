package com.mycompany.pos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.bson.Document;

public class cashier extends Application {

    private MongoCollection<Document> productCollection;
    private TableView<SaleItem> salesTable;
    private ObservableList<SaleItem> saleItems;
    private Label totalLabel;
    private double totalAmount = 0.0;

    @Override
    public void start(Stage primaryStage) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        productCollection = dbConnection.getProductCollection();

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #D8BFD8;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #000000; -fx-text-fill: #D8BFD8 ;");
        backButton.setOnAction(e -> {
            new cashierLoginFrame().start(new Stage());
            primaryStage.close();
        });

        HBox topBar = new HBox(backButton);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);
        mainLayout.setTop(topBar);

        VBox centerLayout = new VBox(10);
        centerLayout.setPadding(new Insets(20));
        centerLayout.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Sales Entry");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);

        TextField productNameField = new TextField();
        productNameField.setPromptText("Product Name");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        Button addButton = new Button("Add Product");
        addButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #D8BFD8;");

        inputBox.getChildren().addAll(productNameField, quantityField, addButton);

        salesTable = new TableView<>();
        salesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        saleItems = FXCollections.observableArrayList();

        TableColumn<SaleItem, String> productNameColumn = new TableColumn<>("Product Name");
        productNameColumn.setCellValueFactory(data -> data.getValue().productNameProperty());

        TableColumn<SaleItem, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty());

        TableColumn<SaleItem, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());

        salesTable.getColumns().addAll(productNameColumn, quantityColumn, priceColumn);
        salesTable.setItems(saleItems);

        totalLabel = new Label("Total: 0.0 PKR");
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button generateBillButton = new Button("Generate Bill");
        generateBillButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #D8BFD8;");

        HBox billBox = new HBox(10, totalLabel, generateBillButton);
        billBox.setAlignment(Pos.CENTER);

        addButton.setOnAction(e -> {
            String productName = productNameField.getText();
            String quantityText = quantityField.getText();

            if (productName.isEmpty() || quantityText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);

                Document product = productCollection.find(Filters.eq("name", productName)).first();

                if (product != null) {
                    double price = Double.parseDouble(product.getString("price").replace(" PKR", ""));
                    double totalPrice = price * quantity;

                    int currentStock = Integer.parseInt(product.getString("stock"));
                    if (currentStock >= quantity) {
                        productCollection.updateOne(
                                Filters.eq("name", productName),
                                new Document("$set", new Document("stock", String.valueOf(currentStock - quantity)))
                        );

                        saleItems.add(new SaleItem(productName, String.valueOf(quantity), String.format("%.2f PKR", totalPrice)));

                        totalAmount += totalPrice;
                        totalLabel.setText("Total: " + String.format("%.2f PKR", totalAmount));

                        productNameField.clear();
                        quantityField.clear();
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Warning", "Not enough stock available.");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Product not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Quantity must be a valid number.");
            }
        });

        generateBillButton.setOnAction(e -> {
            if (saleItems.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning", "No products added to generate a bill.");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Bill Generated", "Total Bill Amount: " + String.format("%.2f PKR", totalAmount));
                saleItems.clear();
                totalAmount = 0.0;
                totalLabel.setText("Total: 0.0 PKR");
            }
        });

        centerLayout.getChildren().addAll(titleLabel, inputBox, salesTable, billBox);

        mainLayout.setCenter(centerLayout);

        Scene scene = new Scene(mainLayout, 1440, 740);
        primaryStage.setTitle("Cashier Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class SaleItem {
        private final SimpleStringProperty productName;
        private final SimpleStringProperty quantity;
        private final SimpleStringProperty price;

        public SaleItem(String productName, String quantity, String price) {
            this.productName = new SimpleStringProperty(productName);
            this.quantity = new SimpleStringProperty(quantity);
            this.price = new SimpleStringProperty(price);
        }

        public SimpleStringProperty productNameProperty() {
            return productName;
        }

        public SimpleStringProperty quantityProperty() {
            return quantity;
        }

        public SimpleStringProperty priceProperty() {
            return price;
        }
    }
}
