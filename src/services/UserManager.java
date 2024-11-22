package services;

import models.User;
import java.util.*;

public class UserManager {
   private Map<String, User> users;
   private User currentUser;

   public UserManager() {
       this.users = new HashMap<>();
       // Add default admin
       addUser(new User("admin", "admin123", "ADMIN"));
   }

   public void addUser(User user) {
       users.put(user.getUsername(), user);
   }

   public boolean login(String username, String password) {
       User user = users.get(username);
       if (user != null && user.checkPassword(password)) {
           currentUser = user;
           return true;
       }
       return false;
   }

   public void logout() {
       currentUser = null;
   }

   public boolean isAdmin() {
       return currentUser != null && currentUser.getRole().equals("ADMIN");
   }

   public User getCurrentUser() {
       return currentUser;
   }
}