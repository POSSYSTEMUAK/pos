package com.mycompany.pos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class BranchService {
    private final MongoCollection<Document> branchCollection;

    public BranchService() {
        MongoDatabase database = DatabaseConnection.getDatabase();
        branchCollection = database.getCollection("branches");
    }

    public void addBranch(String branchId, String name, String city) {
        Document branch = new Document("_id", branchId)
                .append("name", name)
                .append("city", city);
        branchCollection.insertOne(branch);
        System.out.println("Branch added successfully.");
    }
}
