package services;

import models.Notification;
import models.Product;
import java.util.*;
import java.util.stream.Collectors;

public class NotificationService {
   private List<Notification> notifications;
   private Map<String, List<String>> userSubscriptions; // userId -> notificationTypes

   public NotificationService() {
       this.notifications = new ArrayList<>();
       this.userSubscriptions = new HashMap<>();
   }

   public void subscribe(String userId, String notificationType) {
       userSubscriptions.computeIfAbsent(userId, k -> new ArrayList<>())
                       .add(notificationType);
   }

   public void createNotification(String type, String message) {
       String id = "NOT" + (notifications.size() + 1);
       notifications.add(new Notification(id, type, message));
   }

   public void createLowStockNotification(Product product) {
       if (product.getQuantity() < 5) {
           createNotification("LOW_STOCK", 
               "Low stock alert for " + product.getName() + 
               " (Quantity: " + product.getQuantity() + ")");
       }
   }

   public void createOrderNotification(String orderId, String status) {
       createNotification("ORDER_STATUS", 
           "Order " + orderId + " status changed to: " + status);
   }

   public List<Notification> getUnreadNotifications() {
       return notifications.stream()
                         .filter(n -> !n.isRead())
                         .collect(Collectors.toList());
   }

   public void markAsRead(String notificationId) {
       notifications.stream()
                   .filter(n -> n.getId().equals(notificationId))
                   .forEach(Notification::markAsRead);
   }
}