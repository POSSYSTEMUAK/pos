package com.mycompany.pos;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseConnection {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "POS";

    public static MongoDatabase getDatabase() {
        MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    // New method to get the 'Employees' collection
    public MongoCollection<Document> getEmployeeCollection() {
        MongoDatabase database = getDatabase();  // Reuse the getDatabase() method
        return database.getCollection("Employees"); // Replace with the actual collection name if different
    }

    // New main method to test the connection and get the Employees collection
    public static void main(String[] args) {
        try {
            // Test the connection
            MongoDatabase database = getDatabase();
            System.out.println("Database connection successful!");

            // Get the 'Employees' collection
            DatabaseConnection dbConnection = new DatabaseConnection();
            MongoCollection<Document> employeeCollection = dbConnection.getEmployeeCollection();

            // Optionally, print the contents of the collection
            System.out.println("Fetching documents from 'Employees' collection...");
            for (Document doc : employeeCollection.find()) {
                System.out.println(doc.toJson());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error connecting to the database.");
        }
    }
}
