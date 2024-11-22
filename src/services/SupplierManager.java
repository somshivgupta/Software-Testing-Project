package services;

import models.Supplier;
import java.util.*;
import java.util.stream.*;

public class SupplierManager {
   private Map<String, Supplier> suppliers;
   private Map<String, List<String>> supplierProducts; // supplierId -> productIds

   public SupplierManager() {
       this.suppliers = new HashMap<>();
       this.supplierProducts = new HashMap<>();
   }

   public void addSupplier(Supplier supplier) {
       suppliers.put(supplier.getId(), supplier);
       supplierProducts.put(supplier.getId(), new ArrayList<>());
   }

   public void assignProductToSupplier(String supplierId, String productId) {
       if (!suppliers.containsKey(supplierId)) {
           throw new IllegalArgumentException("Supplier not found");
       }
       supplierProducts.get(supplierId).add(productId);
   }

   public void updateSupplierRating(String supplierId, double rating) {
       if (rating < 0 || rating > 5) {
           throw new IllegalArgumentException("Rating must be between 0 and 5");
       }
       Supplier supplier = suppliers.get(supplierId);
       if (supplier != null) {
           supplier.updateRating(rating);
       }
   }

   public List<String> getSupplierProducts(String supplierId) {
       return supplierProducts.getOrDefault(supplierId, new ArrayList<>());
   }

   public List<Supplier> getTopSuppliers(int limit) {
       return suppliers.values().stream()
                      .sorted((s1, s2) -> Double.compare(s2.getRating(), s1.getRating()))
                      .limit(limit)
                      .collect(Collectors.toList());
   }
}