package com.mycompany.pos;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VendorManagementApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Vendor List
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();

        // Form Elements
        TextField vendorNameField = new TextField();
        vendorNameField.setPromptText("Vendor Name");

        TextField vendorContactField = new TextField();
        vendorContactField.setPromptText("Contact Details");

        Button addVendorButton = new Button("Add Vendor");
        styleButton(addVendorButton);

        // TableView
        TableView<Vendor> vendorTable = new TableView<>(vendors);
        TableColumn<Vendor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Vendor, String> contactColumn = new TableColumn<>("Contact");
        contactColumn.setCellValueFactory(data -> data.getValue().contactProperty());

        vendorTable.getColumns().addAll(nameColumn, contactColumn);

        // Add Vendor Logic
        addVendorButton.setOnAction(e -> {
            String name = vendorNameField.getText();
            String contact = vendorContactField.getText();
            if (!name.isEmpty() && !contact.isEmpty()) {
                vendors.add(new Vendor(name, contact));
                vendorNameField.clear();
                vendorContactField.clear();
            }
        });

        // Layout
        HBox form = new HBox(10, vendorNameField, vendorContactField, addVendorButton);
        form.setPadding(new Insets(10));
        VBox layout = new VBox(10, form, vendorTable);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 800, 600);

        // CSS Styling
        scene.getStylesheets().add("style.css");

        primaryStage.setTitle("Vendor Management");
        primaryStage.setScene(scene);
        primaryStage.show();
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

// Vendor Class
class Vendor {
    private final StringProperty name;
    private final StringProperty contact;

    public Vendor(String name, String contact) {
        this.name = new SimpleStringProperty(name);
        this.contact = new SimpleStringProperty(contact);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty contactProperty() {
        return contact;
    }
}
