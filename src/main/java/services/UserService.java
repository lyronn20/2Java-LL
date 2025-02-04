package services;

import database.DatabaseManager;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    // Récupérer un utilisateur par ID (tous les utilisateurs peuvent voir les autres, sauf le mot de passe)
    public static User getUserById(int id) {
        String sql = "SELECT id, email, pseudo, role FROM users WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("pseudo"),
                        "****", // Mot de passe masqué
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return null;
    }

    // Mettre à jour un utilisateur (uniquement lui-même sauf ADMIN)
public static boolean updateUser(int userId, int requesterId, String newPseudo, String newPassword, String role) {
    if (userId != requesterId && !isAdmin(requesterId)) {
        System.err.println("Erreur : Vous ne pouvez mettre à jour que votre propre compte.");
        return false;
    }

    String sql = "UPDATE users SET pseudo = ?, password = ?, role = ? WHERE id = ?";
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, newPseudo);
        stmt.setString(2, newPassword);
        stmt.setString(3, role);
        stmt.setInt(4, userId);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        return false;
    }
}

public static boolean deleteUser(int userId, int requesterId) {
    if (userId != requesterId && !isAdmin(requesterId)) {
        System.err.println("Erreur : Vous ne pouvez supprimer que votre propre compte.");
        return false;
    }

    String sql = "DELETE FROM users WHERE id = ?";
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        return false;
    }
}

    // Vérifier si un utilisateur est ADMIN
    public static boolean isAdmin(int userId) {
        String sql = "SELECT role FROM users WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "ADMIN".equals(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du rôle : " + e.getMessage());
        }
        return false;
    }
}
