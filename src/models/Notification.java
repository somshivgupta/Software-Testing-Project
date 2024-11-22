package models;

import java.util.Date;

public class Notification {
   private String id;
   private String type;    // "LOW_STOCK", "ORDER_STATUS", "PRICE_CHANGE", "ERROR"
   private String message;
   private Date timestamp;
   private boolean isRead;

   public Notification(String id, String type, String message) {
       this.id = id;
       this.type = type;
       this.message = message;
       this.timestamp = new Date();
       this.isRead = false;
   }

   // Getters
   public String getId() { return id; }
   public String getType() { return type; }
   public String getMessage() { return message; }
   public Date getTimestamp() { return timestamp; }
   public boolean isRead() { return isRead; }

   public void markAsRead() {
       this.isRead = true;
   }
}