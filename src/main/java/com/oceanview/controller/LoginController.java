package com.oceanview.controller;

import com.oceanview.model.UserRole;
import com.oceanview.service.UserService;
import com.oceanview.util.NavigationUtil;
import com.oceanview.util.SessionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Handles User Authentication with Role-Based Access Control (RBAC)
 * Uses Asynchronous processing to prevent UI freezing during DB lookups.
 */
public class LoginController extends BaseController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    private final UserService userService = new UserService();

    /**
     * RBAC Enhancement: Login with role assignment
     */
    @FXML
    void handleLogin(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();

        // Basic Validation
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter all fields!");
            return;
        }

        // UX: Provide immediate feedback
        lblError.setStyle("-fx-text-fill: #0066cc;");
        lblError.setText("Authenticating...");
        btnLogin.setDisable(true); 

        // Async login with role retrieval
        CompletableFuture.supplyAsync(() -> userService.loginAndGetRole(username, password))
            .thenAccept(userInfo -> {
                // Return to JavaFX Thread to update UI
                Platform.runLater(() -> {
                    btnLogin.setDisable(false);

                    if (userInfo != null && userInfo.length >= 3) {
                        // Login successful - userInfo format: {username, role, userId}
                        String user = userInfo[0];
                        String roleStr = userInfo[1];
                        int userId = Integer.parseInt(userInfo[2]);
                        
                        // RBAC: Set current user session with role
                        UserRole role = UserRole.valueOf(roleStr);
                        SessionManager.getInstance().login(user, role, userId);
                        
                        try {
                            // Navigate to dashboard
                            NavigationUtil.navigateTo("Dashboard.fxml");
                            
                            // Clear password field
                            txtPassword.clear();
                            
                        } catch (IOException e) {
                            showError("Failed to load Dashboard!");
                            e.printStackTrace();
                        }
                        
                    } else {
                        showError("Invalid Username or Password!");
                    }
                });
            })
            .exceptionally(ex -> {
                Platform.runLater(() -> {
                    btnLogin.setDisable(false);
                    showError("Connection error. Please try again later.");
                    ex.printStackTrace(); 
                });
                return null;
            });
    }

    /**
     * Navigate to Signup screen
     */
    @FXML
    void goToSignup(MouseEvent event) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        
        try {
            NavigationUtil.navigateTo("Signup.fxml");
        } catch (IOException e) {
            showError("Failed to load Signup page!");
            e.printStackTrace();
        }
    }

    /**
     * Show error message
     */
    private void showError(String message) {
        lblError.setStyle("-fx-text-fill: red;");
        lblError.setText(message);
    }
}