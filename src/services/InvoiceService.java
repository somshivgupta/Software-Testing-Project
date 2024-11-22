package services;

import models.Invoice;
import models.Order;
import java.util.*;
import java.util.stream.*;

public class InvoiceService {
   private Map<String, Invoice> invoices;
   private OrderManager orderManager;

   public InvoiceService(OrderManager orderManager) {
       this.invoices = new HashMap<>();
       this.orderManager = orderManager;
   }

   public Invoice generateInvoice(String orderId) {
       Order order = orderManager.getOrder(orderId);
       if (order == null) {
           throw new IllegalArgumentException("Order not found");
       }
       
       String invoiceId = "INV" + (invoices.size() + 1);
       Invoice invoice = new Invoice(invoiceId, order);
       invoices.put(invoiceId, invoice);
       return invoice;
   }

   public void processPayment(String invoiceId) {
       Invoice invoice = invoices.get(invoiceId);
       if (invoice == null) {
           throw new IllegalArgumentException("Invoice not found");
       }
       invoice.setStatus("PAID");
   }

   public void applyDiscount(String invoiceId, double discountAmount) {
       Invoice invoice = invoices.get(invoiceId);
       if (invoice == null) {
           throw new IllegalArgumentException("Invoice not found");
       }
       invoice.applyDiscount(discountAmount);
   }

   public List<Invoice> getPendingInvoices() {
       return invoices.values().stream()
                     .filter(i -> i.getStatus().equals("PENDING"))
                     .collect(Collectors.toList());
   }
}