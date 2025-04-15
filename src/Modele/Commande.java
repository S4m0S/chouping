package Modele;

import java.sql.Date;

public class Commande {

    private int id_commande;
    private int id_compte;
    private Date date_achat;
    private int[] items;
    private int[] nb_items;

    public Commande(int num_compte_p){

        this.id_compte = num_compte_p;


        // Implementer une fonction qui va chercher les items de la commande ou les mettres en parametre
    }

    public Commande(int id_commande_p, int id_compte_p, Date date_achat_p){
        this.id_commande = id_commande_p;
        this.date_achat = date_achat_p;
        this.id_compte = id_compte_p;
    }


    // Getter
    public Date getDate_achat() {return date_achat;}
    public int getId_commande() {return id_commande;}
    public int getId_compte() {return id_compte;}
    public int[] getItems() {return items;}

    public int[] getNb_items() {
        return nb_items;
    }

    // Setter
    public void setDate_achat(Date date_achat) {this.date_achat = date_achat;}
    public void setItems(int[] items) {this.items = items;}
    public void setId_commande(int id_commande) {this.id_commande = id_commande;}
    public void setId_compte(int id_compte) {this.id_compte = id_compte;}

    public void setNb_items(int[] nb_items) {
        this.nb_items = nb_items;
    }
}
