package DAO;

import Modele.Client;
import Modele.User;

import java.sql.*;
import java.util.ArrayList;

public class clientDAO implements objectDao{

    private DaoFactory daoFactory;

    public clientDAO(DaoFactory daoFactory_p){
        this.daoFactory = daoFactory_p;
    }

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
            // On peut faire un truc qui reesaie au lieu de return null quand il y a une erreur
            return null;
        }
    }

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


            // Il faudrait check que cela a bien ete effectue
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion a la base de donnée");
        }
    }

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
