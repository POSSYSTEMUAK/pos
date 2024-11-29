package com.mycompany.pos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class EmployeeService {
    private final MongoCollection<Document> employeeCollection;

    public EmployeeService() {
        MongoDatabase database = DatabaseConnection.getDatabase();
        employeeCollection = database.getCollection("employees");
    }

    public void addEmployee(String employeeId, String name, String role, String branchId, String password) {
        Document employee = new Document("_id", employeeId)
                .append("name", name)
                .append("role", role)
                .append("branch_id", branchId)
                .append("password", password); // Store securely (e.g., hash it)
        employeeCollection.insertOne(employee);
        System.out.println("Employee added successfully.");
    }

    public void resetPassword(String employeeId, String newPassword) {
        employeeCollection.updateOne(eq("_id", employeeId), Updates.set("password", newPassword));
        System.out.println("Password reset successfully.");
    }
}
