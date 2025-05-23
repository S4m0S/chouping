package DAO;

import Modele.Article;
import Modele.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * DAO (Data Access Object) pour gérer les opérations sur la table 'commande'.
 * Permet d'ajouter, chercher, modifier, supprimer et récupérer toutes les commandes.
 */

public class commandeDAO implements objectDao {
    private DaoFactory daoFactory;

    /**
     * Constructeur du DAO pour commande.
     *
     * @param daoFactory_p l'usine à connexions pour accéder à la base de données
     */


    public commandeDAO(DaoFactory daoFactory_p) {
        this.daoFactory = daoFactory_p;
    }

    /**
     * Récupère toutes les commandes présentes dans la base de données.
     *
     * @return une liste d'objets de type Commande
     */

    @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> liste_Commandes = new ArrayList<>();

        try {
            Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultat = statement.executeQuery("SELECT * FROM commande;");

            while (resultat.next()) {
                int id_commande = resultat.getInt("id_commande");
                int id_compte = resultat.getInt("id_compte");
                Date date_achat = resultat.getDate("date");

                Commande commande = new Commande(id_commande, id_compte, date_achat);
                commande.setItems(recupererItemsCommande(connection, id_commande));

                liste_Commandes.add(commande);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des commandes.");
        }

        return liste_Commandes;
    }

    /**
     * Ajoute une commande dans la base de données.
     *
     * @param object_p la commande à ajouter
     * @return l'identifiant de la commande générée, ou -1 en cas d'erreur
     */
    @Override
    public int ajouter(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO commande(id_compte, date) VALUES (?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            Commande commande = (Commande) object_p;
            statement.setInt(1, commande.getId_compte());



            Timestamp timestamp = Timestamp.from(Instant.now());
            statement.setTimestamp(2, timestamp);


            statement.executeUpdate();

            // Récupérer l'ID généré
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id_commande_genere = generatedKeys.getInt(1);
                commande.setId_commande(id_commande_genere);

                // Ajouter les items à la table de jointure
                ajouterItemsCommande(connection, id_commande_genere, commande.getItems(), commande.getNb_items());
                return  id_commande_genere;
            }

            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout d'une commande.");
            return -1;
        }
    }

    /**
     * Cherche une commande spécifique par son identifiant.
     *
     * @param object_id l'identifiant de la commande
     * @return l'objet Commande trouvé, ou null si non trouvé
     */
    @Override
    public Object chercher(int object_id) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM commande WHERE id_commande = ?;"
            );
            statement.setInt(1, object_id);

            ResultSet resultat = statement.executeQuery();

            if (resultat.next()) {
                int id_compte = resultat.getInt("id_compte");
                Date date_achat = resultat.getDate("date_achat");

                Commande commande = new Commande(object_id, id_compte, date_achat);
                commande.setItems(recupererItemsCommande(connection, object_id));
                return commande;
            } else {
                System.out.println("Aucune commande trouvée.");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche de commande.");
            return null;
        }
    }

    /**
     * Modifie une commande existante dans la base de données.
     *
     * @param object_p la commande modifiée
     */
    @Override
    public void modifier(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            Commande commande = (Commande) object_p;

            // Modifier la commande de base
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE commande SET id_compte=?, date=? WHERE id_commande=?;"
            );

            statement.setInt(1, commande.getId_compte());
            statement.setDate(2, commande.getDate_achat());
            statement.setInt(3, commande.getId_commande());
            statement.executeUpdate();

            // Supprimer les anciens items et ajouter les nouveaux
            PreparedStatement deleteItems = connection.prepareStatement(
                    "DELETE FROM commande_articles WHERE id_commande=?;"
            );
            deleteItems.setInt(1, commande.getId_commande());
            deleteItems.executeUpdate();

            ajouterItemsCommande(connection, commande.getId_commande(), commande.getItems(), commande.getNb_items());

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de la commande.");
        }
    }

    /**
     * Supprime une commande (et ses articles associés) de la base de données.
     *
     * @param object_p la commande à supprimer
     */
    @Override
    public void supprimer(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            Commande commande = (Commande) object_p;

            // Supprimer d'abord les items liés à la commande
            PreparedStatement deleteItems = connection.prepareStatement(
                    "DELETE FROM commande_articles WHERE id_commande=?;"
            );
            deleteItems.setInt(1, commande.getId_commande());
            deleteItems.executeUpdate();

            // Ensuite supprimer la commande
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM commande WHERE id_commande=?;"
            );
            statement.setInt(1, commande.getId_commande());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de la commande.");
        }
    }

    // Méthodes utilitaires
    /**
     * Récupère les identifiants des articles associés à une commande.
     *
     * @param connection la connexion à la base de données
     * @param id_commande l'identifiant de la commande
     * @return un tableau d'identifiants d'articles
     * @throws SQLException en cas de problème d'accès aux données
     */
    private int[] recupererItemsCommande(Connection connection, int id_commande) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id_article FROM commande_articles WHERE id_commande=?;"
        );
        statement.setInt(1, id_commande);

        ResultSet resultat = statement.executeQuery();

        ArrayList<Integer> liste = new ArrayList<>();
        while (resultat.next()) {
            liste.add(resultat.getInt("id_article"));
        }

        // Conversion en tableau
        return liste.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Ajoute les articles associés à une commande dans la table de liaison.
     *
     * @param connection la connexion à la base de données
     * @param id_commande l'identifiant de la commande
     * @param items les identifiants des articles
     * @param nb_items les quantités associées à chaque article
     * @throws SQLException en cas de problème d'accès aux données
     */
    private void ajouterItemsCommande(Connection connection, int id_commande, int[] items, int[] nb_items) throws SQLException {
        if (items == null) return;

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO commande_articles(id_commande, id_article,quantite) VALUES (?, ?, ?);"
        );

        for (int i = 0; i < items.length; i++) {
            statement.setInt(1, id_commande);
            statement.setInt(2, items[i]);
            statement.setInt(3, nb_items[i]);
            statement.addBatch();
        }

        statement.executeBatch();
    }

    /**
     * Récupère les détails des articles associés à une commande spécifique.
     *
     * @param id_commande l'identifiant de la commande
     * @return une liste d'articles associés à la commande
     */
    public ArrayList<Article> getItemsDetailsCommande(int id_commande) {
        ArrayList<Article> items = new ArrayList<>();

        String sql = "SELECT i.*, c.* FROM article i " +
                "JOIN commande_articles ca ON i.id_article = ca.id_article " +
                "JOIN caracteristiques c ON i.id_article = c.id_article " +
                "WHERE ca.id_commande = ?";



        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id_commande);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Article item = new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getInt("type"),
                        rs.getInt("classe"),
                        rs.getInt("couleur"),
                        rs.getDouble("taille"),
                        rs.getInt("matiere"),
                        rs.getInt("solidite"),
                        rs.getDouble("poids")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des détails des articles");
        }

        return items;
    }
}
