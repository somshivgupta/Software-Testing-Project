package com.inventory.unit.models;

import org.junit.jupiter.api.Test;
import com.inventory.models.User;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    
    @Test
    void testConstructorDefinitionsAndUses() {
        // Tests DU pairs: constructor params → field assignments → getter uses
        String usernameValue = "testUser";    // username definition
        String passwordValue = "testPass";     // password definition
        String roleValue = "USER";            // role definition
        
        User user = new User(usernameValue, passwordValue, roleValue);  // field definitions
        
        // Test each definition reaches its use
        assertEquals(usernameValue, user.getUsername());  // username use
        assertEquals(roleValue, user.getRole());         // role use
    }

    @Test
    void testPasswordDefinitionAndUse() {
        // Tests DU pair: password definition → use in checkPassword
        String passwordValue = "testPass";     // password definition
        User user = new User("testUser", passwordValue, "USER");
        
        // Test password definition used in checking
        assertTrue(user.checkPassword(passwordValue));  // password use
    }
}