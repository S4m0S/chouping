package Modele;

import java.util.ArrayList;

public class Panier {
    private ArrayList<Article> listeArticle;
    private ArrayList<Integer> listeQuantite;

    public Panier(){
        this.listeArticle = new ArrayList<Article>();
        this.listeQuantite = new ArrayList<Integer>();
    }



    public void ajouterArticle(Article article_ajouter, int quantite){
        this.listeArticle.add(article_ajouter);
        this.listeQuantite.add(quantite);
    }

    public void changerQuantite(Article article_changer, int quantite){
        for (int i = 0; i < this.listeArticle.size(); i++) {
            if (this.listeArticle.get(i).getId_article()==article_changer.getId_article()){
                this.listeQuantite.set(i, quantite);
                return;
            }
        }
    }

    public void supprimerArticle(Article article_supprimer){
        for (int i = 0; i < this.listeArticle.size(); i++) {
            if (this.listeArticle.get(i).getId_article()==article_supprimer.getId_article()){
                this.listeArticle.remove(i);
                this.listeQuantite.remove(i);
                return;
            }
        }
    }

    public ArrayList<Article> getListeArticle(){
        return this.listeArticle;
    }

    public ArrayList<Integer> getListeQuantite(){
        return this.listeQuantite;
    }

}
