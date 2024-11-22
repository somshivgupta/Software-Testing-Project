package models;

import java.util.*;

public class PurchaseOrder {
   private String id;
   private Supplier supplier;
   private Map<Product, Integer> items;  // Product -> quantity
   private String status;  // "PENDING", "APPROVED", "RECEIVED"
   private Date orderDate;
   private double totalAmount;

   public PurchaseOrder(String id, Supplier supplier) {
       this.id = id;
       this.supplier = supplier;
       this.items = new HashMap<>();
       this.status = "PENDING";
       this.orderDate = new Date();
       this.totalAmount = 0.0;
   }

   public void addItem(Product product, int quantity) {
       items.put(product, quantity);
       totalAmount += product.getPrice() * quantity;
   }

   // Getters
   public String getId() { return id; }
   public Supplier getSupplier() { return supplier; }
   public Map<Product, Integer> getItems() { return items; }
   public String getStatus() { return status; }
   public Date getOrderDate() { return orderDate; }
   public double getTotalAmount() { return totalAmount; }

   public void setStatus(String status) {
       this.status = status;
   }

   @Override
   public String toString() {
       return String.format("PurchaseOrder{id='%s', supplier='%s', status='%s', total=%.2f}",
           id, supplier.getName(), status, totalAmount);
   }
}