package utils;

import models.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_PATH = "inventory.txt";

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
            throw new RuntimeException("Error saving inventory", e);
        }
    }

    public List<Product> loadInventory() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                products.add(new Product(
                    parts[0], // id
                    parts[1], // name
                    Double.parseDouble(parts[2]), // price
                    Integer.parseInt(parts[3]), // quantity
                    parts[4]  // category
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading inventory", e);
        }
        return products;
    }
}