package com.inventory.unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.inventory.services.UserManager;
import com.inventory.models.User;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Test
    void testUsersMapDefinitionAndUses() {
        // Tests users map definition → uses
        User user = new User("testUser", "pass", "USER");
        userManager.addUser(user);  // use in adding
        assertTrue(userManager.login("testUser", "pass"));  // use in retrieval
    }

    @Test
    void testCurrentUserDefinitionAndUses() {
        // Tests currentUser definition → uses
        User user = new User("testUser", "pass", "USER");
        userManager.addUser(user);
        userManager.login("testUser", "pass");  // currentUser definition
        
        assertEquals(user.getUsername(), userManager.getCurrentUser().getUsername());  // use
        assertFalse(userManager.isAdmin());  // use in role check
    }

    @Test
    void testAdminUserDefinitionAndUses() {
        // Tests default admin definition and uses
        assertTrue(userManager.login("admin", "admin123"));  // use of default admin
        assertTrue(userManager.isAdmin());  // use in role check
        
        userManager.logout();  // currentUser redefinition
        assertNull(userManager.getCurrentUser());  // use after redefinition
    }
}