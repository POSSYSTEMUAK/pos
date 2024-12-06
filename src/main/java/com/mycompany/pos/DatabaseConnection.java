package com.mycompany.pos;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
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

    public MongoCollection<Document> getEmployeeCollection() {
        return getDatabase().getCollection("Employees");
    }

    public MongoCollection<Document> getAdminCollection() {
        return getDatabase().getCollection("admin");
    }
    public MongoCollection<Document> getManagerCollection() {
        return getDatabase().getCollection("managers");
    }

    public MongoCollection<Document> getProductCollection() {
        return getDatabase().getCollection("products");
    }

    public MongoCollection<Document> getVendorCollection() {
        return getDatabase().getCollection("vendors");
    }
}
