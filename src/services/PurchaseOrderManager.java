// NEW FILE: src/main/services/PurchaseOrderManager.java
package services;

import models.*;
import java.util.*;
import java.util.stream.*;

public class PurchaseOrderManager {
   private Map<String, PurchaseOrder> purchaseOrders;
   private InventoryManager inventoryManager;
   private SupplierManager supplierManager;

   public PurchaseOrderManager(InventoryManager inventoryManager, SupplierManager supplierManager) {
       this.purchaseOrders = new HashMap<>();
       this.inventoryManager = inventoryManager;
       this.supplierManager = supplierManager;
   }

   public PurchaseOrder createPurchaseOrder(String supplierId) {
       Supplier supplier = supplierManager.getSupplier(supplierId);
       if (supplier == null) {
           throw new IllegalArgumentException("Supplier not found");
       }
       String poId = "PO" + (purchaseOrders.size() + 1);
       PurchaseOrder po = new PurchaseOrder(poId, supplier);
       purchaseOrders.put(poId, po);
       return po;
   }

   public void addItemToPO(String poId, String productId, int quantity) {
       PurchaseOrder po = purchaseOrders.get(poId);
       if (po == null) {
           throw new IllegalArgumentException("Purchase Order not found");
       }
       Optional<Product> product = inventoryManager.getProduct(productId);
       if (product.isPresent()) {
           po.addItem(product.get(), quantity);
       }
   }

   public void approvePO(String poId) {
       PurchaseOrder po = purchaseOrders.get(poId);
       if (po != null) {
           po.setStatus("APPROVED");
       }
   }

   public void receivePO(String poId) {
       PurchaseOrder po = purchaseOrders.get(poId);
       if (po != null && po.getStatus().equals("APPROVED")) {
           po.setStatus("RECEIVED");
           // Update inventory
           po.getItems().forEach((product, quantity) -> 
               inventoryManager.updateQuantity(product.getId(), quantity));
       }
   }

   public List<PurchaseOrder> getPendingPOs() {
       return purchaseOrders.values().stream()
                          .filter(po -> po.getStatus().equals("PENDING"))
                          .collect(Collectors.toList());
   }
}