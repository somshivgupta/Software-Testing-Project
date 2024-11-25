package com.inventory.unit.models;

import org.junit.jupiter.api.Test;
import com.inventory.models.OrderItem;
import com.inventory.models.Product;
import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {
    
    @Test
    void testConstructorDefinitionsAndUses() {
        // Tests DU pairs: constructor params → field assignments → getter uses
        Product product = new Product("1", "Test", 10.0, 5, "Test"); // product def
        OrderItem item = new OrderItem(product, 2);                   // quantity def, product use
        
        // Test each definition reaches its use
        assertEquals(product, item.getProduct());     // product field use
        assertEquals(2, item.getQuantity());         // quantity field use
    }

    @Test
    void testProductPropertiesDUPairs() {
        // Tests DU pairs for Product's properties used in OrderItem
        Product product = new Product("1", "Test", 10.0, 5, "Test"); // price def, quantity def
        OrderItem item = new OrderItem(product, 3);
        
        // Test product.price definition use in getSubtotal
        double itemTotal = item.getSubtotal();                       // uses product.getPrice()
        assertEquals(30.0, itemTotal);                               // 10.0 * 3
        
        // Test product.quantity definition is accessed
        Product usedProduct = item.getProduct();                     // product field use
        assertEquals(5, usedProduct.getQuantity());                  // product quantity use
    }

    @Test
    void testQuantityDUPairs() {
        // Tests all uses of quantity field
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        int orderQuantity = 2;                                       // quantity def
        OrderItem item = new OrderItem(product, orderQuantity);      // quantity use in constructor
        
        // Test quantity in direct getter
        assertEquals(orderQuantity, item.getQuantity());             // quantity field use
        
        // Test quantity in subtotal calculation
        assertEquals(20.0, item.getSubtotal());                      // quantity use in calculation
    }

    @Test
    void testProductStockQuantityDUPair() {
        // Tests DU pair of product.quantity in stock validation
        Product product = new Product("1", "Test", 10.0, 5, "Test"); // available quantity def
        OrderItem item = new OrderItem(product, 4);                   // uses product.getQuantity for validation
        
        // Verify the product's quantity was accessed for validation
        Product usedProduct = item.getProduct();
        assertEquals(5, usedProduct.getQuantity());                   // product quantity use
        assertEquals(4, item.getQuantity());                         // order quantity use
    }
}