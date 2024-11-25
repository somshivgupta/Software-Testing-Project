package com.inventory.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.inventory.services.OrderManager;
import com.inventory.services.InventoryManager;
import com.inventory.models.Order;
import com.inventory.models.OrderItem;
import com.inventory.models.Product;
import java.util.List;

public class OrderManagerTest {
    private OrderManager orderManager;
    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
        orderManager = new OrderManager(inventoryManager);
    }

    @Test
    void testOrderMapDefinitionAndUses() {
        // Tests orders map definition → uses
        Order order = orderManager.createOrder("ORD1");    // orders map use (put)
        assertEquals(order, orderManager.getOrder("ORD1")); // orders map use (get)
    }

    @Test
    void testCreateOrderDefinitionsAndUses() {
        // Tests orderId definition → uses
        String orderId = "ORD1";                          // orderId definition
        Order order = orderManager.createOrder(orderId);   // orderId use
        
        assertEquals(orderId, order.getOrderId());         // use in getting ID
        assertEquals("PENDING", order.getStatus());        // initial status definition use
    }

    @Test
    void testProcessOrderWithQuantities() {
        // Tests DU pairs in processing loop
        // Setup product and inventory
        Product product = new Product("1", "Test", 10.0, 10, "Test");
        inventoryManager.addProduct(product);
        
        // Create order with items
        Order order = orderManager.createOrder("ORD1");
        OrderItem item = new OrderItem(product, 2);        // quantity definition
        order.addItem(item);
        
        // Process order - tests loop DU pairs
        orderManager.processOrder("ORD1");                 // uses quantities in loop
        
        Order processedOrder = orderManager.getOrder("ORD1");
        assertEquals("COMPLETED", processedOrder.getStatus()); // status update use
    }

    @Test
    void testGetAllOrdersDefinitionsAndUses() {
        // Tests DU pairs in orders collection
        orderManager.createOrder("ORD1");                  // First order definition
        orderManager.createOrder("ORD2");                  // Second order definition
        
        List<Order> allOrders = orderManager.getAllOrders(); // Use of orders map
        assertEquals(2, allOrders.size());                   // Verify both definitions used
    }

    @Test
    void testQuantityAccumulationInProcessing() {
        // Tests DU pairs in quantity accumulation loop
        Product product = new Product("1", "Test", 10.0, 10, "Test");
        inventoryManager.addProduct(product);
        
        Order order = orderManager.createOrder("ORD1");
        // Multiple items for same product to test quantity accumulation
        order.addItem(new OrderItem(product, 2));         // First quantity definition
        order.addItem(new OrderItem(product, 3));         // Second quantity definition
        
        orderManager.processOrder("ORD1");                // Uses quantities in accumulation
        
        assertEquals("COMPLETED", order.getStatus());      // Verify processing completed
    }
}