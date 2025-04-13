package DAO;

import Modele.Item;
import java.sql.*;
import java.util.ArrayList;

public class itemDAO implements objectDao {

    private DaoFactory daoFactory;

    public itemDAO(DaoFactory daoFactory_p) {
        this.daoFactory = daoFactory_p;
    }

    @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> liste_Items = new ArrayList<Object>();

        try {
            Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultat = statement.executeQuery("SELECT * FROM item;");

            while (resultat.next()) {
                int id_item = resultat.getInt(1);
                String nom = resultat.getString(2);
                int stock = resultat.getInt(3);
                String description = resultat.getString(4);
                double prix = resultat.getDouble(5);
                int type = resultat.getInt(6);
                int classe = resultat.getInt(7);
                int couleur = resultat.getInt(8);
                double taille = resultat.getDouble(9);
                int matiere = resultat.getInt(10);
                int solidite = resultat.getInt(11);
                double poids = resultat.getDouble(12);

                Item item = new Item(id_item, nom, stock, description, prix, type, classe, couleur, taille, matiere, solidite, poids);

                liste_Items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des items.");
        }

        return liste_Items;
    }

    @Override
    public void ajouter(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO item(nom, stock, description, prix, type, classe, couleur, taille, matiere, solidite, poids) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );

            statement.setString(1, ((Item) object_p).getNom());
            statement.setInt(2, ((Item) object_p).getStock());
            statement.setString(3, ((Item) object_p).getDescription());
            statement.setDouble(4, ((Item) object_p).getPrix());
            statement.setInt(5, ((Item) object_p).getType());
            statement.setInt(6, ((Item) object_p).getClasse());
            statement.setInt(7, ((Item) object_p).getCouleur());
            statement.setDouble(8, ((Item) object_p).getTaille());
            statement.setInt(9, ((Item) object_p).getMatiere());
            statement.setInt(10, ((Item) object_p).getSolidite());
            statement.setDouble(11, ((Item) object_p).getPoids());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'item.");
        }
    }

    @Override
    public Object chercher(int object_id) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE id_item=?;");

            statement.setInt(1, object_id);

            ResultSet resultat = statement.executeQuery();

            if (resultat.next()) {
                String nom = resultat.getString(2);
                int stock = resultat.getInt(3);
                String description = resultat.getString(4);
                double prix = resultat.getDouble(5);
                int type = resultat.getInt(6);
                int classe = resultat.getInt(7);
                int couleur = resultat.getInt(8);
                double taille = resultat.getDouble(9);
                int matiere = resultat.getInt(10);
                int solidite = resultat.getInt(11);
                double poids = resultat.getDouble(12);

                Item item = new Item(object_id, nom, stock, description, prix, type, classe, couleur, taille, matiere, solidite, poids);

                return item;
            } else {
                System.out.println("Item non trouvé avec cet ID.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche de l'item.");
            return null;
        }
    }

    @Override
    public void modifier(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE item SET nom=?, stock=?, description=?, prix=?, type=?, classe=?, couleur=?, taille=?, matiere=?, solidite=?, poids=? WHERE id_item=?;"
            );

            statement.setString(1, ((Item) object_p).getNom());
            statement.setInt(2, ((Item) object_p).getStock());
            statement.setString(3, ((Item) object_p).getDescription());
            statement.setDouble(4, ((Item) object_p).getPrix());
            statement.setInt(5, ((Item) object_p).getType());
            statement.setInt(6, ((Item) object_p).getClasse());
            statement.setInt(7, ((Item) object_p).getCouleur());
            statement.setDouble(8, ((Item) object_p).getTaille());
            statement.setInt(9, ((Item) object_p).getMatiere());
            statement.setInt(10, ((Item) object_p).getSolidite());
            statement.setDouble(11, ((Item) object_p).getPoids());
            statement.setInt(12, ((Item) object_p).getId_item());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de l'item.");
        }
    }

    @Override
    public void supprimer(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM item WHERE id_item=?;");
            statement.setInt(1, ((Item) object_p).getId_item());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'item.");
        }
    }
}
