package DAO;

import Modele.Client;
import Modele.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * DAO (Data Access Object) pour gérer les opérations sur la table 'client'.
 * Permet d'ajouter, chercher, modifier, supprimer et récupérer tous les clients.
 */
public class clientDAO implements objectDao{

    private DaoFactory daoFactory;

    /**
     * Constructeur du DAO pour client.
     * @param daoFactory_p l'usine à connexions pour accéder à la base de données
     */
    public clientDAO(DaoFactory daoFactory_p){
        this.daoFactory = daoFactory_p;
    }

    /**
     * Récupère tous les clients présents dans la base de données.
     * @return une liste d'objets de type Client
     */
    @Override
    public ArrayList<Object> getAll(){
        ArrayList<Object> liste_Client= new ArrayList<Object>();

        try{
            Connection connection = daoFactory.getConnection();
            Statement statement =connection.createStatement();

            ResultSet resultat =statement.executeQuery("SELECT c.*, cp.* FROM client c JOIN compte cp ON c.id_compte = cp.id_compte;");

            while (resultat.next()){

                int id_compte = resultat.getInt(1);
                String nom = resultat.getString(2);
                String email = resultat.getString(3);
                int classe = resultat.getInt(4);
                double monnaie = resultat.getDouble(5);
                Date date_naissance = resultat.getDate(6);

                Client client_n = new Client(id_compte,nom,email,classe,monnaie,date_naissance);

                int type_compte = resultat.getInt("user_type");
                String mdp = resultat.getString("mdp");
                String pseudo = resultat.getString("pseudo");

                User user_p = new User(id_compte,pseudo,type_compte);
                user_p.setPassword(mdp);

                client_n.setUser(user_p);

                liste_Client.add(client_n);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion a la base de donnée");
            System.out.println("Impossible de récuperer les clients");
        }

        return liste_Client;
    }

    /**
     * Ajoute un client dans la base de données.
     * @param object_p l'objet Client à ajouter
     * @return l'identifiant du compte généré, ou -1 en cas d'erreur
     */
    @Override
    public int ajouter(Object object_p){

        try{
            Connection connection = daoFactory.getConnection();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO compte(pseudo , mdp, user_type) VALUES (?,?,?);");

            statement.setString(1,((Client) object_p).getNom());
            statement.setString(2,"mdp");
            statement.setInt(3, 0);

            statement.executeUpdate();

            statement = connection.prepareStatement("SELECT id_compte FROM compte WHERE pseudo=?;");
            statement.setString(1,((Client) object_p).getNom());

            ResultSet resultat = statement.executeQuery();
            resultat.next();

            int id_compte = resultat.getInt("id_compte");

            statement = connection.prepareStatement("INSERT INTO client(id_compte, nom, mail, classe, monnaie, date_naissance) VALUES (?,?,?,?,?,?);");

            statement.setInt(1,id_compte);
            statement.setString(2,((Client) object_p).getNom());
            statement.setString(3,((Client) object_p).getMail());
            statement.setInt(4,((Client) object_p).getClasse());
            statement.setDouble(5,((Client) object_p).getMonnaie());
            statement.setDate(6,((Client) object_p).getDate_naissance());

            statement.executeUpdate();

            return id_compte;

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion a la base de donnée");
            System.out.println("Impossible de créer un compte");
            return -1;
        }

    }

    /**
     * Supprime un administrateur de la base de données.
     * @param user_id l'identifiant de l'utilisateur à supprimer
     */
    public void supprimerAdmin(int user_id){
        try{
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM compte WHERE id_compte=?;");
            statement.setInt(1,user_id);

            statement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion a la base de donnée");
            System.out.println("Impossible de supprimer le client");
        }
    }

    /**
     * Ajoute un administrateur dans la base de données.
     * @param new_user l'utilisateur à ajouter
     */
    public void ajouterAdmin(User new_user){
        try{
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO compte(pseudo,mdp,user_type) VALUES (?,?,?)");

            statement.setString(1,new_user.getPseudo());
            statement.setString(2,new_user.getPassword());
            statement.setInt(3,new_user.getUserType());

            statement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Tente de connecter un utilisateur avec son pseudo et mot de passe.
     * @param pseudo le pseudo de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return l'objet User si la connexion réussit, null sinon
     */
    public User connection(String pseudo, String password){
        try{
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT id_compte, user_type FROM compte WHERE pseudo=? AND mdp=?");

            statement.setString(1,pseudo);
            statement.setString(2,password);

            ResultSet resultat = statement.executeQuery();

            if (resultat.next()){
                int id_compte = resultat.getInt("id_compte");
                int user_type = resultat.getInt("user_type");

                User user = new User(id_compte,pseudo,user_type);

                return user;
            }
            else {
                return null;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Cherche un client spécifique par son identifiant.
     * @param object_id l'identifiant du client
     * @return l'objet Client trouvé, ou null si non trouvé
     */
    @Override
    public Object chercher(int object_id){

        try{
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM client WHERE id_compte=?;");

            statement.setInt(1,object_id);

            ResultSet resultat = statement.executeQuery();

            if(resultat.next()) {

                String nom = resultat.getString(2);
                String email = resultat.getString(3);
                int classe = resultat.getInt(4);
                double monnaie = resultat.getDouble(5);
                Date date_naissance = resultat.getDate(6);

                Client client = new Client(object_id, nom, email, classe, monnaie, date_naissance);

                return client;
            }
            else {
                System.out.println("Impossible de trouver un client avec ID");
                return null;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion a la base de donnée");
            System.out.println("Impossible de rechercher le client");
            return null;
        }
    }

    /**
     * Modifie un client existant dans la base de données.
     * @param object_p le client modifié
     */
    @Override
    public void modifier(Object object_p){
        try{
            Connection connection = daoFactory.getConnection();

            PreparedStatement statement = connection.prepareStatement("UPDATE client SET nom=?, mail=?, classe=?, monnaie=?, date_naissance=? WHERE id_compte=?;");

            statement.setString(1,((Client)object_p).getNom());
            statement.setString(2,((Client)object_p).getMail());
            statement.setInt(3,((Client)object_p).getClasse());
            statement.setDouble(4,((Client)object_p).getMonnaie());
            statement.setDate(5,((Client)object_p).getDate_naissance());
            statement.setInt(6,((Client)object_p).getId_compte());

            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion a la base de donnée");
        }
    }

    /**
     * Supprime un client de la base de données, ainsi que son compte et ses commandes.
     * @param object_p le client à supprimer
     */
    @Override
    public void supprimer(Object object_p){
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM client WHERE id_compte=?;");
            statement.setInt(1,((Client) object_p).getId_compte());

            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM compte WHERE id_compte=?;");
            statement.setInt(1,((Client) object_p).getId_compte());

            statement.executeUpdate();

            statement = connection.prepareStatement("DELETE FROM commande WHERE id_compte=?;");
            statement.setInt(1,((Client) object_p).getId_compte());

            statement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion a la base de donnée");
            System.out.println("Impossible de supprimer le client");
        }
    }

}