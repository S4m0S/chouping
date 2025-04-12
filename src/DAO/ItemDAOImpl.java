package DAO;

import Modele.Item;
import java.sql.*;
import java.util.ArrayList;
import DAO.DaoFactory;


/**
 * Implémentation de l'interface ItemDAO pour la gestion des items dans la base de données.
 */
public class ItemDAOImpl implements ItemDAO {
    private DaoFactory daoFactory;

    public ItemDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Item> getAll() {
        ArrayList<Item> items = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery("SELECT * FROM items");

            while (resultats.next()) {
                Item item = new Item(
                    resultats.getInt("id_item"),
                    resultats.getString("nom"),
                    resultats.getInt("stock"),
                    resultats.getString("description"),
                    resultats.getDouble("prix"),
                    resultats.getInt("type"),
                    resultats.getInt("classe"),
                    resultats.getInt("couleur"),
                    resultats.getDouble("taille"),
                    resultats.getInt("matiere"),
                    resultats.getInt("solidite"),
                    resultats.getDouble("poids")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des items.");
        }

        return items;
    }

    @Override
    public void ajouter(Item item) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(
                     "INSERT INTO items (nom, stock, description, prix, type, classe, couleur, taille, matiere, solidite, poids) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, item.getNom());
            stmt.setInt(2, item.getStock());
            stmt.setString(3, item.getDescription());
            stmt.setDouble(4, item.getPrix());
            stmt.setInt(5, item.getType());
            stmt.setInt(6, item.getClasse());
            stmt.setInt(7, item.getCouleur());
            stmt.setDouble(8, item.getTaille());
            stmt.setInt(9, item.getMatiere());
            stmt.setInt(10, item.getSolidite());
            stmt.setDouble(11, item.getPoids());

            stmt.executeUpdate();
            System.out.println("Item ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'item.");
        }
    }

    @Override
    public Item chercher(int id) {
        Item item = null;

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery("SELECT * FROM items WHERE id_item = " + id);

            if (resultats.next()) {
                item = new Item(
                    resultats.getInt("id_item"),
                    resultats.getString("nom"),
                    resultats.getInt("stock"),
                    resultats.getString("description"),
                    resultats.getDouble("prix"),
                    resultats.getInt("type"),
                    resultats.getInt("classe"),
                    resultats.getInt("couleur"),
                    resultats.getDouble("taille"),
                    resultats.getInt("matiere"),
                    resultats.getInt("solidite"),
                    resultats.getDouble("poids")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche de l'item.");
        }

        return item;
    }

    @Override
    public Item modifier(Item item) {
        // Méthode à compléter si nécessaire
        return item;
    }

    @Override
    public void supprimer(Item item) {
        // Méthode à compléter si nécessaire
    }
}
