package models;

public class OrderItem {
   private Product product;
   private int quantity;
   private double priceAtTime;

   public OrderItem(Product product, int quantity) {
       this.product = product;
       this.quantity = quantity;
       this.priceAtTime = product.getPrice();
   }

   public Product getProduct() { return product; }
   public int getQuantity() { return quantity; }
   public double getPriceAtTime() { return priceAtTime; }
   public double getSubtotal() { return priceAtTime * quantity; }

   @Override
   public String toString() {
       return "OrderItem{" +
               "product=" + product.getName() +
               ", quantity=" + quantity +
               ", priceAtTime=" + priceAtTime +
               '}';
   }
}