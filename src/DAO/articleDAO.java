package DAO;

    import Modele.Article;

    import java.awt.*;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Map;

/**
 * DAO (Data Access Object) pour gérer les opérations liées aux articles.
 */
public class articleDAO implements objectDao {

    private DaoFactory daoFactory;

    /**
     * Constructeur pour initialiser la factory DAO.
     * @param daoFactory_p la factory de connexion à la base de données
     */
    public articleDAO(DaoFactory daoFactory_p) {
        this.daoFactory = daoFactory_p;
    }

    /**
     * Récupère tous les articles de la base de données.
     * @return une liste d'objets Article
     */
    @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> liste_articles = new ArrayList<Object>();

            try {
                Connection connection = daoFactory.getConnection();


                Statement statement = connection.createStatement();
                // Get all articles and their characteristics
                ResultSet resultat = statement.executeQuery("SELECT * FROM article;");

                Statement statement1 = connection.createStatement();
                ResultSet resultat1 = statement1.executeQuery("SELECT * FROM caracteristiques;");

                // Also get pack information
                Statement packStatement = connection.createStatement();
                ResultSet packResult = packStatement.executeQuery("SELECT * FROM pack;");

                // Create a map of article IDs to their pack IDs for quick lookup
                Map<Integer, Integer> articleToPackMap = new HashMap<>();
                while (packResult.next()) {
                    int articleId = packResult.getInt("id_article");
                    int packId = packResult.getInt("id_pack");
                    articleToPackMap.put(articleId, packId);
                }

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
                    int promo = resultat.getInt("promo");

                    // Get pack ID from the map (default to 0 if not found)
                    int id_pack = articleToPackMap.getOrDefault(id_article, 0);

                    Article article = new Article(id_article, nom, stock, description, prix, type, classe, couleur, taille, matiere, solidite, poids);
                    article.setPromo(promo);
                    article.setPack(id_pack); // Set the pack ID

                    liste_articles.add(article);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la récupération des articles.");
            }

            return liste_articles;
        }

    /**
     * Ajoute un nouvel article dans la base de données.
     * @param object_p l'objet Article à ajouter
     * @return l'identifiant généré de l'article ajouté, ou -1 en cas d'erreur
     */
    @Override
    public int ajouter(Object object_p) {
        try {

            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO article(nom, stock, description) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
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

    /**
     * Cherche un article dans la base de données par son identifiant.
     * @param object_id l'identifiant de l'article
     * @return l'objet Article correspondant, ou null si introuvable
     */
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

    /**
     * Modifie un article existant dans la base de données.
     * @param object_p l'objet Article modifié
     */
    @Override
    public void modifier(Object object_p) {
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE article SET nom=?, stock=?, description=?, promo=? WHERE id_article=?;"
            );

                statement.setString(1, ((Article) object_p).getNom());
                statement.setInt(2, ((Article) object_p).getStock());
                statement.setString(3, ((Article) object_p).getDescription());
                statement.setInt(4,(((Article) object_p).getId_article()));
                statement.setInt(5,((Article) object_p).getPromo());

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

                if (((Article) object_p).getPack()!=0){
                    System.out.println("HEY");
                    statement = connection.prepareStatement("SELECT * FROM pack where id_article=?;");
                    statement.setInt(1,((Article) object_p).getId_article());

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()){
                        System.out.println("HEY");
                        statement = connection.prepareStatement("UPDATE pack SET id_pack=? WHERE id_article=?;");

                        statement.setInt(1,((Article) object_p).getPack());
                        statement.setInt(2,((Article) object_p).getId_article());

                        statement.executeUpdate();
                    }
                    else {
                        System.out.println("HEY");
                        statement = connection.prepareStatement("INSERT INTO pack(id_pack, id_article) VALUES (?, ?);");

                        statement.setInt(1,((Article) object_p).getPack());
                        statement.setInt(2,((Article) object_p).getId_article());

                        statement.executeUpdate();
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la modification de l'article.");
            }
        }

    /**
     * Supprime un article de la base de données.
     * @param object_p l'objet Article à supprimer
     */
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
    public ArrayList<Integer> getAllPack(){
        ArrayList<Integer> listPack = new ArrayList<Integer>();

        try {
            Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultat = statement.executeQuery("SELECT * FROM description_pack;");

            while (resultat.next()){
                listPack.add(resultat.getInt("id_pack"));
            }

            return listPack;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ajouterPack(int nb_article, double prix){

        try {

            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO description_pack(nb_articles, prix) VALUES (?,?);");
            statement.setInt(1, (nb_article));
            statement.setDouble(2, (prix));

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'article.");
        }
    }
}
