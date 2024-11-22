package models;

import java.util.*;

public class Order {
   private String orderId;
   private List<OrderItem> items;
   private String status;  // "PENDING", "COMPLETED", "CANCELLED"
   private Date orderDate;

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
   public String getOrderId() { return orderId; }
   public List<OrderItem> getItems() { return items; }
   public String getStatus() { return status; }
   public Date getOrderDate() { return orderDate; }

   public void setStatus(String status) {
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