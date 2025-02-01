package auth;

            import database.DatabaseManager;
            import models.User;
            import org.mindrot.jbcrypt.BCrypt;

            import java.sql.Connection;
            import java.sql.PreparedStatement;
            import java.sql.ResultSet;
            import java.sql.SQLException;

            public class AuthService {

                // Vérifie si l'adresse e-mail est whitelistée
                private static boolean isEmailWhitelisted(String email) {
                    String sql = "SELECT COUNT(*) FROM whitelist WHERE email = ?";
                    try (Connection conn = DatabaseManager.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, email);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            return rs.getInt(1) > 0;
                        }
                    } catch (SQLException e) {
                        System.err.println("Erreur lors de la vérification de la whitelist : " + e.getMessage());
                    }
                    return false;
                }

                // Inscription d'un utilisateur
                public static boolean registerUser(String email, String pseudo, String password) {
                    if (!isEmailWhitelisted(email)) {
                        System.err.println("Erreur : L'adresse e-mail n'est pas whitelistée.");
                        return false;
                    }

                    String hashedPassword = hashPassword(password);
                    String sql = "INSERT INTO users (email, pseudo, password, role) VALUES (?, ?, ?, 'EMPLOYE')";

                    try (Connection conn = DatabaseManager.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, email);
                        stmt.setString(2, pseudo);
                        stmt.setString(3, hashedPassword);
                        int rowsInserted = stmt.executeUpdate();
                        return rowsInserted > 0;
                    } catch (SQLException e) {
                        System.err.println("Erreur lors de l'inscription : " + e.getMessage());
                        return false;
                    }
                }

                // Connexion d'un utilisateur
                public static User loginUser(String email, String password) {
                    String sql = "SELECT * FROM users WHERE email = ?";

                    try (Connection conn = DatabaseManager.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, email);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            String hashedPassword = rs.getString("password");
                            if (checkPassword(password, hashedPassword)) {
                                return new User(
                                        rs.getInt("id"),
                                        rs.getString("email"),
                                        rs.getString("pseudo"),
                                        rs.getString("password"),
                                        rs.getString("role")
                                );
                            } else {
                                System.err.println("Mot de passe incorrect.");
                            }
                        } else {
                            System.err.println("Utilisateur non trouvé.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Erreur lors de la connexion : " + e.getMessage());
                    }
                    return null;
                }

                // Hachage sécurisé du mot de passe
                private static String hashPassword(String password) {
                    return BCrypt.hashpw(password, BCrypt.gensalt());
                }

                // Vérification du mot de passe haché
                private static boolean checkPassword(String password, String hashedPassword) {
                    return BCrypt.checkpw(password, hashedPassword);
                }
            }