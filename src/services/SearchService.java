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
       return inventoryManager.getAllProducts().stream()
           .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
           .collect(Collectors.toList());
   }

   public List<Product> filterByStock(int minStock) {
       return inventoryManager.getAllProducts().stream()
           .filter(p -> p.getQuantity() >= minStock)
           .collect(Collectors.toList());
   }

   public List<Product> searchAndFilter(String keyword, Double minPrice, Double maxPrice, Integer minStock) {
       List<Product> result = inventoryManager.getAllProducts();
       
       if (keyword != null) {
           result = result.stream()
               .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
               .collect(Collectors.toList());
       }
       
       if (minPrice != null) {
           result = result.stream()
               .filter(p -> p.getPrice() >= minPrice)
               .collect(Collectors.toList());
       }
       
       if (maxPrice != null) {
           result = result.stream()
               .filter(p -> p.getPrice() <= maxPrice)
               .collect(Collectors.toList());
       }
       
       if (minStock != null) {
           result = result.stream()
               .filter(p -> p.getQuantity() >= minStock)
               .collect(Collectors.toList());
       }
       
       return result;
   }
}