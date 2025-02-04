import models.User;
import services.UserService;
import auth.AuthService;
import services.StoreService;
import services.InventoryService;
import models.Inventory;
import models.Item;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // MainApp.main(args);

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
            System.out.println("7. Supprimer un magasin");
            System.out.println("8. Ajouter un employé à un magasin");
            System.out.println("9. Afficher les employés d'un magasin");
            System.out.println("10. Créer un article");
            System.out.println("11. Supprimer un article");
            System.out.println("12. Consulter l'inventaire");
            System.out.println("13. Mettre à jour la quantité d'un article");
            System.out.println("14. Quitter");
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
                    System.out.print("Entrez votre ID : ");
                    int whitelistRequesterId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    if (UserService.isAdmin(whitelistRequesterId)) {
                        System.out.print("Entrez l'email à whitelister : ");
                        String whitelistEmail = scanner.nextLine();
                        boolean whitelisted = AuthService.whitelistEmail(whitelistEmail);
                        System.out.println(whitelisted ? "Email whitelisted avec succès." : "Échec du whitelisting.");
                    } else {
                        System.out.println("Erreur : Seuls les administrateurs peuvent whitelister des emails.");
                    }
                    break;
                case 6:
                    System.out.print("Entrez votre ID : ");
                    int storeRequesterId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    if (UserService.isAdmin(storeRequesterId)) {
                        System.out.print("Entrez le nom du magasin : ");
                        String storeName = scanner.nextLine();
                        System.out.print("Entrez l'emplacement du magasin : ");
                        String storeLocation = scanner.nextLine();
                        boolean storeCreated = StoreService.createStore(storeName, storeLocation);
                        System.out.println(storeCreated ? "Magasin créé avec succès." : "Échec de la création du magasin.");
                    } else {
                        System.out.println("Erreur : Seuls les administrateurs peuvent créer des magasins.");
                    }
                    break;
                case 7:
                    System.out.print("Entrez votre ID : ");
                    int deleteStoreRequesterId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    if (UserService.isAdmin(deleteStoreRequesterId)) {
                        System.out.print("Entrez l'ID du magasin à supprimer : ");
                        int storeId = scanner.nextInt();
                        boolean storeDeleted = StoreService.deleteStore(storeId);
                        System.out.println(storeDeleted ? "Magasin supprimé avec succès." : "Échec de la suppression du magasin.");
                    } else {
                        System.out.println("Erreur : Seuls les administrateurs peuvent supprimer des magasins.");
                    }
                    break;
                case 8:
                    System.out.print("Entrez votre ID : ");
                    int addEmployeeRequesterId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    if (UserService.isAdmin(addEmployeeRequesterId)) {
                        System.out.print("Entrez l'ID du magasin : ");
                        int storeId = scanner.nextInt();
                        System.out.print("Entrez l'ID de l'employé : ");
                        int employeeId = scanner.nextInt();
                        boolean employeeAdded = StoreService.addEmployeeToStore(storeId, employeeId);
                        System.out.println(employeeAdded ? "Employé ajouté avec succès." : "Échec de l'ajout de l'employé.");
                    } else {
                        System.out.println("Erreur : Seuls les administrateurs peuvent ajouter des employés.");
                    }
                    break;
                case 9:
                    System.out.print("Entrez l'ID du magasin : ");
                    int storeId = scanner.nextInt();
                    List<User> employees = StoreService.getEmployeesByStoreId(storeId);
                    System.out.println("Employés du magasin " + storeId + " :");
                    for (User employee : employees) {
                        System.out.println(employee);
                    }
                    break;
                case 10:
                    System.out.print("Entrez votre ID : ");
                    int itemRequesterId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    if (UserService.isAdmin(itemRequesterId)) {
                        System.out.print("Entrez le nom de l'article : ");
                        String itemName = scanner.nextLine();
                        System.out.print("Entrez la quantité de l'article : ");
                        int itemQuantity = scanner.nextInt();
                        System.out.print("Entrez le prix de l'article : ");
                        double itemPrice = scanner.nextDouble();
                        System.out.print("Entrez l'ID du magasin : ");
                        int storeIdForItem = scanner.nextInt();
                        boolean itemCreated = InventoryService.createItem(itemName, itemQuantity, itemPrice, storeIdForItem);
                        System.out.println(itemCreated ? "Article créé avec succès." : "Échec de la création de l'article.");
                    } else {
                        System.out.println("Erreur : Seuls les administrateurs peuvent créer des articles.");
                    }
                    break;
                case 11:
                    System.out.print("Entrez votre ID : ");
                    int deleteItemRequesterId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    if (UserService.isAdmin(deleteItemRequesterId)) {
                        System.out.print("Entrez l'ID de l'article à supprimer : ");
                        int itemId = scanner.nextInt();
                        boolean itemDeleted = InventoryService.deleteItem(itemId);
                        System.out.println(itemDeleted ? "Article supprimé avec succès." : "Échec de la suppression de l'article.");
                    } else {
                        System.out.println("Erreur : Seuls les administrateurs peuvent supprimer des articles.");
                    }
                    break;
                case 12:
                    System.out.print("Entrez l'ID du magasin : ");
                    int inventoryStoreId = scanner.nextInt();
                    Inventory inventory = InventoryService.getInventoryByStoreId(inventoryStoreId);
                    System.out.println("Inventaire du magasin " + inventoryStoreId + " :");
                    for (Item item : inventory.getItems()) {
                        System.out.println(item);
                    }
                    break;
                case 13:
                    System.out.print("Entrez l'ID de l'article : ");
                    int updateItemId = scanner.nextInt();
                    System.out.print("Entrez la quantité à ajouter (ou à soustraire) : ");
                    int quantityChange = scanner.nextInt();
                    boolean quantityUpdated = InventoryService.updateItemQuantity(updateItemId, quantityChange);
                    System.out.println(quantityUpdated ? "Quantité mise à jour avec succès." : "Échec de la mise à jour de la quantité.");
                    break;
                case 14:
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