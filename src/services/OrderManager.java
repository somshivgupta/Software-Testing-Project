package services;

import models.Order;
import java.util.*;
import models.OrderItem;

public class OrderManager {
    private Map<String, Order> orders;
    private InventoryManager inventoryManager;

    public OrderManager(InventoryManager inventoryManager) {
        this.orders = new HashMap<>();
        this.inventoryManager = inventoryManager;
    }

    public Order createOrder(String orderId) {
        Order order = new Order(orderId);
        orders.put(orderId, order);
        return order;
    }

    public void processOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        for (OrderItem item : order.getItems()) {
            if (item.getQuantity() > inventoryManager.getProduct(item.getProduct().getId()).get().getQuantity()) {
                throw new IllegalArgumentException("Insufficient quantity for " + item.getProduct().getName());
            }
        }

        // Check and update inventory
        try {
            for (OrderItem item : order.getItems()) {
                inventoryManager.updateQuantity(
                        item.getProduct().getId(),
                        -item.getQuantity());
            }
            order.setStatus("COMPLETED");
        } catch (Exception e) {
            order.setStatus("CANCELLED");
            throw new RuntimeException("Failed to process order: " + e.getMessage());
        }
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }
}