package com.mycompany.pos;

import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.FindIterable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import com.mongodb.client.MongoCollection;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BranchManagerTest extends BranchManager {

    private MongoCollection<Document> mockEmployeeCollection;

    @Override
    public MongoCollection<Document> getEmployeeCollection() {
        return mockEmployeeCollection;
    }

    @BeforeEach
    void setUp() {
        // Mocking MongoDB Collection
        mockEmployeeCollection = Mockito.mock(MongoCollection.class);
    }

    @Test
    void testAddCashier() {
        // Arrange
        TextField cashierName = new TextField("John Doe");
        PasswordField cashierPassword = new PasswordField();
        cashierPassword.setText("secure123");

        // Act
        addEmployee(cashierName, cashierPassword, "Cashier");

        // Assert
        verify(mockEmployeeCollection, times(1)).insertOne(argThat(doc ->
                doc.getString("name").equals("John Doe") &&
                        doc.getString("role").equals("Cashier") &&
                        doc.getString("password").equals("secure123")
        ));
    }

    @Test
    void testShowCashiers() {
        // Arrange
        List<Document> fakeCashiers = new ArrayList<>();
        fakeCashiers.add(new Document("name", "Alice").append("role", "Cashier").append("password", "pass123"));
        fakeCashiers.add(new Document("name", "Bob").append("role", "Cashier").append("password", "pass456"));
        when(mockEmployeeCollection.find(new Document("role", "Cashier"))).thenReturn((FindIterable<Document>) fakeCashiers.iterator());

        TableView<Document> table = createEmployeeTable("Cashier");

        // Act
        table.getItems().clear();
        mockEmployeeCollection.find(new Document("role", "Cashier")).forEach(table.getItems()::add);

        // Assert
        Assertions.assertEquals(2, table.getItems().size());
        Assertions.assertEquals("Alice", table.getItems().get(0).getString("name"));
        Assertions.assertEquals("Bob", table.getItems().get(1).getString("name"));
    }

    @Test
    void testAddDataEntryOperator() {
        // Arrange
        TextField operatorName = new TextField("Jane Smith");
        PasswordField operatorPassword = new PasswordField();
        operatorPassword.setText("password789");

        // Act
        addEmployee(operatorName, operatorPassword, "Data Entry Operator");

        // Assert
        verify(mockEmployeeCollection, times(1)).insertOne(argThat(doc ->
                doc.getString("name").equals("Jane Smith") &&
                        doc.getString("role").equals("Data Entry Operator") &&
                        doc.getString("password").equals("password789")
        ));
    }

    @Test
    void testShowDataEntryOperators() {
        // Arrange
        List<Document> fakeOperators = new ArrayList<>();
        fakeOperators.add(new Document("name", "Charlie").append("role", "Data Entry Operator").append("password", "op123"));
        fakeOperators.add(new Document("name", "Dana").append("role", "Data Entry Operator").append("password", "op456"));
        when(mockEmployeeCollection.find(new Document("role", "Data Entry Operator"))).thenReturn((FindIterable<Document>) fakeOperators.iterator());

        TableView<Document> table = createEmployeeTable("Data Entry Operator");

        // Act
        table.getItems().clear();
        mockEmployeeCollection.find(new Document("role", "Data Entry Operator")).forEach(table.getItems()::add);

        // Assert
        Assertions.assertEquals(2, table.getItems().size());
        Assertions.assertEquals("Charlie", table.getItems().get(0).getString("name"));
        Assertions.assertEquals("Dana", table.getItems().get(1).getString("name"));
    }

    @Test
    void testEditEmployee() {
        // Arrange
        Document mockEmployee = new Document("name", "Old Name").append("password", "oldpass").append("_id", 1);
        when(mockEmployeeCollection.replaceOne(any(Document.class), any(Document.class))).thenReturn(null);

        // Act
        showEditDialog(mockEmployee, "Cashier");

        // Simulate user actions in the dialog
        mockEmployee.put("name", "New Name");
        mockEmployee.put("password", "newpass");
        mockEmployeeCollection.replaceOne(new Document("_id", 1), mockEmployee);

        // Assert
        verify(mockEmployeeCollection, times(1)).replaceOne(eq(new Document("_id", 1)), eq(mockEmployee));
        Assertions.assertEquals("New Name", mockEmployee.getString("name"));
        Assertions.assertEquals("newpass", mockEmployee.getString("password"));
    }


    @Override
    public void start(Stage stage) {
        // Minimal UI setup for test execution
    }

    @AfterEach
    void tearDown() {
        mockEmployeeCollection = null;
    }
}
