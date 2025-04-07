package Modele;

import java.sql.Date;
import java.util.ArrayList;

public class Client {

    private int id_compte;
    private String nom;
    private String mail;
    private int classe;
    private double monnaie;
    private java.sql.Date date_naissance;

    // Initialaser
    public Client(String nom_p, String mail_p, int classe_p, double monnaie_p, java.sql.Date date_naissance_p){
        this.nom = nom_p;
        this.mail = mail_p;
        this.classe = classe_p;
        this.monnaie = monnaie_p;
        this.date_naissance = date_naissance_p;
    }

    // Setter
    public void setClasse(int classe) {this.classe = classe;}
    public void setDate_naissance(Date date_naissance) {this.date_naissance = date_naissance;}
    public void setMail(String mail) {this.mail = mail;}
    public void setMonnaie(double monnaie) {this.monnaie = monnaie;}
    public void setNom(String nom) {this.nom = nom;}
    public void setId_compte(int id_compte) {this.id_compte = id_compte;}

    // Getter
    public String getNom() {return nom;}
    public String getMail() {return mail;}
    public int getClasse() {return classe;}
    public double getMonnaie() {return monnaie;}
    public Date getDate_naissance() {return date_naissance;}
    public int getId_compte() {return id_compte;}
}
