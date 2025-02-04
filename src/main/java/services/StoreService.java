package services;

import database.DatabaseManager;
import models.Store;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreService {
    public static boolean createStore(String name, String location) {
        String sql = "INSERT INTO stores (name, location) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, location);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du magasin : " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteStore(int storeId) {
        String sql = "DELETE FROM stores WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du magasin : " + e.getMessage());
            return false;
        }
    }

    public static boolean addEmployeeToStore(int storeId, int userId) {
        String sql = "INSERT INTO store_users (store_id, user_id) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storeId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'employé au magasin : " + e.getMessage());
            return false;
        }
    }

    public static List<User> getEmployeesByStoreId(int storeId) {
        List<User> employees = new ArrayList<>();
        String sql = "SELECT u.id, u.email, u.pseudo, u.role FROM users u " +
                     "JOIN store_users se ON u.id = se.user_id WHERE se.store_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("pseudo"),
                        "****", // Mot de passe masqué
                        rs.getString("role")
                );
                employees.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des employés du magasin : " + e.getMessage());
        }
        return employees;

    }
}