package database;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

        public class DatabaseManager {
            private static final String URL = "jdbc:mysql://localhost:3306/dbjavall?serverTimezone=UTC";
            private static final String USER = "root"; // Remplace par ton utilisateur MySQL
            private static final String PASSWORD = ""; // Remplace par ton mot de passe MySQL

            private static Connection connection;

            // Méthode pour établir la connexion
            public static Connection getConnection() {
                if (connection == null || isConnectionClosed()) {
                    try {
                        // Charger le driver JDBC MySQL
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        // Établir la connexion
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        System.out.println("Connexion à la base de données réussie !");
                    } catch (ClassNotFoundException e) {
                        System.err.println("Driver JDBC non trouvé : " + e.getMessage());
                    } catch (SQLException e) {
                        System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
                    }
                }
                return connection;
            }

            // Méthode pour vérifier si la connexion est fermée
            private static boolean isConnectionClosed() {
                try {
                    return connection == null || connection.isClosed();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la vérification de l'état de la connexion : " + e.getMessage());
                    return true;
                }
            }

            // Méthode pour fermer la connexion

        }