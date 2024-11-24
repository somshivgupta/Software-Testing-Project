package com.inventory.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.inventory.models.Product;

public class FileHandler {
    private final String filePath;

    public FileHandler() {
        this("src/main/java/com/inventory/resources/inventory.txt"); // Default path
    }

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public List<Product> loadInventory() {
        List<Product> products = new ArrayList<>();
        File file = new File(filePath);

        // Return empty list if file doesn't exist
        if (!file.exists()) {
            return products;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 5) {
                        System.err.println("Warning: Invalid data format at line " + lineNumber + ". Skipping line.");
                        continue;
                    }
                    
                    // Parse all parts with strict validation
                    String id = parts[0].trim();
                    if (id.isEmpty()) {
                        System.err.println("Warning: Empty ID at line " + lineNumber + ". Skipping line.");
                        continue;
                    }
                    
                    String name = parts[1].trim();
                    double price;
                    int quantity;
                    
                    try {
                        price = Double.parseDouble(parts[2].trim());
                        if (price < 0) {
                            System.err.println("Warning: Negative price at line " + lineNumber + ". Skipping line.");
                            continue;
                        }
                        
                        quantity = Integer.parseInt(parts[3].trim());
                        if (quantity < 0) {
                            System.err.println("Warning: Negative quantity at line " + lineNumber + ". Skipping line.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Invalid number format at line " + lineNumber + ". Skipping line.");
                        continue;
                    }
                    
                    String category = parts[4].trim();
                    
                    products.add(new Product(id, name, price, quantity, category));
                } catch (IllegalArgumentException e) {
                    System.err.println("Warning: Invalid product data at line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not read inventory file: " + e.getMessage());
            return new ArrayList<>();
        }
        return products;
    }

    public void saveInventory(List<Product> products) {
        try {
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (Product product : products) {
                    writer.write(String.format("%s,%s,%.2f,%d,%s%n",
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getCategory()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not save inventory: " + e.getMessage(), e);
        }
    }
}