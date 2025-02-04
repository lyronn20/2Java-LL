package ui;

import models.Inventory;
import models.Item;
import models.Store;
import models.User;
import services.InventoryService;
import services.StoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeePanel extends JPanel {
    private MainFrame mainFrame;

    public EmployeePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(5, 1));

        JButton viewStoresButton = new JButton("View stores");
        JButton viewEmployeesButton = new JButton("View employees of store");
        JButton viewInventoryButton = new JButton("View inventory");
        JButton createItemButton = new JButton("Create item");
        JButton deleteItemButton = new JButton("Delete item");
        JButton updateItemQuantityButton = new JButton("Update item quantity");
        JButton logoutButton = new JButton("Logout");

        viewStoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Store> stores = StoreService.getStores();
                StringBuilder storesList = new StringBuilder("Stores:\n");
                for (Store store : stores) {
                    storesList.append(store).append("\n");
                }
                JOptionPane.showMessageDialog(mainFrame, storesList.toString());
            }
        });

        viewEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeIdStr = JOptionPane.showInputDialog(mainFrame, "Enter the store ID:");
                if (storeIdStr != null) {
                    int storeId = Integer.parseInt(storeIdStr);
                    List<User> employees = StoreService.getEmployeesByStoreId(storeId);
                    StringBuilder employeesList = new StringBuilder("Employees:\n");
                    for (User employee : employees) {
                        employeesList.append(employee).append("\n");
                    }
                    JOptionPane.showMessageDialog(mainFrame, employeesList.toString());
                }
            }
        });

        viewInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeIdStr = JOptionPane.showInputDialog(mainFrame, "Enter the store ID:");
                if (storeIdStr != null) {
                    int storeId = Integer.parseInt(storeIdStr);
                    Inventory inventory = InventoryService.getInventoryByStoreId(storeId);
                    StringBuilder inventoryList = new StringBuilder("Inventory:\n");
                    for (Item item : inventory.getItems()) {
                        inventoryList.append(item).append("\n");
                    }
                    JOptionPane.showMessageDialog(mainFrame, inventoryList.toString());
                }
            }
        });

        createItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField();
                JTextField quantityField = new JTextField();
                JTextField priceField = new JTextField();
                JTextField storeIdField = new JTextField();
                Object[] message = {
                    "Item Name:", nameField,
                    "Quantity:", quantityField,
                    "Price:", priceField,
                    "Store ID:", storeIdField
                };
                int option = JOptionPane.showConfirmDialog(mainFrame, message, "Create Item", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    int storeId = Integer.parseInt(storeIdField.getText());
                    boolean created = InventoryService.createItem(name, quantity, price, storeId);
                    JOptionPane.showMessageDialog(mainFrame, created ? "Item created successfully." : "Failed to create item.");
                }
            }
        });

        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemIdStr = JOptionPane.showInputDialog(mainFrame, "Enter the item ID to delete:");
                if (itemIdStr != null) {
                    int itemId = Integer.parseInt(itemIdStr);
                    boolean deleted = InventoryService.deleteItem(itemId);
                    JOptionPane.showMessageDialog(mainFrame, deleted ? "Item deleted successfully." : "Failed to delete item.");
                }
            }
        });

        updateItemQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField itemIdField = new JTextField();
                JTextField quantityChangeField = new JTextField();
                Object[] message = {
                    "Item ID:", itemIdField,
                    "Quantity Change:", quantityChangeField
                };
                int option = JOptionPane.showConfirmDialog(mainFrame, message, "Update Item Quantity", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    int itemId = Integer.parseInt(itemIdField.getText());
                    int quantityChange = Integer.parseInt(quantityChangeField.getText());
                    boolean updated = InventoryService.updateItemQuantity(itemId, quantityChange);
                    JOptionPane.showMessageDialog(mainFrame, updated ? "Item quantity updated successfully." : "Failed to update item quantity.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("Login");
            }
        });

        add(viewStoresButton);
        add(viewEmployeesButton);
        add(viewInventoryButton);
        add(createItemButton);
        add(deleteItemButton);
        add(updateItemQuantityButton);
        add(logoutButton);
    }
}