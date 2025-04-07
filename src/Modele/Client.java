package Modele;

import java.util.Date;

public class Client {

    private String nom;
    private String mail;
    private int classe;
    private double monnaie;
    private java.sql.Date date_naissance;

    public Client(String nom_p, String mail_p, int classe_p, double monnaie_p, java.sql.Date date_naissance_p){
        this.nom = nom_p;
        this.mail = mail_p;
        this.classe = classe_p;
        this.monnaie = monnaie_p;
        this.date_naissance = date_naissance_p;
    }


}
