import models.User;
import services.UserService;
import auth.AuthService;
import services.StoreService;
import services.InventoryService;

import java.util.Scanner;

public class Main {
    private static int currentStoreId = 1; // Initialiser avec le premier storeId

    public static void main(String[] args) {
        AuthService.createDefaultAdmin();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu Utilisateur ===");
            System.out.println("1. Voir un utilisateur");
            System.out.println("2. Créer un utilisateur");
            System.out.println("3. Mettre à jour un utilisateur");
            System.out.println("4. Supprimer un utilisateur");
            System.out.println("5. Whitelister un email");
            System.out.println("6. Créer un magasin");
            System.out.println("7. Créer un article");
            System.out.println("8. Supprimer un article");
            System.out.println("9. Quitter");
            System.out.print("Choisissez une option : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choice) {
                case 1:
                    System.out.print("Entrez l'ID de l'utilisateur à afficher : ");
                    int userId = scanner.nextInt();
                    User user = UserService.getUserById(userId);
                    if (user != null) {
                        System.out.println("Utilisateur trouvé : " + user);
                    } else {
                        System.out.println("Utilisateur non trouvé.");
                    }
                    break;
                case 2:
                    System.out.print("Entrez votre e-mail : ");
                    String email = scanner.nextLine();
                    System.out.print("Entrez votre pseudo : ");
                    String pseudo = scanner.nextLine();
                    System.out.print("Entrez votre mot de passe : ");
                    String password = scanner.nextLine();
                    System.out.print("Entrez le rôle (ADMIN ou EMPLOYE) : ");
                    String role = scanner.nextLine().toUpperCase();
                    boolean created = AuthService.registerUser(email, pseudo, password, role);
                    System.out.println(created ? "Utilisateur créé avec succès." : "Échec de la création de l'utilisateur.");
                    break;
                case 3:
                    System.out.print("Entrez votre ID : ");
                    int requesterId = scanner.nextInt();
                    System.out.print("Entrez l'ID de l'utilisateur à modifier : ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    System.out.print("Nouveau pseudo : ");
                    String newPseudo = scanner.nextLine();
                    System.out.print("Nouveau mot de passe : ");
                    String newPassword = scanner.nextLine();
                    boolean updated = UserService.updateUser(updateId, requesterId, newPseudo, newPassword, "EMPLOYE");
                    System.out.println(updated ? "Mise à jour réussie." : "Échec de la mise à jour.");
                    break;
                case 4:
                    System.out.print("Entrez votre ID : ");
                    int deleterId = scanner.nextInt();
                    System.out.print("Entrez l'ID de l'utilisateur à supprimer : ");
                    int deleteId = scanner.nextInt();
                    boolean deleted = UserService.deleteUser(deleteId, deleterId);
                    System.out.println(deleted ? "Suppression réussie." : "Échec de la suppression.");
                    break;
                case 5:
                    System.out.print("Entrez l'email à whitelister : ");
                    String whitelistEmail = scanner.nextLine();
                    boolean whitelisted = AuthService.whitelistEmail(whitelistEmail);
                    System.out.println(whitelisted ? "Email whitelisted avec succès." : "Échec du whitelisting.");
                    break;
                case 6:
                    System.out.print("Entrez le nom du magasin : ");
                    String storeName = scanner.nextLine();
                    System.out.print("Entrez l'emplacement du magasin : ");
                    String storeLocation = scanner.nextLine();
                    boolean storeCreated = StoreService.createStore(storeName, storeLocation);
                    System.out.println(storeCreated ? "Magasin créé avec succès." : "Échec de la création du magasin.");
                    break;
                case 7:
                    System.out.print("Entrez le nom de l'article : ");
                    String itemName = scanner.nextLine();
                    System.out.print("Entrez la quantité de l'article : ");
                    int itemQuantity = scanner.nextInt();
                    System.out.print("Entrez le prix de l'article : ");
                    double itemPrice = scanner.nextDouble();
                    boolean itemCreated = InventoryService.createItem(itemName, itemQuantity, itemPrice, currentStoreId);
                    System.out.println(itemCreated ? "Article créé avec succès." : "Échec de la création de l'article.");
                    currentStoreId++; // Incrémenter le storeId après chaque création d'article
                    break;
                case 8:
                    System.out.print("Entrez l'ID de l'article à supprimer : ");
                    int itemId = scanner.nextInt();
                    boolean itemDeleted = InventoryService.deleteItem(itemId);
                    System.out.println(itemDeleted ? "Article supprimé avec succès." : "Échec de la suppression de l'article.");
                    break;
                case 9:
                    System.out.println("Fermeture du programme.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option invalide, veuillez réessayer.");
            }
        }
    }
}