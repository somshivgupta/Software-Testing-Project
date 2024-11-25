package com.inventory.integration;

import org.junit.jupiter.api.Test;
import com.inventory.models.Order;
import com.inventory.models.OrderItem;
import com.inventory.models.Product;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class OrderItemOrderTest {

    @Test
    void testOrderItemDefinitionsFlowToOrder() {
        // Setup OrderItem definitions
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        OrderItem item = new OrderItem(product, 2);
        
        // Order using OrderItem definitions
        Order order = new Order("ORD1");
        order.addItem(item);
        
        // Test OrderItem definitions flow to Order uses
        List<OrderItem> items = order.getItems();
        assertEquals(1, items.size());
        assertEquals(item, items.get(0));    // OrderItem definition → use in list
        assertEquals(2, items.get(0).getQuantity());  // quantity definition → use
    }

    @Test
    void testOrderItemSubtotalInOrderTotal() {
        // OrderItem definitions with different prices and quantities
        Product product1 = new Product("1", "Test1", 10.0, 5, "Test");
        Product product2 = new Product("2", "Test2", 20.0, 5, "Test");
        OrderItem item1 = new OrderItem(product1, 2);  // subtotal: 20.0
        OrderItem item2 = new OrderItem(product2, 3);  // subtotal: 60.0
        
        // Order using OrderItems in getTotalAmount loop
        Order order = new Order("ORD1");
        order.addItem(item1);
        order.addItem(item2);
        
        // Test OrderItem subtotals flow through loop to total
        assertEquals(80.0, order.getTotalAmount());  // Loop using OrderItem's getSubtotal()
    }

    @Test
    void testMultipleOrderItemsInLoop() {
        // Multiple OrderItem definitions to test loop processing
        Order order = new Order("ORD1");
        for(int i = 0; i < 3; i++) {
            Product product = new Product(
                String.valueOf(i), 
                "Test" + i, 
                10.0 * (i + 1), 
                5, 
                "Test"
            );
            OrderItem item = new OrderItem(product, 2);  // quantities all 2
            order.addItem(item);
        }
        
        // Test all OrderItems' subtotals are used in loop
        // (10*2) + (20*2) + (30*2) = 20 + 40 + 60 = 120
        assertEquals(120.0, order.getTotalAmount());
    }
}