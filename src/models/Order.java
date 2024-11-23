package models;

import java.util.*;

public class Order {
    private String orderId;
    private List<OrderItem> items;
    private String status;
    private Date orderDate;
    private static final Set<String> VALID_STATUSES = Set.of("PENDING", "COMPLETED");

    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.status = "PENDING";
        this.orderDate = new Date();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getTotalAmount() {
        return items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    // Setters
    public void setStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid order status");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", items=" + items +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                ", total=" + getTotalAmount() +
                '}';
    }
}