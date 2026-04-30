package com.oceanview.service;

import com.oceanview.model.UserRole;
import com.oceanview.network.WebServiceClient;

/**
 * Service Layer acts as a Proxy for the distributed Web Service.
 * Controllers call this class without knowing it fetches data from a REST API.
 * ROLE-BASED: Now returns user information including role for access control
 */
public class UserService {

    /**
     * Legacy login method - replaced by loginAndGetRole
     */
    @Deprecated
    public boolean login(String username, String password) {
        try {
            return WebServiceClient.loginViaApi(username, password);
        } catch (Exception e) {
            System.err.println("API Error during login: " + e.getMessage());
            return false;
        }
    }

    /**
     * NEW: Login and return user information with role
     * Returns array: {username, role, userId}
     * Example: {"admin", "ADMIN", "1"}
     */
    public String[] loginAndGetRole(String username, String password) {
        try {
            // For now, assign roles based on username (in production, get from database)
            // This would normally come from WebServiceClient
            
            if (username.equals("admin") && password.equals("admin123")) {
                // Admin user
                return new String[]{username, UserRole.ADMIN.name(), "1"};
            } else if (username.equals("staff") && password.equals("staff123")) {
                // Staff user
                return new String[]{username, UserRole.STAFF.name(), "2"};
            } else if (username.equals("guest") && password.equals("guest123")) {
                // Guest user
                return new String[]{username, UserRole.GUEST.name(), "3"};
            }
            
            return null; // Login failed
        } catch (Exception e) {
            System.err.println("API Error during login: " + e.getMessage());
            return null;
        }
    }

    public boolean registerUser(String username, String password, String role) {
        try {
            return WebServiceClient.registerViaApi(username, password, role);
        } catch (Exception e) {
            System.err.println("API Error during registration: " + e.getMessage());
            return false;
        }
    }
}