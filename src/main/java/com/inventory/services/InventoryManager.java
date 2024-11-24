package com.inventory.services;

import java.util.*;

import com.inventory.models.Product;
import com.inventory.utils.FileHandler;

public class InventoryManager {
    private Map<String, Product> inventory;
    private FileHandler fileHandler;

    public InventoryManager() {
        this.inventory = new HashMap<>();
        this.fileHandler = new FileHandler();
    }

    public void addProduct(Product product) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Invalid product");
        }
        inventory.put(product.getId(), product);
    }

    public void updateQuantity(String productId, int changeAmount) {
        Product product = inventory.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        int newQuantity = product.getQuantity() + changeAmount;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient quantity");
        }
        product.setQuantity(newQuantity);
    }

    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStock = new ArrayList<>();
        for (Product product : inventory.values()) {
            if (product.getQuantity() <= threshold) {
                lowStock.add(product);
            }
        }
        return lowStock;
    }

    public double calculateTotalInventoryValue() {
        double total = 0.0;
        for (Product product : inventory.values()) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    public Optional<Product> getProduct(String id) {
        return Optional.ofNullable(inventory.get(id));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(inventory.values());
    }

    public void saveInventoryToFile() {
        fileHandler.saveInventory(new ArrayList<>(inventory.values()));
    }

    public void loadInventoryFromFile() {
        List<Product> products = fileHandler.loadInventory();
        inventory.clear();
        for (Product product : products) {
            inventory.put(product.getId(), product);
        }
    }
}