package com.inventory.services;

import java.util.*;

import com.inventory.models.Order;
import com.inventory.models.OrderItem;
import com.inventory.models.Product;

public class OrderManager {
    private Map<String, Order> orders;
    private InventoryManager inventoryManager;

    public OrderManager(InventoryManager inventoryManager) {
        this.orders = new HashMap<>();
        this.inventoryManager = inventoryManager;
    }

    public Order createOrder(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        if (orders.containsKey(orderId)) {
            throw new IllegalArgumentException("Order ID already exists");
        }
        Order order = new Order(orderId);
        orders.put(orderId, order);
        return order;
    }

    public void processOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cannot process empty order");
        }
        Map<String, Integer> totalOrderedQuantities = new HashMap<>();

        for (OrderItem item : order.getItems()) {
            String productId = item.getProduct().getId();
            totalOrderedQuantities.merge(productId, item.getQuantity(), Integer::sum);

            Optional<Product> product = inventoryManager.getProduct(productId);
            if (product.isPresent() && totalOrderedQuantities.get(productId) > product.get().getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient quantity for " + item.getProduct().getName() +
                                ". Available: " + product.get().getQuantity() +
                                ", Ordered: " + totalOrderedQuantities.get(productId));
            }
        }

        for (OrderItem item : order.getItems()) {
            inventoryManager.updateQuantity(
                    item.getProduct().getId(),
                    -item.getQuantity());
        }
        order.setStatus("COMPLETED");
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }
}