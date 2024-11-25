package com.inventory.unit.models;

import org.junit.jupiter.api.Test;
import com.inventory.models.Product;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    void testConstructorDefinitionsAndUses() {
        // Tests DU pairs: constructor parameters → field assignments → getter uses
        Product product = new Product("1", "Test", 10.0, 5, "Category");
        
        // Test each definition reaches its use
        assertEquals("1", product.getId());
        assertEquals("Test", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(5, product.getQuantity());
        assertEquals("Category", product.getCategory());
    }

    @Test
    void testSetterDefinitionsAndUses() {
        // Tests DU pairs: setter parameters → field updates → getter uses
        Product product = new Product("1", "Test", 10.0, 5, "Category");
        
        // New definitions through setters
        product.setName("NewTest");
        product.setPrice(20.0);
        product.setQuantity(10);
        product.setCategory("NewCategory");
        
        // Test each new definition reaches its use
        assertEquals("NewTest", product.getName());
        assertEquals(20.0, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals("NewCategory", product.getCategory());
    }
}