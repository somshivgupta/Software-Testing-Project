package com.inventory.unit.models;

import org.junit.jupiter.api.Test;
import com.inventory.models.Order;
import com.inventory.models.OrderItem;
import com.inventory.models.Product;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    
    @Test
    void testConstructorDefinitionsAndUses() {
        // Tests DU pairs: constructor param -> field assignments -> getter uses
        Order order = new Order("ORD1");
        
        // Test each definition reaches its use
        assertEquals("ORD1", order.getOrderId());
        assertTrue(order.getItems().isEmpty());  // new ArrayList definition
        assertEquals("PENDING", order.getStatus());  // initial status definition
        assertNotNull(order.getOrderDate());  // Date definition
    }

    @Test
    void testItemsListDefinitionAndUses() {
        // Tests DU pairs: items list definition -> addItem use -> getItems use
        Order order = new Order("ORD1");
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        OrderItem item = new OrderItem(product, 2);
        
        // Definition in addItem and use in getItems
        order.addItem(item);
        assertEquals(1, order.getItems().size());
        assertTrue(order.getItems().contains(item));
    }

    @Test
    void testGetTotalAmountWithLoop() {
        // Tests DU pairs in loop: items list definition -> loop through items -> sum calculation
        Order order = new Order("ORD1");
        
        // Add multiple items to test the loop
        Product product1 = new Product("1", "Test1", 10.0, 5, "Test");
        Product product2 = new Product("2", "Test2", 20.0, 5, "Test");
        
        OrderItem item1 = new OrderItem(product1, 2);  // 2 * 10 = 20
        OrderItem item2 = new OrderItem(product2, 3);  // 3 * 20 = 60
        
        order.addItem(item1);
        order.addItem(item2);
        
        // Tests loop through items and sum calculation
        assertEquals(80.0, order.getTotalAmount());
    }

    @Test
    void testStatusDefinitionAndUses() {
        // Tests DU pairs: status field definition -> setter -> getter
        Order order = new Order("ORD1");
        
        // New status definition through setter
        order.setStatus("COMPLETED");
        assertEquals("COMPLETED", order.getStatus());
    }
}