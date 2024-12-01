package com.mycompany.pos;

import com.mongodb.client.*;
import org.bson.Document;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VendorManagementApp extends Application {

    private MongoCollection<Document> vendorCollection;

    @Override
    public void start(Stage primaryStage) {
        // Initialize MongoDB connection
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("POS");
        vendorCollection = database.getCollection("vendors");

        // Vendor List
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();

        // Load vendors from MongoDB
        vendorCollection.find().forEach(doc -> vendors.add(
                new Vendor(doc.getString("name"), doc.getString("contact"))
        ));

        // Form Elements
        TextField vendorNameField = new TextField();
        vendorNameField.setPromptText("Vendor Name");

        TextField vendorContactField = new TextField();
        vendorContactField.setPromptText("Contact Details");

        Button addVendorButton = new Button("Add Vendor");
        styleButton(addVendorButton);

        // TableView
        TableView<Vendor> vendorTable = new TableView<>(vendors);
        vendorTable.setEditable(true);

        TableColumn<Vendor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            Vendor vendor = event.getRowValue();
            vendor.setName(event.getNewValue());
            updateVendorInDb(vendor);
        });

        TableColumn<Vendor, String> contactColumn = new TableColumn<>("Contact");
        contactColumn.setCellValueFactory(data -> data.getValue().contactProperty());
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactColumn.setOnEditCommit(event -> {
            Vendor vendor = event.getRowValue();
            vendor.setContact(event.getNewValue());
            updateVendorInDb(vendor);
        });

        TableColumn<Vendor, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button();

            {
                editButton.setGraphic(new ImageView(new Image("edit-icon.png")));
                editButton.setOnAction(event -> {
                    Vendor vendor = getTableView().getItems().get(getIndex());
                    vendorNameField.setText(vendor.getName());
                    vendorContactField.setText(vendor.getContact());
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

        vendorTable.getColumns().addAll(nameColumn, contactColumn, actionColumn);

        // Add Vendor Logic
        addVendorButton.setOnAction(e -> {
            String name = vendorNameField.getText();
            String contact = vendorContactField.getText();
            if (!name.isEmpty() && !contact.isEmpty()) {
                Vendor newVendor = new Vendor(name, contact);
                vendors.add(newVendor);
                addVendorToDb(newVendor);
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

    private void addVendorToDb(Vendor vendor) {
        Document doc = new Document("name", vendor.getName())
                .append("contact", vendor.getContact());
        vendorCollection.insertOne(doc);
    }

    private void updateVendorInDb(Vendor vendor) {
        vendorCollection.updateOne(
                new Document("name", vendor.getName()),
                new Document("$set", new Document("contact", vendor.getContact()))
        );
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

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public StringProperty contactProperty() {
        return contact;
    }
}
