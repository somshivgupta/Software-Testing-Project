import models.*;
import services.*;

public class App {
    public static void main(String[] args) {
        // Initialize all services
        InventoryManager manager = new InventoryManager();
        OrderManager orderManager = new OrderManager(manager);
        CategoryManager categoryManager = new CategoryManager();
        ReportGenerator reportGenerator = new ReportGenerator(manager, orderManager);
        SearchService searchService = new SearchService(manager);
        UserManager userManager = new UserManager();
        NotificationService notificationService = new NotificationService();
        InvoiceService invoiceService = new InvoiceService(orderManager);
        SupplierManager supplierManager = new SupplierManager();

        Category electronics = new Category("C1", "Electronics", "Electronic devices and accessories");
        categoryManager.addCategory(electronics);

        Product laptop = new Product("P1", "Laptop", 999.99, 10, "Electronics");
        Product mouse = new Product("P2", "Mouse", 29.99, 50, "Electronics");
        manager.addProduct(laptop);
        manager.addProduct(mouse);

        categoryManager.assignProductToCategory("C1", laptop);
        categoryManager.assignProductToCategory("C1", mouse);

        // Test Category Management
        System.out.println("Products in Electronics category:");
        categoryManager.getProductsByCategory("C1").forEach(System.out::println);

        System.out.println("\nAfter 10% price increase in category:");
        categoryManager.updateCategoryProducts("C1", 0.10);

        // Test Order functionality
        System.out.println("\nTesting Order Management:");
        Order order = orderManager.createOrder("ORD1"); // Create order through manager
        order.addItem(new OrderItem(laptop, 2));
        System.out.println("Order created: " + order);
        orderManager.processOrder("ORD1");
        System.out.println("Order after processing: " + orderManager.getOrder("ORD1"));

        // Test Search functionality
        System.out.println("\nTesting Search Service:");
        System.out.println("Products containing 'lap':");
        searchService.searchByName("lap").forEach(System.out::println);

        System.out.println("\nProducts between $20-$100:");
        searchService.filterByPriceRange(20, 100).forEach(System.out::println);

        System.out.println("\nProducts with stock >= 30:");
        searchService.filterByStock(30).forEach(System.out::println);

        // Test Authentication
        System.out.println("\nTesting Authentication:");
        System.out.println("Login attempt (admin): " + userManager.login("admin", "admin123"));
        System.out.println("Is admin: " + userManager.isAdmin());

        // Test Notifications
        System.out.println("\nTesting Notifications:");
        notificationService.createLowStockNotification(laptop);
        notificationService.createOrderNotification("ORD1", "COMPLETED");

        System.out.println("Unread notifications:");
        notificationService.getUnreadNotifications()
                .forEach(n -> System.out.println(n.getType() + ": " + n.getMessage()));

        // Generate Report
        System.out.println("\nFinal Report:");
        System.out.println(reportGenerator.generateReport());

        // Test Invoice
        System.out.println("\nTesting Invoice System:");
        Invoice invoice = invoiceService.generateInvoice("ORD1");
        System.out.println("Generated Invoice: " + invoice);

        invoiceService.applyDiscount("INV1", 100);
        System.out.println("After discount: " + invoice);

        invoiceService.processPayment("INV1");
        System.out.println("After payment: " + invoice);

        // Test Supplier
        System.out.println("\nTesting Supplier Management:");
        Supplier supplier = new Supplier("S1", "TechSupply Inc", "John Doe", "john@techsupply.com", "1234567890");
        supplierManager.addSupplier(supplier);

        supplierManager.assignProductToSupplier("S1", "P1");
        System.out.println("Supplier products: " + supplierManager.getSupplierProducts("S1"));

        System.out.println("\nUpdating supplier rating...");
        supplierManager.updateSupplierRating("S1", 4.5);
        System.out.println("Top suppliers: " + supplierManager.getTopSuppliers(1));
    }
}

// import models.*;
// import services.*;

// public class App {
// public static void main(String[] args) {
// InventoryManager manager = new InventoryManager();
// OrderManager orderManager = new OrderManager(manager);
// CategoryManager categoryManager = new CategoryManager();
// ReportGenerator reportGenerator = new ReportGenerator(manager, orderManager);
// SearchService searchService = new SearchService(manager);
// UserManager userManager = new UserManager();
// NotificationService notificationService = new NotificationService();

// // Create and test category
// Category electronics = new Category("C1", "Electronics", "Electronic devices
// and accessories");
// categoryManager.addCategory(electronics);

