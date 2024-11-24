package com.inventory;
import java.util.Scanner;

import com.inventory.models.*;
import com.inventory.services.InventoryManager;
import com.inventory.services.OrderManager;
import com.inventory.services.SearchService;
import com.inventory.services.UserManager;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager;
    private static InventoryManager inventoryManager;
    private static OrderManager orderManager;
    private static SearchService searchService;

    public static void main(String[] args) {
        initialize();
        mainLoop();
    }

    private static void initialize() {
        userManager = new UserManager();
        inventoryManager = new InventoryManager();
        orderManager = new OrderManager(inventoryManager);
        searchService = new SearchService(inventoryManager);

        // Add some sample products
        addSampleProducts();
    }

    private static void addSampleProducts() {
        try {
            // Load inventory from file
            inventoryManager.loadInventoryFromFile();
            System.out.println("Inventory loaded successfully from file.");
        } catch (RuntimeException e) {
            System.out.println("Warning: Could not load inventory from file. Starting with empty inventory.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mainLoop() {
        boolean running = true;
        while (running) {
            if (userManager.getCurrentUser() == null) {
                running = showLoginMenu();
            } else {
                if (userManager.isAdmin()) {
                    showAdminMenu();
                } else {
                    showUserMenu();
                }
            }
        }
        System.out.println("Thank you for using the Inventory Management System!");
    }

    private static boolean showLoginMenu() {
        System.out.println("\n=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register New User");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                handleLogin();
                return true;
            case 2:
                handleRegistration();
                return true;
            case 3:
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
                return true;
        }
    }

    private static void handleRegistration() {
        System.out.println("\n=== User Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            // Create new user with "USER" role
            User newUser = new User(username, password, "USER");
            userManager.addUser(newUser);
            System.out.println("Registration successful! Please login with your credentials.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    private static void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userManager.login(username, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void showUserMenu() {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Products");
            System.out.println("2. Search Products");
            System.out.println("3. Create Order");
            System.out.println("4. View Orders");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                    createOrder();
                    break;
                case 4:
                    viewOrders();
                    break;
                case 5:
                    userManager.logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View Products");
            System.out.println("2. Search Products");
            System.out.println("3. Create Order");
            System.out.println("4. View Orders");
            System.out.println("5. Add Product");
            System.out.println("6. Update Product Quantity");
            System.out.println("7. View Low Stock Products");
            System.out.println("8. View Total Inventory Value");
            System.out.println("9. Save Inventory to File");
            System.out.println("10. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                    createOrder();
                    break;
                case 4:
                    viewOrders();
                    break;
                case 5:
                    addProduct();
                    break;
                case 6:
                    updateProductQuantity();
                    break;
                case 7:
                    viewLowStockProducts();
                    break;
                case 8:
                    viewTotalInventoryValue();
                    break;
                case 9:
                    saveInventory();
                    break;
                case 10:
                    userManager.logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Implementation of menu options
    private static void viewProducts() {
        System.out.println("\n=== Product List ===");
        List<Product> products = inventoryManager.getAllProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void searchProducts() {
        System.out.println("\n=== Search Products ===");
        System.out.println("1. Search by name");
        System.out.println("2. Filter by price range");
        System.out.println("3. Filter by stock level");
        System.out.print("Choose search type: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        List<Product> results = null;
        switch (choice) {
            case 1:
                System.out.print("Enter search keyword: ");
                String keyword = scanner.nextLine();
                results = searchService.searchByName(keyword);
                break;
            case 2:
                System.out.print("Enter minimum price: ");
                double minPrice = getValidDoubleInput(scanner, "Enter minimum price: ");
                System.out.print("Enter maximum price: ");
                double maxPrice = getValidDoubleInput(scanner, "Enter maximum price: ");
                results = searchService.filterByPriceRange(minPrice, maxPrice);
                break;
            case 3:
                System.out.print("Enter minimum stock level: ");
                int minStock = scanner.nextInt();
                results = searchService.filterByStock(minStock);
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        if (results != null && !results.isEmpty()) {
            System.out.println("\nSearch Results:");
            for (Product product : results) {
                System.out.println(product);
            }
        } else {
            System.out.println("No products found.");
        }
    }

    private static void createOrder() {
        System.out.println("\n=== Create Order ===");
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();

        Order order = orderManager.createOrder(orderId);
        boolean addingItems = true;
        // Map to track quantities ordered for each product
        Map<String, Integer> orderedQuantities = new HashMap<>();

        while (addingItems) {
            viewProducts();
            System.out.print("\nEnter product ID (or 'done' to finish): ");
            String productId = scanner.nextLine();

            if (productId.equalsIgnoreCase("done")) {
                addingItems = false;
                continue;
            }

            Product product = inventoryManager.getProduct(productId).orElse(null);
            if (product == null) {
                System.out.println("Product not found.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            // Check if we've already ordered this product
            int currentOrdered = orderedQuantities.getOrDefault(productId, 0);

            // Validate total quantity against available stock
            if (currentOrdered + quantity > product.getQuantity()) {
                System.out.println("Error: Cannot order " + quantity + " more units. Only " +
                        (product.getQuantity() - currentOrdered) + " units available.");
                continue;
            }

            try {
                OrderItem item = new OrderItem(product, quantity);
                order.addItem(item);
                // Update ordered quantities
                orderedQuantities.put(productId, currentOrdered + quantity);
                System.out.println("Item added to order.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        try {
            orderManager.processOrder(orderId);
            System.out.println("Order processed successfully!");
            System.out.println("Total amount: $" + order.getTotalAmount());
        } catch (IllegalArgumentException e) {
            System.out.println("Error processing order: " + e.getMessage());
        }
    }

    private static void viewOrders() {
        System.out.println("\n=== Order List ===");
        List<Order> orders = orderManager.getAllOrders();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private static void addProduct() {
        System.out.println("\n=== Add Product ===");
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = getValidDoubleInput(scanner, "Enter product price: ");
        System.out.print("Enter initial quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter product category: ");
        String category = scanner.nextLine();

        try {
            Product newProduct = new Product(id, name, price, quantity, category);
            inventoryManager.addProduct(newProduct);
            inventoryManager.saveInventoryToFile(); // Save changes to file
            System.out.println("Product added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    private static void updateProductQuantity() {
        System.out.println("\n=== Update Product Quantity ===");
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter quantity change (positive for increase, negative for decrease): ");
        int change = scanner.nextInt();
        scanner.nextLine(); 

        try {
            inventoryManager.updateQuantity(id, change);
            inventoryManager.saveInventoryToFile(); // Save changes to file
            System.out.println("Quantity updated successfully!");

            // Show updated quantity
            Product product = inventoryManager.getProduct(id).orElse(null);
            if (product != null) {
                System.out.println("New quantity for " + product.getName() + ": " + product.getQuantity());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating quantity: " + e.getMessage());
        }
    }

    private static void viewLowStockProducts() {
        System.out.println("\n=== Low Stock Products ===");
        System.out.print("Enter threshold quantity: ");
        int threshold = scanner.nextInt();
        scanner.nextLine(); 

        List<Product> lowStock = inventoryManager.getLowStockProducts(threshold);
        if (lowStock.isEmpty()) {
            System.out.println("No products below threshold.");
        } else {
            System.out.println("Products below threshold " + threshold + ":");
            for (Product product : lowStock) {
                System.out.println(product);
            }
        }
    }

    private static void viewTotalInventoryValue() {
        System.out.println("\n=== Total Inventory Value ===");
        double totalValue = inventoryManager.calculateTotalInventoryValue();
        System.out.printf("Total value: $%.2f%n", totalValue);
    }

    private static void saveInventory() {
        System.out.println("\n=== Saving Inventory ===");
        try {
            inventoryManager.saveInventoryToFile();
            System.out.println("Inventory saved successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    private static double getValidDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}