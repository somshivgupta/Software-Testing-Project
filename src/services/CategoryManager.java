package services;

import models.Category;
import models.Product;
import java.util.*;

public class CategoryManager {
   private Map<String, Category> categories;
   private Map<String, List<Product>> categoryProducts;

   public CategoryManager() {
       this.categories = new HashMap<>();
       this.categoryProducts = new HashMap<>();
   }

   public void addCategory(Category category) {
       categories.put(category.getId(), category);
       categoryProducts.put(category.getId(), new ArrayList<>());
   }

   public void assignProductToCategory(String categoryId, Product product) {
       if (!categories.containsKey(categoryId)) {
           throw new IllegalArgumentException("Category not found");
       }
       categoryProducts.get(categoryId).add(product);
   }

   public List<Product> getProductsByCategory(String categoryId) {
       return categoryProducts.getOrDefault(categoryId, new ArrayList<>());
   }

   public void updateCategoryProducts(String categoryId, double priceChange) {
       List<Product> products = categoryProducts.get(categoryId);
       if (products != null) {
           for (Product product : products) {
               product.setPrice(product.getPrice() * (1 + priceChange));
           }
       }
   }
}