// // Create products and assign to category
// Product laptop = new Product("P1", "Laptop", 999.99, 10, "Electronics");
// Product mouse = new Product("P2", "Mouse", 29.99, 50, "Electronics");
// manager.addProduct(laptop);
// manager.addProduct(mouse);

// categoryManager.assignProductToCategory("C1", laptop);
// categoryManager.assignProductToCategory("C1", mouse);

// System.out.println("Products in Electronics category:");
// categoryManager.getProductsByCategory("C1").forEach(System.out::println);

// // Test category price update
// System.out.println("\nAfter 10% price increase in category:");
// categoryManager.updateCategoryProducts("C1", 0.10);
// categoryManager.getProductsByCategory("C1").forEach(System.out::println);

// // Test SearchService
// System.out.println("\nTesting Search Service:");
// System.out.println("Products containing 'lap':");
// searchService.searchByName("lap").forEach(System.out::println);

// System.out.println("\nProducts between $20-$100:");
// searchService.filterByPriceRange(20, 100).forEach(System.out::println);

// System.out.println("\nProducts with stock >= 30:");
// searchService.filterByStock(30).forEach(System.out::println);

// System.out.println("\nTesting User Authentication:");
// System.out.println("Login attempt (admin): " + userManager.login("admin",
// "admin123"));
// System.out.println("Is admin: " + userManager.isAdmin());

// // Test regular user
// userManager.addUser(new User("john", "pass123", "USER"));
// userManager.logout();
// System.out.println("\nLogin attempt (john): " + userManager.login("john",
// "pass123"));
// System.out.println("Is admin: " + userManager.isAdmin());

// // Test admin operations
// System.out.println("\nTesting Admin Operations:");
// userManager.login("admin", "admin123");
// System.out.println("Current user: " + userManager.getCurrentUser());

// if (userManager.isAdmin()) {
// // Admin operations
// Product newProduct = new Product("P4", "Monitor", 299.99, 15, "Electronics");
// manager.addProduct(newProduct);
// categoryManager.assignProductToCategory("C1", newProduct);
// System.out.println("Added new product as admin");
// System.out.println(manager.getProduct("P4").get());
// }

// // Test notifications
// System.out.println("\nTesting Notifications:");
// notificationService.createLowStockNotification(laptop);
// notificationService.createOrderNotification("ORD1", "COMPLETED");

// System.out.println("Unread notifications:");
// notificationService.getUnreadNotifications().forEach(n ->
// System.out.println(n.getType() + ": " + n.getMessage()));
// }
// }

// import models.Product;
// import models.OrderItem;
// import models.Order;
// import services.InventoryManager;
// import services.OrderManager;
// import services.ReportGenerator;

// public class App {
// public static void main(String[] args) {
// InventoryManager manager = new InventoryManager();
// OrderManager orderManager = new OrderManager(manager);
// ReportGenerator reportGenerator = new ReportGenerator(manager, orderManager);
// // Create products
// Product laptop = new Product("P1", "Laptop", 999.99, 10, "Electronics");
// manager.addProduct(laptop);
// manager.addProduct(new Product("P2", "Mouse", 29.99, 50, "Electronics"));
// manager.addProduct(new Product("P3", "Keyboard", 59.99, 30, "Electronics"));

// // Test OrderItem
// System.out.println("Order Item Test:");
// OrderItem item = new OrderItem(laptop, 2);
// System.out.println("Product: " + item.getProduct().getName());
// System.out.println("Quantity: " + item.getQuantity());
// System.out.println("Price: " + item.getPriceAtTime());
// System.out.println("Subtotal: " + item.getSubtotal());

// // Test Order
// System.out.println("\nTesting Order:");
// Order order = new Order("ORD1");
// order.addItem(new OrderItem(laptop, 2));
// order.addItem(new OrderItem(new Product("P2", "Mouse", 29.99, 5,
// "Electronics"), 3));
// System.out.println(order);
// System.out.println("Total Amount: $" + order.getTotalAmount());

// // Test OrderManager
// System.out.println("\nTesting OrderManager:");
// Order newOrder = orderManager.createOrder("ORD2");
// newOrder.addItem(new OrderItem(laptop, 1));
// System.out.println("Before processing: " + newOrder);
// orderManager.processOrder("ORD2");
// System.out.println("After processing: " + newOrder);
// System.out.println("Updated laptop quantity: " +
// manager.getProduct("P1").get());

// // Test ReportGenerator
// System.out.println("\nTesting Report Generator:");
// System.out.println(reportGenerator.generateReport());
// }
// }