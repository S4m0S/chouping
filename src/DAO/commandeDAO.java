package DAO;

import Modele.Article;
import Modele.Commande;

import java.sql.*;
import java.util.ArrayList;

public class commandeDAO implements objectDao {
    private DaoFactory daoFactory;

    public commandeDAO(DaoFactory daoFactory_p) {
        this.daoFactory = daoFactory_p;
    }

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
                Date date_achat = resultat.getDate("date_achat");

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

    @Override
    public void ajouter(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO commande(id_compte, date_achat) VALUES (?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            Commande commande = (Commande) object_p;
            statement.setInt(1, commande.getId_compte());
            statement.setDate(2, commande.getDate_achat());

            statement.executeUpdate();

            // Récupérer l'ID généré
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id_commande_genere = generatedKeys.getInt(1);
                commande.setId_commande(id_commande_genere);

                // Ajouter les items à la table de jointure
                ajouterItemsCommande(connection, id_commande_genere, commande.getItems());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout d'une commande.");
        }
    }

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

    @Override
    public void modifier(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            Commande commande = (Commande) object_p;

            // Modifier la commande de base
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE commande SET id_compte=?, date_achat=? WHERE id_commande=?;"
            );

            statement.setInt(1, commande.getId_compte());
            statement.setDate(2, commande.getDate_achat());
            statement.setInt(3, commande.getId_commande());
            statement.executeUpdate();

            // Supprimer les anciens items et ajouter les nouveaux
            PreparedStatement deleteItems = connection.prepareStatement(
                    "DELETE FROM commande_item WHERE id_commande=?;"
            );
            deleteItems.setInt(1, commande.getId_commande());
            deleteItems.executeUpdate();

            ajouterItemsCommande(connection, commande.getId_commande(), commande.getItems());

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de la commande.");
        }
    }

    @Override
    public void supprimer(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            Commande commande = (Commande) object_p;

            // Supprimer d'abord les items liés à la commande
            PreparedStatement deleteItems = connection.prepareStatement(
                    "DELETE FROM commande_item WHERE id_commande=?;"
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

    private int[] recupererItemsCommande(Connection connection, int id_commande) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id_item FROM commande_item WHERE id_commande=?;"
        );
        statement.setInt(1, id_commande);

        ResultSet resultat = statement.executeQuery();

        ArrayList<Integer> liste = new ArrayList<>();
        while (resultat.next()) {
            liste.add(resultat.getInt("id_item"));
        }

        // Conversion en tableau
        return liste.stream().mapToInt(Integer::intValue).toArray();
    }

    private void ajouterItemsCommande(Connection connection, int id_commande, int[] items) throws SQLException {
        if (items == null) return;

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO commande_item(id_commande, id_item) VALUES (?, ?);"
        );

        for (int id_item : items) {
            statement.setInt(1, id_commande);
            statement.setInt(2, id_item);
            statement.addBatch();
        }

        statement.executeBatch();
    }

    public ArrayList<Article> getItemsDetailsCommande(int id_commande) {
        ArrayList<Article> items = new ArrayList<>();

        String sql = "SELECT i.* FROM item i " +
                "JOIN commande_articles ca ON i.id_item = ca.id_article " +
                "WHERE ca.id_commande = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id_commande);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Article item = new Article(
                        rs.getInt("id_item"),
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
