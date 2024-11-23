package services;

import models.Product;
import java.util.*;
import java.util.stream.Collectors;

public class SearchService {
    private InventoryManager inventoryManager;

    public SearchService(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public List<Product> searchByName(String keyword) {
        return inventoryManager.getAllProducts().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
        }

        return inventoryManager.getAllProducts().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Product> filterByStock(int minStock) {
        if (minStock < 0) {
            throw new IllegalArgumentException("Stock level cannot be negative");
        }
        return inventoryManager.getAllProducts().stream()
                .filter(p -> p.getQuantity() >= minStock)
                .collect(Collectors.toList());
    }
}