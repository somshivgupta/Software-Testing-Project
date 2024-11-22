package models;

public class Product {
   private String id;
   private String name;
   private double price;
   private int quantity;
   private String category;

   public Product(String id, String name, double price, int quantity, String category) {
       this.id = id;
       this.name = name;
       this.price = price;
       this.quantity = quantity;
       this.category = category;
   }

   // Getters
   public String getId() { return id; }
   public String getName() { return name; }
   public double getPrice() { return price; }
   public int getQuantity() { return quantity; }
   public String getCategory() { return category; }

   // Setters
   public void setName(String name) { this.name = name; }
   public void setPrice(double price) { this.price = price; }
   public void setQuantity(int quantity) { this.quantity = quantity; }
   public void setCategory(String category) { this.category = category; }

   @Override
   public String toString() {
       return "Product{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", quantity=" + quantity +
               ", category='" + category + '\'' +
               '}';
   }
}
