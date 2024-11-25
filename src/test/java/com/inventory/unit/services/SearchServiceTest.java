package com.inventory.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.inventory.services.SearchService;
import com.inventory.services.InventoryManager;
import com.inventory.models.Product;
import java.util.List;

public class SearchServiceTest {
    private SearchService searchService;
    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
        searchService = new SearchService(inventoryManager);
    }

    @Test
    void testSearchByNameDefinitionsAndUses() {
        // Setup products in inventory
        Product product1 = new Product("1", "TestProduct", 10.0, 5, "Test");
        Product product2 = new Product("2", "AnotherTest", 20.0, 5, "Test");
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);

        // Test keyword definition → use in stream filter
        String keyword = "Test";                              // keyword definition
        List<Product> results = searchService.searchByName(keyword);  // keyword use in loop
        
        assertEquals(2, results.size());  // verify loop processed all matches
    }

    @Test
    void testPriceRangeDefinitionsAndUses() {
        // Setup products with different prices
        Product product1 = new Product("1", "Test1", 10.0, 5, "Test");
        Product product2 = new Product("2", "Test2", 20.0, 5, "Test");
        Product product3 = new Product("3", "Test3", 30.0, 5, "Test");
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        inventoryManager.addProduct(product3);

        // Test price range definitions → uses in stream filter
        double minPrice = 15.0;                                          // minPrice definition
        double maxPrice = 25.0;                                         // maxPrice definition
        List<Product> results = searchService.filterByPriceRange(minPrice, maxPrice);  // use in loop
        
        assertEquals(1, results.size());
        assertEquals(20.0, results.get(0).getPrice());  // verify correct price filtered
    }

    @Test
    void testStockLevelDefinitionsAndUses() {
        // Setup products with different stock levels
        Product product1 = new Product("1", "Test1", 10.0, 3, "Test");
        Product product2 = new Product("2", "Test2", 20.0, 5, "Test");
        Product product3 = new Product("3", "Test3", 30.0, 7, "Test");
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        inventoryManager.addProduct(product3);

        // Test minStock definition → use in stream filter
        int minStock = 5;                                    // minStock definition
        List<Product> results = searchService.filterByStock(minStock);  // use in loop
        
        assertEquals(2, results.size());  // verify loop processed all matches
    }

    @Test
    void testMultipleProductsInSearchLoop() {
        // Test product definitions → uses in search loop
        // Add multiple products to test loop processing
        for(int i = 0; i < 5; i++) {
            Product product = new Product(
                String.valueOf(i),
                "SearchTest" + i,
                10.0 * i,
                5,
                "Test"
            );
            inventoryManager.addProduct(product);
        }

        // Test stream loop processing all products
        String keyword = "SearchTest";                            // keyword definition
        List<Product> results = searchService.searchByName(keyword);  // use in loop
        
        assertEquals(5, results.size());  // verify loop processed all products
    }
}