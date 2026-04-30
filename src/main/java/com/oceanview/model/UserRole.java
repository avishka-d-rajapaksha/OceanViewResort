package com.oceanview.model;

/**
 * User Roles for Role-Based Access Control (RBAC)
 * Defines different access levels in the system
 */
public enum UserRole {
    ADMIN("Administrator", "Full access to all features"),
    STAFF("Staff Member", "Can manage rooms, guests, reservations"),
    GUEST("Guest", "View-only access to bookings and profile");

    private final String displayName;
    private final String description;

    UserRole(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Check if user role has permission for an action
     */
    public boolean hasPermission(String action) {
        switch (this) {
            case ADMIN:
                // Admin can do everything
                return true;
            
            case STAFF:
                // Staff can manage rooms, guests, reservations
                return action.startsWith("manage_") || action.startsWith("view_");
            
            case GUEST:
                // Guest can only view their own data
                return action.startsWith("view_");
            
            default:
                return false;
        }
    }

    /**
     * Check specific permissions
     */
    public boolean canAddRoom() {
        return this == ADMIN || this == STAFF;
    }

    public boolean canEditRoom() {
        return this == ADMIN || this == STAFF;
    }

    public boolean canDeleteRoom() {
        return this == ADMIN;
    }

    public boolean canAddGuest() {
        return this == ADMIN || this == STAFF;
    }

    public boolean canEditGuest() {
        return this == ADMIN || this == STAFF;
    }

    public boolean canDeleteGuest() {
        return this == ADMIN;
    }

    public boolean canCreateReservation() {
        return this == ADMIN || this == STAFF || this == GUEST;
    }

    public boolean canEditReservation() {
        return this == ADMIN || this == STAFF;
    }

    public boolean canDeleteReservation() {
        return this == ADMIN;
    }

    public boolean canViewReports() {
        return this == ADMIN || this == STAFF;
    }

    public boolean canManageBilling() {
        return this == ADMIN || this == STAFF;
    }

    public boolean canManageUsers() {
        return this == ADMIN;
    }
}
