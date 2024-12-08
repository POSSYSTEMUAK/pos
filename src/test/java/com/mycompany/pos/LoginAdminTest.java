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

class LoginAdminTest {

    private DatabaseConnection dbConnection;
    private MongoCollection<Document> adminCollection;

    @BeforeEach
    void setUp() {
        // Initialize the real DatabaseConnection
        dbConnection = new DatabaseConnection();
        MongoDatabase database = dbConnection.getDatabase();
        adminCollection = database.getCollection("admin");

        // Check if test data exists and insert it only if it doesn't
        Document testAdmin = new Document("username", "testUser")
                .append("password", "testPass");

        if (adminCollection.find(new Document("username", "testUser")).first() == null) {
            adminCollection.insertOne(testAdmin);
        }
    }

    @Test
    void testValidateAdmin_Success() {
        LoginAdmin loginAdmin = new LoginAdmin();
        boolean result = loginAdmin.validateAdmin("testUser", "testPass");
        System.out.println("Test for successful login: " + (result ? "Success" : "Failure"));
        assertTrue(result, "Login should succeed with valid credentials");
    }

    @Test
    void testValidateAdmin_Failure() {
        LoginAdmin loginAdmin = new LoginAdmin();
        boolean result = loginAdmin.validateAdmin("wrongUser", "wrongPass");
        System.out.println("Test for failed login: " + (result ? "Success" : "Failure"));
        assertFalse(result, "Login should fail with invalid credentials");

    }
}