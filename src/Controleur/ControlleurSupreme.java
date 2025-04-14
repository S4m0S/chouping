package Controleur;

import DAO.DaoFactory;
import Modele.Client;
import Modele.Panier;
import Modele.User;
import Vue.*;

public class ControlleurSupreme {
    private VuePrincipal vuePrincipal;
    private DaoFactory daoFactory;
    private User user;
    private Client client;
    private Panier panier;

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

    public void accederCategorie(String nomCategorie) {
        VueBoutiqueCategorie vue = new VueBoutiqueCategorie(this, nomCategorie);
        vuePrincipal.accederVue(vue);
    }

    public void updateClient(Client client_p){
        this.client = client_p;
        daoFactory.getClientDAO().modifier(client_p);
    }



    public void accederPanier(){
        vuePrincipal.accederVue(new VuePanier(this));
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
        return true;
    }

    public Panier getPanier(){return this.panier;}


}
