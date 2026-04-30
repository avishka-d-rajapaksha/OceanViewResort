package com.oceanview.controller;

import com.oceanview.model.Room;
import com.oceanview.service.RoomService;
import com.oceanview.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class RoomsController extends BaseController {

    @FXML private TableView<Room> tblRooms;
    @FXML private TableColumn<Room, String> colRoomNo, colType, colStatus;
    @FXML private TableColumn<Room, Double> colPrice;
    @FXML private TextField txtRoomNumber, txtPrice;
    @FXML private ComboBox<String> cmbRoomType;
    @FXML private Label lblRoomStatus;
    @FXML private Button btnAddRoom;
    @FXML private Button btnEditRoom;
    @FXML private Button btnDeleteRoom;

    private final RoomService roomService = new RoomService();
    private ObservableList<Room> roomData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // RBAC: Apply role-based restrictions
        applyRoleBasedRestrictions();
        
        // Safety Guard: Check if table exists before setting up shortcuts
        if (tblRooms != null) {
            setupGlobalShortcuts(tblRooms);
            
            // Safety Guard: Bind Columns only if they were found in FXML
            if (colRoomNo != null) colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
            if (colType != null) colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            if (colPrice != null) colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            
            loadRoomData();
        }

        if (cmbRoomType != null) {
            cmbRoomType.setItems(FXCollections.observableArrayList("Standard", "Deluxe", "Suite", "Penthouse"));
        }
    }
    
    /**
     * RBAC: Apply role-based restrictions to UI components
     */
    private void applyRoleBasedRestrictions() {
        SessionManager session = SessionManager.getInstance();
        
        // Disable input fields if user is guest
        if (!session.canAddRoom()) {
            if (txtRoomNumber != null) txtRoomNumber.setDisable(true);
            if (txtPrice != null) txtPrice.setDisable(true);
            if (cmbRoomType != null) cmbRoomType.setDisable(true);
        }
    }
    
    // BUG FIX: Add method to refresh room data when navigating to this view
    public void refreshData() {
        loadRoomData();
    }

    private void loadRoomData() {
        if (tblRooms == null) return;
        List<Room> rooms = roomService.getAllRooms();
        roomData.setAll(rooms);
        tblRooms.setItems(roomData);
    }

    // FXML Error එක (LoadException) නැති කිරීමට මෙය අලුතින් එකතු කරන ලදී
    @FXML
    public void showAddRoom(ActionEvent event) {
        System.out.println("Add Room Action Triggered!");
        // ඔබට වෙනත් කවුළුවක් (Pane එකක්) පෙන්වීමට අවශ්‍ය නම් මෙතැන කේතය ලියන්න
    }

    @FXML
    public void handleAddRoom(ActionEvent event) {
        // RBAC: Check permission
        if (!SessionManager.getInstance().canAddRoom()) {
            if (lblRoomStatus != null) {
                lblRoomStatus.setText("✗ Error: You don't have permission to add rooms.");
                lblRoomStatus.setStyle("-fx-text-fill: red;");
            }
            return;
        }
        
        try {
            String roomNo = txtRoomNumber.getText();
            String type = cmbRoomType.getValue();
            double price = Double.parseDouble(txtPrice.getText());

            boolean success = roomService.addRoom(roomNo, type, price);

            if (success) {
                if (lblRoomStatus != null) {
                    lblRoomStatus.setText("✓ Room added successfully!");
                    lblRoomStatus.setStyle("-fx-text-fill: green;");
                }
                clearForm();
                loadRoomData();
            } else if (lblRoomStatus != null) {
                lblRoomStatus.setText("✗ Error: Room number exists.");
                lblRoomStatus.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            if (lblRoomStatus != null) lblRoomStatus.setText("✗ Error: Invalid input.");
        }
    }

    private void clearForm() {
        if (txtRoomNumber != null) txtRoomNumber.clear();
        if (txtPrice != null) txtPrice.clear();
        if (cmbRoomType != null) cmbRoomType.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void handleEditRoom(ActionEvent event) {
        // RBAC: Check permission
        if (!SessionManager.getInstance().canEditRoom()) {
            if (lblRoomStatus != null) {
                lblRoomStatus.setText("✗ Error: You don't have permission to edit rooms.");
                lblRoomStatus.setStyle("-fx-text-fill: red;");
            }
            return;
        }
        
        Room selected = tblRooms.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Pre-fill form with selected room data
            txtRoomNumber.setText(selected.getRoomNumber());
            txtPrice.setText(String.valueOf(selected.getPrice()));
            cmbRoomType.setValue(selected.getType());
            
            if (lblRoomStatus != null) {
                lblRoomStatus.setText("✓ Edit mode - change values and click Add to save");
                lblRoomStatus.setStyle("-fx-text-fill: blue;");
            }
        } else {
            if (lblRoomStatus != null) {
                lblRoomStatus.setText("✗ Error: Select a room to edit.");
                lblRoomStatus.setStyle("-fx-text-fill: red;");
            }
        }
    }
    
    @FXML
    public void handleDeleteRoom(ActionEvent event) {
        // RBAC: Only admin can delete
        if (!SessionManager.getInstance().canDeleteRoom()) {
            if (lblRoomStatus != null) {
                lblRoomStatus.setText("✗ Error: Only administrators can delete rooms.");
                lblRoomStatus.setStyle("-fx-text-fill: red;");
            }
            return;
        }
        
        Room selected = tblRooms.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean success = roomService.deleteRoom(selected.getRoomNumber());
            
            if (success) {
                if (lblRoomStatus != null) {
                    lblRoomStatus.setText("✓ Room deleted successfully!");
                    lblRoomStatus.setStyle("-fx-text-fill: green;");
                }
                clearForm();
                loadRoomData();
            } else {
                if (lblRoomStatus != null) {
                    lblRoomStatus.setText("✗ Error: Could not delete room.");
                    lblRoomStatus.setStyle("-fx-text-fill: red;");
                }
            }
        } else {
            if (lblRoomStatus != null) {
                lblRoomStatus.setText("✗ Error: Select a room to delete.");
                lblRoomStatus.setStyle("-fx-text-fill: red;");
            }
        }
    }
}