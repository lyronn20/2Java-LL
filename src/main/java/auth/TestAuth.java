package auth;

import models.User;

public class TestAuth {
    public static void main(String[] args) {
        // 🔹 Test de l'inscription
        boolean isRegistered = AuthService.registerUser("test@example.com", "TestUser", "password123");
        if (isRegistered) {
            System.out.println("Utilisateur inscrit avec succès !");
        } else {
            System.out.println("Échec de l'inscription.");
        }

        // 🔹 Test de la connexion
        User user = AuthService.loginUser("test@example.com", "password123");
        if (user != null) {
            System.out.println("Connexion réussie : " + user);
        } else {
            System.out.println("Échec de la connexion.");
        }
    }
}
