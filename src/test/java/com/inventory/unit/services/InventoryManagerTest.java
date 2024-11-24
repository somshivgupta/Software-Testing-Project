package com.inventory.unit.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.inventory.services.InventoryManager;
import com.inventory.models.Product;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerTest {
    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
    }

    @Test
    void testAddProductDUPairs() {
        // Tests DU pairs: product definition → inventory.put() use
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        inventoryManager.addProduct(product);
        assertTrue(inventoryManager.getProduct("1").isPresent());
        
        // Test duplicate addition
        assertThrows(IllegalArgumentException.class, () -> inventoryManager.addProduct(product));
    }

    @Test
    void testUpdateQuantityDUPairs() {
        // Tests DU pairs: quantity definition → product.setQuantity() use
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        inventoryManager.addProduct(product);
        
        inventoryManager.updateQuantity("1", 3);
        assertEquals(8, inventoryManager.getProduct("1").get().getQuantity());
        
        // Test invalid update
        assertThrows(IllegalArgumentException.class, () -> 
            inventoryManager.updateQuantity("1", -10));
    }

    @Test
    void testGetLowStockProducts() {
        // Tests DU pairs: threshold definition → product.getQuantity() use
        Product product1 = new Product("1", "Test1", 10.0, 5, "Test");
        Product product2 = new Product("2", "Test2", 20.0, 2, "Test");
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        
        List<Product> lowStock = inventoryManager.getLowStockProducts(4);
        assertEquals(1, lowStock.size());
        assertTrue(lowStock.contains(product2));
    }

    @Test
    void testCalculateTotalValue() {
        // Tests DU pairs: total definition → product.getPrice() and product.getQuantity() uses
        inventoryManager.addProduct(new Product("1", "Test1", 10.0, 2, "Test"));
        inventoryManager.addProduct(new Product("2", "Test2", 20.0, 3, "Test"));
        
        assertEquals(80.0, inventoryManager.calculateTotalInventoryValue());
        
        // Test empty inventory
        inventoryManager = new InventoryManager();
        assertEquals(0.0, inventoryManager.calculateTotalInventoryValue());
    }

    @Test
    void testConcurrentModification() throws InterruptedException {
        // Tests DU pairs: concurrent inventory access
        Product product = new Product("1", "Test", 10.0, 10, "Test");
        inventoryManager.addProduct(product);
        
        int threadCount = 5;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    inventoryManager.updateQuantity("1", 1);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        assertEquals(15, inventoryManager.getProduct("1").get().getQuantity());
        executor.shutdown();
    }

    @Test
    void testInventoryClear() {
        // Tests DU pairs: inventory.clear() → subsequent operations
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        inventoryManager.addProduct(product);
        
        // Simulate clear operation (as would happen in loadInventoryFromFile)
        inventoryManager = new InventoryManager();
        assertTrue(inventoryManager.getAllProducts().isEmpty());
        assertEquals(0.0, inventoryManager.calculateTotalInventoryValue());
    }
}