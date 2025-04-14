package Controleur;

import DAO.DaoFactory;
import Modele.Article;
import Modele.Client;
import Modele.Panier;
import Modele.User;
import Vue.*;

import java.util.ArrayList;

public class ControlleurSupreme {
    private VuePrincipal vuePrincipal;
    private DaoFactory daoFactory;
    private User user;
    private Client client;
    private Panier panier;
    private String categorieActuelle;

    public ControlleurSupreme(VuePrincipal vuePrincipal_p, DaoFactory daoFactory_p){
        this.vuePrincipal = vuePrincipal_p;
        this.daoFactory = daoFactory_p;
        this.vuePrincipal.getControlleurSupreme(this);
        this.user = null;
        this.client = null;
        this.panier = null;
    }


    public void requestConnection(String pseudo, String password, VueBase vue){
        User user_essaie = daoFactory.getClientDAO().connection(pseudo,password);
        if(user_essaie!=null){
            ((VueConnection) vue).changeErrorLabel("Vous êtes désormais connecté");
            this.user = user_essaie;

            switch (this.user.getUserType()){
                case 0:
                    this.client = (Client) daoFactory.getClientDAO().chercher(this.user.getId_user());
                    this.panier = new Panier();
                    vuePrincipal.accederVue(new VueClient(this));

            }

        }
        else{
            ((VueConnection) vue).changeErrorLabel("Erreur lors de la connection à votre compte");
        }
    }

    public void accederCompte(){
        if (this.user == null) {
            vuePrincipal.accederVue(new VueConnection(this));
        }
        else{
            vuePrincipal.accederVue(new VueClient(this));
        }
    }

    public void accederCreationCompte(){
        vuePrincipal.accederVue(new VueCreationCompte(this));
    }

    public void accederAcceuil(){
        vuePrincipal.accederVue(new VueAccueil(this));
    }

    public void accederCategorie(String nomCategorie_p) {
        this.categorieActuelle = nomCategorie_p;
        VueBoutiqueCategorie vue = new VueBoutiqueCategorie(this);
        vuePrincipal.accederVue(vue);
    }

    public void updateClient(Client client_p){
        this.client = client_p;
        daoFactory.getClientDAO().modifier(client_p);
    }

    public String getCategorieActuelle(){
        return this.categorieActuelle;
    }

    public ArrayList<Article> getAllArticles(){
        ArrayList<Object> objectList = daoFactory.getarticleDAO().getAll();

        // Créer une nouvelle liste d'Articles
        ArrayList<Article> articleList = new ArrayList<>();

        // Convertir chaque élément
        for (Object obj : objectList) {
            if (obj instanceof Article) {
                articleList.add((Article) obj);
            }
        }

        return articleList;
    }



    public void accederPanier(){
        if (this.client==null) {
            vuePrincipal.accederVue(new VueConnection(this));
        }
        else {
            vuePrincipal.accederVue(new VuePanier(this));
        }
    }

    public User getUser(){
        return this.user;
    }

    public Client getClient(){
        return this.client;
    }

    public boolean creerClient(Client nouveauClient){
        daoFactory.getClientDAO().ajouter(nouveauClient);
        this.client = nouveauClient;
        vuePrincipal.accederVue(new VueConnection(this));
        return true;
    }

    public Panier getPanier(){return this.panier;}


}
