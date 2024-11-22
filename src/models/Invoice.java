package models;

import java.util.Date;

public class Invoice {
   private String invoiceId;
   private Order order;
   private double subtotal;
   private double taxRate = 0.1; // 10% tax
   private double discount;
   private double total;
   private String status; // "PENDING", "PAID", "CANCELLED"
   private Date invoiceDate;

   public Invoice(String invoiceId, Order order) {
       this.invoiceId = invoiceId;
       this.order = order;
       this.subtotal = order.getTotalAmount();
       this.discount = 0;
       calculateTotal();
       this.status = "PENDING";
       this.invoiceDate = new Date();
   }

   private void calculateTotal() {
       double afterDiscount = subtotal - discount;
       double tax = afterDiscount * taxRate;
       this.total = afterDiscount + tax;
   }

   public void applyDiscount(double discountAmount) {
       this.discount = discountAmount;
       calculateTotal();
   }

   // Getters
   public String getInvoiceId() { return invoiceId; }
   public Order getOrder() { return order; }
   public double getSubtotal() { return subtotal; }
   public double getDiscount() { return discount; }
   public double getTotal() { return total; }
   public String getStatus() { return status; }
   public Date getInvoiceDate() { return invoiceDate; }

   public void setStatus(String status) {
       this.status = status;
   }

   @Override
   public String toString() {
       return String.format("Invoice{id='%s', subtotal=%.2f, discount=%.2f, tax=%.2f, total=%.2f, status='%s'}", 
           invoiceId, subtotal, discount, subtotal * taxRate, total, status);
   }
}