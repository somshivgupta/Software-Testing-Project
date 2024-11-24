package com.inventory.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.inventory.models.Product;

public class FileHandler {
    private static final String FILE_PATH = "src/main/java/com/inventory/resources/inventory.txt";

    public void saveInventory(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                writer.write(String.format("%s,%s,%.2f,%d,%s%n",
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategory()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not save inventory: " + e.getMessage(), e);
        }
    }

    public List<Product> loadInventory() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 5) {
                        throw new RuntimeException("Invalid data format at line " + lineNumber);
                    }
                    products.add(new Product(
                        parts[0], // id
                        parts[1], // name
                        Double.parseDouble(parts[2]), // price
                        Integer.parseInt(parts[3]), // quantity
                        parts[4]  // category
                    ));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid number format at line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read inventory file: " + e.getMessage(), e);
        }
        return products;
    }
}