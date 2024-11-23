package models;

public class User {
    private String username;
    private String password; // Not hashed for this project
    private String role; // "ADMIN" or "USER"

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters only - no setters for security
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', role='" + role + "'}";
    }
}