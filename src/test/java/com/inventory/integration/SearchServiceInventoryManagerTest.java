package com.inventory.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.inventory.services.SearchService;
import com.inventory.services.InventoryManager;
import com.inventory.models.Product;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SearchServiceInventoryManagerTest {
    private SearchService searchService;
    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
        searchService = new SearchService(inventoryManager);
    }

    @Test
    void testInventoryProductsFlowToSearch() {
        // Test product definitions in inventory → uses in search
        Product product1 = new Product("1", "TestProduct", 10.0, 5, "Test");
        Product product2 = new Product("2", "TestSearch", 20.0, 5, "Test");
        
        // Definitions in inventory
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        
        // Uses in search loop
        List<Product> results = searchService.searchByName("Test");
        assertEquals(2, results.size());
    }

    @Test
    void testProductPricesFlowToFilter() {
        // Test price definitions in inventory → uses in price filter loop
        // Define products with different prices
        for(int i = 1; i <= 5; i++) {
            Product product = new Product(
                String.valueOf(i),
                "Test" + i,
                i * 10.0,  // prices: 10, 20, 30, 40, 50
                5,
                "Test"
            );
            inventoryManager.addProduct(product);  // price definitions in inventory
        }
        
        // Use price definitions in filter
        List<Product> results = searchService.filterByPriceRange(15.0, 35.0);  // uses prices in loop
        assertEquals(2, results.size());  // should find products with prices 20 and 30
    }

    @Test
    void testQuantitiesFlowToStockFilter() {
        // Test quantity definitions in inventory → uses in stock filter loop
        Product product1 = new Product("1", "Test1", 10.0, 3, "Test");  // quantity def
        Product product2 = new Product("2", "Test2", 20.0, 5, "Test");  // quantity def
        Product product3 = new Product("3", "Test3", 30.0, 7, "Test");  // quantity def
        
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        inventoryManager.addProduct(product3);
        
        // Use quantities in filter loop
        List<Product> results = searchService.filterByStock(5);
        assertEquals(2, results.size());  // should find products with quantity >= 5
    }
}