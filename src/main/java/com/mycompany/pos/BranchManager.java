package com.mycompany.pos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BranchManager extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Branch Manager Dashboard");

        Label header = new Label("Branch Manager Dashboard");

        // Employee Management Section
        Label employeeLabel = new Label("Employee Management");
        TextField employeeName = new TextField();
        employeeName.setPromptText("Employee Name");
        TextField employeeRole = new TextField();
        employeeRole.setPromptText("Role (Cashier/Data Entry Operator)");

        Button addEmployeeButton = new Button("Add Employee");
        Button resetPasswordButton = new Button("Reset Employee Password");

        VBox layout = new VBox(10, header, employeeLabel, employeeName, employeeRole,
                addEmployeeButton, resetPasswordButton);

        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}

