package Modele;

public class User {

    private int userType;
    private int id_user;
    private String pseudo;
    private String password;

    public User(int id_user_p,String pseudo_p, int user_type_p){
        this.pseudo = pseudo_p;
        this.userType = user_type_p;
        this.id_user = id_user_p;
    }

    public User(String pseudo_p, int user_type_p){
        this.pseudo = pseudo_p;
        this.userType = user_type_p;

    }




    public void setPassword(String password_p){
        this.password = password_p;
    }

    public String getPassword(){
        return this.password;
    }

    public int getUserType(){
        return this.userType;
    }
    public int getId_user(){return this.id_user;}
}
