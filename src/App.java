import models.Product;
import models.OrderItem;
import models.Order;
import services.InventoryManager;
import services.OrderManager;
import services.ReportGenerator;

public class App {
   public static void main(String[] args) {
       InventoryManager manager = new InventoryManager();
       OrderManager orderManager = new OrderManager(manager);
       ReportGenerator reportGenerator = new ReportGenerator(manager, orderManager);
       // Create products
       Product laptop = new Product("P1", "Laptop", 999.99, 10, "Electronics");
       manager.addProduct(laptop);
       manager.addProduct(new Product("P2", "Mouse", 29.99, 50, "Electronics"));
       manager.addProduct(new Product("P3", "Keyboard", 59.99, 30, "Electronics"));

       // Test OrderItem
       System.out.println("Order Item Test:");
       OrderItem item = new OrderItem(laptop, 2);
       System.out.println("Product: " + item.getProduct().getName());
       System.out.println("Quantity: " + item.getQuantity());
       System.out.println("Price: " + item.getPriceAtTime());
       System.out.println("Subtotal: " + item.getSubtotal());

       // Test Order
       System.out.println("\nTesting Order:");
       Order order = new Order("ORD1");
       order.addItem(new OrderItem(laptop, 2));
       order.addItem(new OrderItem(new Product("P2", "Mouse", 29.99, 5, "Electronics"), 3));
       System.out.println(order);
       System.out.println("Total Amount: $" + order.getTotalAmount());

       // Test OrderManager
       System.out.println("\nTesting OrderManager:");
       Order newOrder = orderManager.createOrder("ORD2");
       newOrder.addItem(new OrderItem(laptop, 1));
       System.out.println("Before processing: " + newOrder);
       orderManager.processOrder("ORD2");
       System.out.println("After processing: " + newOrder);
       System.out.println("Updated laptop quantity: " + manager.getProduct("P1").get());

       
       // Test ReportGenerator
       System.out.println("\nTesting Report Generator:");
       System.out.println(reportGenerator.generateReport());
   }
}