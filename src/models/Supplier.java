package models;

public class Supplier {
   private String id;
   private String name;
   private String contactPerson;
   private String email;
   private String phone;
   private double rating;
   private int totalOrders;

   public Supplier(String id, String name, String contactPerson, String email, String phone) {
       this.id = id;
       this.name = name;
       this.contactPerson = contactPerson;
       this.email = email;
       this.phone = phone;
       this.rating = 5.0;
       this.totalOrders = 0;
   }

   // Getters
   public String getId() { return id; }
   public String getName() { return name; }
   public String getContactPerson() { return contactPerson; }
   public String getEmail() { return email; }
   public String getPhone() { return phone; }
   public double getRating() { return rating; }
   public int getTotalOrders() { return totalOrders; }

   public void updateRating(double newRating) {
       this.rating = (this.rating * totalOrders + newRating) / (totalOrders + 1);
       this.totalOrders++;
   }

   @Override
    public String toString() {
        return String.format("Supplier{id='%s', name='%s', contactPerson='%s', rating=%.1f, orders=%d}", 
            id, name, contactPerson, rating, totalOrders);
    }
}