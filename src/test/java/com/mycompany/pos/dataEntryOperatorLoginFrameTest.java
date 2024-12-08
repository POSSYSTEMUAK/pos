package com.mycompany.pos;

import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class dataEntryOperatorLoginFrameTest {

    private DatabaseConnection dbConnection;
    private MongoCollection<Document> EmployeeCollection;

    @BeforeEach
    void setUp() {
        // Initialize the real DatabaseConnection
        dbConnection = new DatabaseConnection();
        MongoDatabase database = dbConnection.getDatabase();
        EmployeeCollection = database.getCollection("Employees");

        // Check if test data exists and insert it only if it doesn't
        Document testAdmin = new Document("name", "User")
                .append("role", "Data Entry Operator")
                .append("password", "Pass")
                .append("branchId", "branch_id");

        if (EmployeeCollection.find(new Document("name", "User")).first() == null) {
            EmployeeCollection.insertOne(testAdmin);
        }
    }


    @Test
    void testValidateAdmin_Success() {
        dataEntryOperatorLoginFrame dataEntryOperatorLoginFrame = new dataEntryOperatorLoginFrame();
        boolean result = dataEntryOperatorLoginFrame.validateCredentials("User", "Pass");
        System.out.println("Test for successful login: " + (result ? "Success" : "Failure"));
        assertTrue(result, "Login should succeed with valid credentials");
    }

    @Test
    void testValidateAdmin_Failure() {
        dataEntryOperatorLoginFrame dataEntryOperatorLoginFrame = new dataEntryOperatorLoginFrame();
        boolean result = dataEntryOperatorLoginFrame.validateCredentials("wrongUser", "wrongPass");
        System.out.println("Test for failed login: " + (result ? "Success" : "Failure"));
        assertFalse(result, "Login should fail with invalid credentials");

    }
}