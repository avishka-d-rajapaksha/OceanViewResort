package com.oceanview.util;

import com.oceanview.model.UserRole;

/**
 * Session Manager to track current logged-in user and role
 * Ensures role-based access control throughout the application
 */
public class SessionManager {
    private static SessionManager instance;
    private String currentUsername;
    private UserRole currentRole;
    private int currentUserId;

    private SessionManager() {
    }

    /**
     * Get singleton instance
     */
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Login user with role
     */
    public void login(String username, UserRole role, int userId) {
        this.currentUsername = username;
        this.currentRole = role;
        this.currentUserId = userId;
        System.out.println("✓ User logged in: " + username + " (" + role.getDisplayName() + ")");
    }

    /**
     * Logout user
     */
    public void logout() {
        System.out.println("✓ User logged out: " + currentUsername);
        this.currentUsername = null;
        this.currentRole = null;
        this.currentUserId = 0;
    }

    /**
     * Check if user is logged in
     */
    public boolean isLoggedIn() {
        return currentUsername != null && currentRole != null;
    }

    /**
     * Get current username
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Get current role
     */
    public UserRole getCurrentRole() {
        return currentRole;
    }

    /**
     * Get current user ID
     */
    public int getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Check if user has permission
     */
    public boolean hasPermission(String action) {
        if (!isLoggedIn()) {
            return false;
        }
        return currentRole.hasPermission(action);
    }

    /**
     * Check specific role-based permissions
     */
    public boolean canAddRoom() {
        return isLoggedIn() && currentRole.canAddRoom();
    }

    public boolean canEditRoom() {
        return isLoggedIn() && currentRole.canEditRoom();
    }

    public boolean canDeleteRoom() {
        return isLoggedIn() && currentRole.canDeleteRoom();
    }

    public boolean canAddGuest() {
        return isLoggedIn() && currentRole.canAddGuest();
    }

    public boolean canEditGuest() {
        return isLoggedIn() && currentRole.canEditGuest();
    }

    public boolean canDeleteGuest() {
        return isLoggedIn() && currentRole.canDeleteGuest();
    }

    public boolean canCreateReservation() {
        return isLoggedIn() && currentRole.canCreateReservation();
    }

    public boolean canEditReservation() {
        return isLoggedIn() && currentRole.canEditReservation();
    }

    public boolean canDeleteReservation() {
        return isLoggedIn() && currentRole.canDeleteReservation();
    }

    public boolean canViewReports() {
        return isLoggedIn() && currentRole.canViewReports();
    }

    public boolean canManageBilling() {
        return isLoggedIn() && currentRole.canManageBilling();
    }

    public boolean canManageUsers() {
        return isLoggedIn() && currentRole.canManageUsers();
    }

    /**
     * Check if current user is admin
     */
    public boolean isAdmin() {
        return isLoggedIn() && currentRole == UserRole.ADMIN;
    }

    /**
     * Check if current user is staff
     */
    public boolean isStaff() {
        return isLoggedIn() && currentRole == UserRole.STAFF;
    }

    /**
     * Check if current user is guest
     */
    public boolean isGuest() {
        return isLoggedIn() && currentRole == UserRole.GUEST;
    }
}
