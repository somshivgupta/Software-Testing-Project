package com.inventory.integration;

import org.junit.jupiter.api.Test;
import com.inventory.models.Product;
import com.inventory.models.OrderItem;
import static org.junit.jupiter.api.Assertions.*;

public class ProductOrderItemTest {

    @Test
    void testProductDefinitionsFlowToOrderItem() {
        // Product definitions
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        
        // OrderItem using Product's definitions
        OrderItem item = new OrderItem(product, 2);
        
        // Test Product definitions flow to OrderItem uses
        Product usedProduct = item.getProduct();
        assertEquals("1", usedProduct.getId());          // Product id definition → use
        assertEquals("Test", usedProduct.getName());     // Product name definition → use
        assertEquals(10.0, usedProduct.getPrice());      // Product price definition → use
        assertEquals(5, usedProduct.getQuantity());      // Product quantity definition → use
    }

    @Test
    void testProductPriceDefinitionInSubtotal() {
        // Product price definition
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        
        // OrderItem using price definition
        OrderItem item = new OrderItem(product, 2);
        
        // Test Product price definition flows to subtotal calculation
        double subtotal = item.getSubtotal();
        assertEquals(20.0, subtotal);  // Product price (10.0) * quantity (2)
    }

    @Test
    void testProductQuantityWithOrderQuantityInteraction() {
        // Product quantity definition
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        
        // OrderItem using both its quantity and product's quantity
        OrderItem item = new OrderItem(product, 3);
        
        // Test both quantities' definitions flow to their uses
        assertEquals(5, item.getProduct().getQuantity());  // Product quantity definition → use
        assertEquals(3, item.getQuantity());               // OrderItem quantity definition → use
    }
}