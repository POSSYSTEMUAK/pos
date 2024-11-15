package com.mycompany.pos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SuperAdmin extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Super Admin Dashboard");

        Label header = new Label("Super Admin Dashboard");

        // Branch Management Section
        Label branchLabel = new Label("Branch Management");
        TextField branchID = new TextField();
        branchID.setPromptText("Branch ID");
        TextField branchName = new TextField();
        branchName.setPromptText("Branch Name");
        TextField branchCity = new TextField();
        branchCity.setPromptText("City");

        Button addBranchButton = new Button("Add Branch");
        Button editBranchButton = new Button("Edit Branch");

        // Branch Manager Section
        Label managerLabel = new Label("Branch Manager Management");
        TextField managerName = new TextField();
        managerName.setPromptText("Manager Name");
        TextField managerBranchID = new TextField();
        managerBranchID.setPromptText("Branch ID");

        Button assignManagerButton = new Button("Assign Manager");

        VBox layout = new VBox(10, header, branchLabel, branchID, branchName, branchCity,
                addBranchButton, editBranchButton, managerLabel, managerName,
                managerBranchID, assignManagerButton);

        Scene scene = new Scene(layout, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

