package ui;

import auth.AuthService;
import models.User;
import services.StoreService;
import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JPanel {
    private MainFrame mainFrame;

    public AdminPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(8, 1));

        JButton viewUserButton = new JButton("View a user");
        JButton createUserButton = new JButton("Create a user");
        JButton updateUserButton = new JButton("Update a user");
        JButton deleteUserButton = new JButton("Delete a user");
        JButton whitelistEmailButton = new JButton("Whitelist an email");
        JButton createStoreButton = new JButton("Create a store");
        JButton deleteStoreButton = new JButton("Delete a store");
        JButton addEmployeeToStoreButton = new JButton("Add employee to store");
        JButton logoutButton = new JButton("Logout");

        viewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userIdStr = JOptionPane.showInputDialog(mainFrame, "Enter the user ID to view:");
                if (userIdStr != null) {
                    int userId = Integer.parseInt(userIdStr);
                    User user = UserService.getUserById(userId);
                    if (user != null) {
                        JOptionPane.showMessageDialog(mainFrame, "User found: " + user);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "User not found.");
                    }
                }
            }
        });

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField emailField = new JTextField();
                JTextField pseudoField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                JTextField roleField = new JTextField();
                Object[] message = {
                    "Email:", emailField,
                    "Pseudo:", pseudoField,
                    "Password:", passwordField,
                    "Role (ADMIN or EMPLOYE):", roleField
                };
                int option = JOptionPane.showConfirmDialog(mainFrame, message, "Create User", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String email = emailField.getText();
                    String pseudo = pseudoField.getText();
                    String password = new String(passwordField.getPassword());
                    String role = roleField.getText().toUpperCase();
                    boolean created = AuthService.registerUser(email, pseudo, password, role);
                    JOptionPane.showMessageDialog(mainFrame, created ? "User created successfully." : "Failed to create user.");
                }
            }
        });

updateUserButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField userIdField = new JTextField();
        JTextField pseudoField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField roleField = new JTextField();
        Object[] message = {
            "User ID:", userIdField,
            "New Pseudo:", pseudoField,
            "New Password:", passwordField,
            "New Role (ADMIN or EMPLOYE):", roleField
        };
        int option = JOptionPane.showConfirmDialog(mainFrame, message, "Update User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int userId = Integer.parseInt(userIdField.getText());
            String newPseudo = pseudoField.getText();
            String newPassword = new String(passwordField.getPassword());
            String newRole = roleField.getText().toUpperCase();
            boolean updated = UserService.updateUser(userId, mainFrame.getAuthService().getCurrentUser().getId(), newPseudo, newPassword, newRole);
            JOptionPane.showMessageDialog(mainFrame, updated ? "User updated successfully." : "Failed to update user.");
        }
    }
});

deleteUserButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String userIdStr = JOptionPane.showInputDialog(mainFrame, "Enter the user ID to delete:");
        if (userIdStr != null) {
            int userId = Integer.parseInt(userIdStr);
            boolean deleted = UserService.deleteUser(userId, mainFrame.getAuthService().getCurrentUser().getId());
            JOptionPane.showMessageDialog(mainFrame, deleted ? "User deleted successfully." : "Failed to delete user.");
        }
    }
});

        whitelistEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = JOptionPane.showInputDialog(mainFrame, "Enter the email to whitelist:");
                if (email != null) {
                    boolean whitelisted = AuthService.whitelistEmail(email);
                    JOptionPane.showMessageDialog(mainFrame, whitelisted ? "Email whitelisted successfully." : "Failed to whitelist email.");
                }
            }
        });

        createStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField();
                JTextField locationField = new JTextField();
                Object[] message = {
                    "Store Name:", nameField,
                    "Location:", locationField
                };
                int option = JOptionPane.showConfirmDialog(mainFrame, message, "Create Store", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String location = locationField.getText();
                    boolean created = StoreService.createStore(name, location);
                    JOptionPane.showMessageDialog(mainFrame, created ? "Store created successfully." : "Failed to create store.");
                }
            }
        });

        deleteStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeIdStr = JOptionPane.showInputDialog(mainFrame, "Enter the store ID to delete:");
                if (storeIdStr != null) {
                    int storeId = Integer.parseInt(storeIdStr);
                    boolean deleted = StoreService.deleteStore(storeId);
                    JOptionPane.showMessageDialog(mainFrame, deleted ? "Store deleted successfully." : "Failed to delete store.");
                }
            }
        });

        addEmployeeToStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField storeIdField = new JTextField();
                JTextField userIdField = new JTextField();
                Object[] message = {
                    "Store ID:", storeIdField,
                    "User ID:", userIdField
                };
                int option = JOptionPane.showConfirmDialog(mainFrame, message, "Add Employee to Store", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    int storeId = Integer.parseInt(storeIdField.getText());
                    int userId = Integer.parseInt(userIdField.getText());
                    boolean added = StoreService.addEmployeeToStore(storeId, userId);
                    JOptionPane.showMessageDialog(mainFrame, added ? "Employee added to store successfully." : "Failed to add employee to store.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("Login");
            }
        });

        add(viewUserButton);
        add(createUserButton);
        add(updateUserButton);
        add(deleteUserButton);
        add(whitelistEmailButton);
        add(createStoreButton);
        add(deleteStoreButton);
        add(addEmployeeToStoreButton);
        add(logoutButton);
    }
}