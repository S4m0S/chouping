package Modele;

import java.util.ArrayList;

public class Panier {
    private ArrayList<Item> listeItem;
    private ArrayList<Integer> listeQuantite;

    public Panier(){
        this.listeItem = new ArrayList<Item>();
        this.listeQuantite = new ArrayList<Integer>();
    }



    public void ajouterItem(Item item_ajouter, int quantite){
        this.listeItem.add(item_ajouter);
        this.listeQuantite.add(quantite);
    }

    public void changerQuantite(Item item_changer, int quantite){
        for (int i = 0; i < this.listeItem.size(); i++) {
            if (this.listeItem.get(i).getId_item()==item_changer.getId_item()){
                this.listeQuantite.set(i, quantite);
                return;
            }
        }
    }

    public void supprimerItem(Item item_supprimer){
        for (int i = 0; i < this.listeItem.size(); i++) {
            if (this.listeItem.get(i).getId_item()==item_supprimer.getId_item()){
                this.listeItem.remove(i);
                this.listeQuantite.remove(i);
                return;
            }
        }
    }

    public ArrayList<Item> getListeItem(){
        return this.listeItem;
    }

    public ArrayList<Integer> getListeQuantite(){
        return this.listeQuantite;
    }

}
