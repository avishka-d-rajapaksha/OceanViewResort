package com.oceanview.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class NavigationUtil {
    
    private static Stage primaryStage;
    private static Object currentController;

    // පද්ධතිය ආරම්භයේදී Stage එක මෙයට ලබා දිය යුතුය
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * සරලව FXML ගොනුවේ නම ලබා දීමෙන් පිටු මාරු කරයි.
     * BUG FIX: Also stores the controller reference for later refresh
     */
    public static void navigateTo(String fxml) throws IOException {
        if (primaryStage == null) {
            System.err.println("දෝෂයකි: Primary Stage එක NavigationUtil වෙත ලබා දී නැත!");
            return;
        }

        // FXML එක පූරණය කිරීම
        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/com/oceanview/ui/" + fxml));
        Parent root = loader.load();
        
        // BUG FIX: Store controller reference for refresh capability
        currentController = loader.getController();

        // පවතින Window එකේම අලුත් Scene එක පෙන්වීම
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ocean View Resort - " + fxml.replace(".fxml", ""));
        primaryStage.show();
    }
    
    // BUG FIX: Method to refresh current view's data
    public static void refreshCurrentView() {
        if (currentController != null) {
            try {
                // Try to call refreshData method if it exists
                java.lang.reflect.Method refreshMethod = currentController.getClass().getMethod("refreshData");
                refreshMethod.invoke(currentController);
                System.out.println("✓ Current view data refreshed");
            } catch (NoSuchMethodException e) {
                // Controller doesn't have refreshData method - that's ok
                System.out.println("View does not support refresh");
            } catch (Exception e) {
                System.err.println("Error refreshing view: " + e.getMessage());
            }
        }
    }
}