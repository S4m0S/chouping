package Modele;

public class User {

    private int userType;
    private int id_user;
    private String pseudo;

    public User(int id_user_p,String pseudo_p, int user_type_p){
        this.pseudo = pseudo_p;
        this.userType = user_type_p;
        this.id_user = id_user_p;
    }




    public int getUserType(){
        return this.userType;
    }
    public int getId_user(){return this.id_user;}
}
