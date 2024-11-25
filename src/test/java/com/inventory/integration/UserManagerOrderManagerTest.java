package com.inventory.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.inventory.services.UserManager;
import com.inventory.services.OrderManager;
import com.inventory.services.InventoryManager;
import com.inventory.models.User;
import com.inventory.models.Order;
import com.inventory.models.Product;
import com.inventory.models.OrderItem;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerOrderManagerTest {
    private UserManager userManager;
    private OrderManager orderManager;
    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
        orderManager = new OrderManager(inventoryManager);
        userManager = new UserManager();
    }

    @Test
    void testUserLoginFlowToOrderCreation() {
        // Test user definition flowing to order creation
        User user = new User("testUser", "pass", "USER");
        userManager.addUser(user);
        userManager.login("testUser", "pass");  // currentUser definition
        
        // Use currentUser definition in order flow
        Order order = orderManager.createOrder("ORD1");
        assertNotNull(order);
    }

    @Test
    void testAdminUserFlowToOrderProcessing() {
        // Test admin definition flowing to order processing
        userManager.login("admin", "admin123");  // admin user definition
        
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        inventoryManager.addProduct(product);
        
        // Use admin definition in order flow
        Order order = orderManager.createOrder("ORD1");
        order.addItem(new OrderItem(product, 2));
        orderManager.processOrder("ORD1");
        
        assertEquals("COMPLETED", order.getStatus());
    }

    @Test
    void testMultipleUserOrdersFlow() {
        // Test multiple user definitions flowing to orders
        userManager.login("admin", "admin123");  // admin definition
        
        // Create multiple orders using admin definition
        Order order1 = orderManager.createOrder("ORD1");
        Order order2 = orderManager.createOrder("ORD2");
        
        assertNotNull(order1);
        assertNotNull(order2);
    }
}
