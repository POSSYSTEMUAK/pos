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

class LoginBranchTest {

        private DatabaseConnection dbConnection;
        private MongoCollection<Document> managerCollection;

        @BeforeEach
        void setUp() {
            // Initialize the real DatabaseConnection
            dbConnection = new DatabaseConnection();
            MongoDatabase database = dbConnection.getDatabase();
            managerCollection = database.getCollection("managers");

            // Check if test data exists and insert it only if it doesn't
            Document testManager = new Document("managerName", "testUser")
                    .append("managerPassword", "testPass");

            if (managerCollection.find(new Document("managerName", "testUser")).first() == null) {
                managerCollection.insertOne(testManager);
            }
        }

        @Test
        void testValidateBranchManager_Success() {
            LoginBranch loginBranch = new LoginBranch();
            boolean result = loginBranch.validateBranchManager("testUser", "testPass");
            System.out.println("Test for successful login: " + (result ? "Success" : "Failure"));
            assertTrue(result, "Login should succeed with valid credentials");
        }

        @Test
        void testValidateBranchManager_Failure() {
            LoginBranch loginBranch = new LoginBranch();
            boolean result = loginBranch.validateBranchManager("wrongUser", "wrongPass");
            System.out.println("Test for failed login: " + (result ? "Success" : "Failure"));
            assertFalse(result, "Login should fail with invalid credentials");

        }
    }