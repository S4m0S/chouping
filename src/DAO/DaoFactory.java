package DAO;
import java.sql.*;

/**
 * DaoFactory - Classe responsable de l'initialisation des connexions à la base de données
 * et de la fourniture des différentes implémentations DAO.
 * Cette classe suit le patron de conception Factory et Singleton pour centraliser
 * la création des objets DAO et la gestion des connexions.
 */
public class DaoFactory {
    // Configuration de la connexion JDBC
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL_PREFIX = "jdbc:mysql://localhost:3307/";

    private final String url;
    private final String username;
    private final String password;

    // Instance unique (pattern Singleton)
    private static DaoFactory instance;

    /**
     * Constructeur privé pour empêcher l'instanciation directe (pattern Singleton)
     *
     * @param url URL de connexion à la base de données
     * @param username Nom d'utilisateur pour la connexion
     * @param password Mot de passe pour la connexion
     */
    private DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Obtient l'instance unique de DaoFactory (pattern Singleton)
     * Initialise également le pilote JDBC
     *
     * @param database Nom de la base de données
     * @param username Nom d'utilisateur pour la connexion
     * @param password Mot de passe pour la connexion
     * @return Instance unique de DaoFactory
     * @throws RuntimeException Si le chargement du driver échoue
     */
    public static synchronized DaoFactory getInstance(String database, String username, String password) {
        if (instance == null) {
            try {
                // Chargement du driver JDBC
                Class.forName(DRIVER);

                // Construction de l'URL complète
                String completeUrl = URL_PREFIX + database;

                // Création de l'instance
                instance = new DaoFactory(completeUrl, username, password);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Erreur de chargement du driver JDBC: " + e.getMessage(), e);
            }
        }
        return instance;
    }

    /**
     * Établit et retourne une connexion à la base de données
     *
     * @return Objet Connection permettant d'interagir avec la base de données
     * @throws SQLException Si la connexion échoue
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Ferme proprement une connexion, un statement et un resultset
     *
     * @param connection Connexion à fermer
     * @param statement Statement à fermer
     * @param resultSet ResultSet à fermer
     */
    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
        }
    }
    /**
     * Ferme la connexion à la base de données
     * Méthode conservée pour compatibilité avec le code existant
     */
    public void disconnect() {
        Connection connexion = null;
        try {
            connexion = this.getConnection();
            closeResources(connexion, null, null);
            System.out.println("Déconnexion réussie de la base de données");
        } catch (SQLException e) {
            System.err.println("Erreur de déconnexion à la base de données: " + e.getMessage());
        }
    }

    /**
     * Récupère l'implémentation DAO pour les produits
     *
     * @return Instance de ProduitDAO
     */


    /**
     * Récupère l'implémentation DAO pour les clients
     *
     * @return Instance de ClientDAO
     */
    public objectDao getClientDAO() {
        return new clientDAO(this);
    }



    /**
     * Récupère l'implémentation DAO pour les commandes
     *
     * @return Instance de CommanderDAO
     */

}