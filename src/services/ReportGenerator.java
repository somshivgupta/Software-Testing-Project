package services;

import models.Order;
import models.Product;
import java.util.*;
import java.text.SimpleDateFormat;
import models.OrderItem;

public class ReportGenerator {
   private InventoryManager inventoryManager;
   private OrderManager orderManager;
   private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

   public ReportGenerator(InventoryManager inventoryManager, OrderManager orderManager) {
       this.inventoryManager = inventoryManager;
       this.orderManager = orderManager;
   }

   public Map<String, Double> generateSalesReport() {
       Map<String, Double> salesByProduct = new HashMap<>();
       for (Order order : orderManager.getAllOrders()) {
           if (order.getStatus().equals("COMPLETED")) {
               for (OrderItem item : order.getItems()) {
                   String productId = item.getProduct().getId();
                   double sales = item.getSubtotal();
                   salesByProduct.merge(productId, sales, Double::sum);
               }
           }
       }
       return salesByProduct;
   }

   public List<Product> generateLowStockReport(int threshold) {
       return inventoryManager.getLowStockProducts(threshold);
   }

   public Map<String, Integer> generateInventoryReport() {
       Map<String, Integer> inventory = new HashMap<>();
       for (Product product : inventoryManager.getAllProducts()) {
           inventory.put(product.getName(), product.getQuantity());
       }
       return inventory;
   }

   public String generateReport() {
       StringBuilder report = new StringBuilder();
       report.append("=== Inventory Management System Report ===\n");
       report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n\n");

       report.append("1. Sales Report:\n");
       Map<String, Double> sales = generateSalesReport();
       sales.forEach((id, amount) -> 
           report.append(String.format("   %s: $%.2f\n", id, amount)));

       report.append("\n2. Inventory Status:\n");
       Map<String, Integer> inventory = generateInventoryReport();
       inventory.forEach((name, qty) -> 
           report.append(String.format("   %s: %d units\n", name, qty)));

       report.append("\n3. Low Stock Alert (Below 5 units):\n");
       List<Product> lowStock = generateLowStockReport(5);
       lowStock.forEach(product -> 
           report.append(String.format("   %s: %d units\n", product.getName(), product.getQuantity())));

       return report.toString();
   }
}