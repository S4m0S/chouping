package DAO;

import Modele.Article;
import java.sql.*;
import java.util.ArrayList;

public class articleDAO implements objectDao {

    private DaoFactory daoFactory;

    public articleDAO(DaoFactory daoFactory_p) {
        this.daoFactory = daoFactory_p;
    }

    @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> liste_articles = new ArrayList<Object>();

        try {
            Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultat = statement.executeQuery("SELECT * FROM article;");


            Connection connection1 = daoFactory.getConnection();
            Statement statement1 = connection1.createStatement();
            ResultSet resultat1 = statement1.executeQuery("SELECT * FROM caracteristiques;");

            while (resultat.next() && resultat1.next()) {


                int id_article = resultat.getInt("id_article");
                String nom = resultat.getString("nom");
                int stock = resultat.getInt("stock");
                String description = resultat.getString("description");
                double prix = resultat1.getDouble("prix");
                int type = resultat1.getInt("type");
                int classe = resultat1.getInt("classe");
                int couleur = resultat1.getInt("couleur");
                double taille = resultat1.getDouble("taille");
                int matiere = resultat1.getInt("matiere");
                int solidite = resultat1.getInt("solidite");
                double poids = resultat1.getDouble("poids");

                Article article = new Article(id_article, nom, stock, description, prix, type, classe, couleur, taille, matiere, solidite, poids);

                liste_articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des articles.");
        }

        return liste_articles;
    }

    @Override
    public int ajouter(Object object_p) {
        try {

            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO article(nom, stock, description) VALUES (?,?,?);",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ((Article) object_p).getNom());
            statement.setInt(2, ((Article) object_p).getStock());
            statement.setString(3, ((Article) object_p).getDescription());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            int generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating article failed, no ID obtained.");
            }




            statement = connection.prepareStatement(
                    "INSERT INTO caracteristiques(id_article, prix, type, classe, couleur, taille, matiere, solidite, poids) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?);"
            );

            statement.setInt(1,generatedId);
            statement.setDouble(2, ((Article) object_p).getPrix());
            statement.setInt(3, ((Article) object_p).getType());
            statement.setInt(4, ((Article) object_p).getClasse());
            statement.setInt(5, ((Article) object_p).getCouleur());
            statement.setDouble(6, ((Article) object_p).getTaille());
            statement.setInt(7, ((Article) object_p).getMatiere());
            statement.setInt(8, ((Article) object_p).getSolidite());
            statement.setDouble(9, ((Article) object_p).getPoids());

            statement.executeUpdate();

            return generatedId;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'article.");
            return -1;
        }
    }

    @Override
    public Object chercher(int object_id) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM article WHERE id_article=?;");

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

                Article article = new Article(object_id, nom, stock, description, prix, type, classe, couleur, taille, matiere, solidite, poids);

                return article;
            } else {
                System.out.println("article non trouvé avec cet ID.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche de l'article.");
            return null;
        }
    }

    @Override
    public void modifier(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE article SET nom=?, stock=?, description=? WHERE id_article=?;"
            );

            statement.setString(1, ((Article) object_p).getNom());
            statement.setInt(2, ((Article) object_p).getStock());
            statement.setString(3, ((Article) object_p).getDescription());
            statement.setInt(4,(((Article) object_p).getId_article()));

            statement.executeUpdate();




            statement = connection.prepareStatement(
                    "UPDATE caracteristiques SET prix=?, type=?, classe=?, couleur=?, taille=?, matiere=?, solidite=?, poids=? WHERE id_article=?;"
            );


            statement.setDouble(1, ((Article) object_p).getPrix());
            statement.setInt(2, ((Article) object_p).getType());
            statement.setInt(3, ((Article) object_p).getClasse());
            statement.setInt(4, ((Article) object_p).getCouleur());
            statement.setDouble(5, ((Article) object_p).getTaille());
            statement.setInt(6, ((Article) object_p).getMatiere());
            statement.setInt(7, ((Article) object_p).getSolidite());
            statement.setDouble(8, ((Article) object_p).getPoids());
            statement.setInt(9, ((Article) object_p).getId_article());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de l'article.");
        }
    }

    @Override
    public void supprimer(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM article WHERE id_article=?;");
            statement.setInt(1, ((Article) object_p).getId_article());

            statement.executeUpdate();


            statement = connection.prepareStatement("DELETE FROM caracteristiques WHERE id_article=?;");
            statement.setInt(1, ((Article) object_p).getId_article());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'article.");
        }
    }
}
