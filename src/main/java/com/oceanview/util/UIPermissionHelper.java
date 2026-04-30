package com.oceanview.util;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

/**
 * UI Permission Helper - Disables/Enables UI components based on user role
 * Ensures role-based access control at the UI level
 */
public class UIPermissionHelper {

    /**
     * Disable UI component if user doesn't have permission
     */
    public static void disableIfNoPermission(Button btn, String permission) {
        SessionManager session = SessionManager.getInstance();
        if (session.isLoggedIn() && session.hasPermission(permission)) {
            btn.setDisable(false);
        } else {
            btn.setDisable(true);
            btn.setStyle("-fx-opacity: 0.5;");
        }
    }

    public static void disableIfNoPermission(MenuItem item, String permission) {
        SessionManager session = SessionManager.getInstance();
        if (session.isLoggedIn() && session.hasPermission(permission)) {
            item.setDisable(false);
        } else {
            item.setDisable(true);
        }
    }

    public static void disableIfNoPermission(Pane pane, String permission) {
        SessionManager session = SessionManager.getInstance();
        if (session.isLoggedIn() && session.hasPermission(permission)) {
            pane.setDisable(false);
        } else {
            pane.setDisable(true);
        }
    }

    /**
     * Hide entire component if user doesn't have permission
     */
    public static void hideIfNoPermission(Button btn, String permission) {
        SessionManager session = SessionManager.getInstance();
        if (session.isLoggedIn() && session.hasPermission(permission)) {
            btn.setVisible(true);
        } else {
            btn.setVisible(false);
        }
    }

    public static void hideIfNoPermission(Pane pane, String permission) {
        SessionManager session = SessionManager.getInstance();
        if (session.isLoggedIn() && session.hasPermission(permission)) {
            pane.setVisible(true);
        } else {
            pane.setVisible(false);
        }
    }

    /**
     * Specific role checks for UI
     */
    public static void disableIfNotAdmin(Button btn) {
        SessionManager session = SessionManager.getInstance();
        btn.setDisable(!session.isAdmin());
        if (!session.isAdmin()) {
            btn.setStyle("-fx-opacity: 0.5;");
        }
    }

    public static void disableIfNotStaffOrAdmin(Button btn) {
        SessionManager session = SessionManager.getInstance();
        boolean allowed = session.isAdmin() || session.isStaff();
        btn.setDisable(!allowed);
        if (!allowed) {
            btn.setStyle("-fx-opacity: 0.5;");
        }
    }

    public static void hideIfNotAdmin(Pane pane) {
        SessionManager session = SessionManager.getInstance();
        pane.setVisible(session.isAdmin());
    }

    public static void hideIfNotStaffOrAdmin(Pane pane) {
        SessionManager session = SessionManager.getInstance();
        pane.setVisible(session.isAdmin() || session.isStaff());
    }

    /**
     * Show role-specific sections
     */
    public static void showOnlyAdminView(Pane... panes) {
        SessionManager session = SessionManager.getInstance();
        for (Pane pane : panes) {
            pane.setVisible(session.isAdmin());
        }
    }

    public static void showOnlyStaffView(Pane... panes) {
        SessionManager session = SessionManager.getInstance();
        for (Pane pane : panes) {
            pane.setVisible(session.isStaff() || session.isAdmin());
        }
    }
}
