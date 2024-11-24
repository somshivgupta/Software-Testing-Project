package com.inventory.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import com.inventory.services.InventoryManager;
import com.inventory.models.Product;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryPersistenceTest {
    private InventoryManager inventoryManager;
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
    }

    @Test
    void testBasicPersistence() {
        // Tests cross-module DU pairs: InventoryManager save → FileHandler load
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        inventoryManager.addProduct(product);
        inventoryManager.saveInventoryToFile();
        
        InventoryManager newManager = new InventoryManager();
        newManager.loadInventoryFromFile();
        
        assertTrue(newManager.getProduct("1").isPresent());
        assertEquals(5, newManager.getProduct("1").get().getQuantity());
    }

    @Test
    void testPersistenceWithUpdates() {
        // Tests cross-module DU pairs: updates across save/load cycles
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        inventoryManager.addProduct(product);
        inventoryManager.saveInventoryToFile();
        
        inventoryManager.updateQuantity("1", 3);
        inventoryManager.saveInventoryToFile();
        
        InventoryManager newManager = new InventoryManager();
        newManager.loadInventoryFromFile();
        assertEquals(8, newManager.getProduct("1").get().getQuantity());
    }

    @Test
    void testBulkOperations() {
        // Tests cross-module DU pairs: large data handling
        for (int i = 0; i < 100; i++) {
            inventoryManager.addProduct(
                new Product(
                    String.valueOf(i),
                    "Product" + i,
                    10.0 * i,
                    5,
                    "Test"
                )
            );
        }
        
        inventoryManager.saveInventoryToFile();
        InventoryManager newManager = new InventoryManager();
        newManager.loadInventoryFromFile();
        
        assertEquals(100, newManager.getAllProducts().size());
    }
}