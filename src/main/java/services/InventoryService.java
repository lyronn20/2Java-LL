package services;

import database.DatabaseManager;
import models.Inventory;
import models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    public static Inventory getInventoryByStoreId(int storeId) {
        Inventory inventory = new Inventory(storeId);
        String sql = "SELECT * FROM inventories WHERE store_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                inventory.addItem(item);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'inventaire : " + e.getMessage());
        }
        return inventory;
    }

    public static boolean updateItemQuantity(int itemId, int quantityChange) {
        String sql = "UPDATE inventories SET quantity = quantity + ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantityChange);
            stmt.setInt(2, itemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la quantité de l'article : " + e.getMessage());
            return false;
        }
    }

    public static boolean createItem(String name, int quantity, double price, int storeId) {
        String sql = "INSERT INTO inventories (name, quantity, price, store_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.setInt(4, storeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'article : " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteItem(int itemId) {
        String sql = "DELETE FROM inventories WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'article : " + e.getMessage());
            return false;
        }
    }
}