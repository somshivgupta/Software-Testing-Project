package com.inventory.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.inventory.services.OrderManager;
import com.inventory.services.InventoryManager;
import com.inventory.models.Order;
import com.inventory.models.OrderItem;
import com.inventory.models.Product;
import static org.junit.jupiter.api.Assertions.*;

public class OrderManagerInventoryManagerTest {
    private OrderManager orderManager;
    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
        orderManager = new OrderManager(inventoryManager);
    }

    @Test
    void testInventoryQuantityFlowToOrderProcessing() {
        // Tests product quantity definition in inventory → use in order processing
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        inventoryManager.addProduct(product);  // quantity definition in inventory
        
        Order order = orderManager.createOrder("ORD1");
        order.addItem(new OrderItem(product, 2));  // use of quantity in OrderItem
        
        orderManager.processOrder("ORD1");  // use of quantity in processing
        
        assertEquals(3, inventoryManager.getProduct("1").get().getQuantity());  // verify quantity update
    }

    @Test
    void testMultipleItemsQuantityFlow() {
        // Tests quantity definitions and uses in loop processing
        Product product = new Product("1", "Test", 10.0, 10, "Test");
        inventoryManager.addProduct(product);  // quantity definition
        
        Order order = orderManager.createOrder("ORD1");
        // Multiple items to test loop processing
        order.addItem(new OrderItem(product, 2));  // first quantity definition
        order.addItem(new OrderItem(product, 3));  // second quantity definition
        
        orderManager.processOrder("ORD1");  // uses both quantities in loop
        
        // Verify final quantity after loop processing
        assertEquals(5, inventoryManager.getProduct("1").get().getQuantity());
    }

    @Test
    void testProductAvailabilityFlow() {
        // Tests product existence definition → use in order processing
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        inventoryManager.addProduct(product);  // product definition in inventory
        
        Order order = orderManager.createOrder("ORD1");
        order.addItem(new OrderItem(product, 2));  // use of product in order
        
        orderManager.processOrder("ORD1");  // use of product in processing
        assertTrue(order.getStatus().equals("COMPLETED"));  // verify order processed
    }

    @Test
    void testMultipleProductsProcessingLoop() {
        // Tests multiple product definitions and uses in processing loop
        Product product1 = new Product("1", "Test1", 10.0, 5, "Test");
        Product product2 = new Product("2", "Test2", 20.0, 5, "Test");
        
        // Multiple product definitions in inventory
        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        
        Order order = orderManager.createOrder("ORD1");
        // Multiple items to test loop processing
        order.addItem(new OrderItem(product1, 2));
        order.addItem(new OrderItem(product2, 3));
        
        // Process order - uses all products and quantities in loop
        orderManager.processOrder("ORD1");
        
        // Verify quantities after loop processing
        assertEquals(3, inventoryManager.getProduct("1").get().getQuantity());
        assertEquals(2, inventoryManager.getProduct("2").get().getQuantity());
    }
}
