package Controleur;

import DAO.DaoFactory;
import Modele.User;
import Vue.*;

public class ControlleurSupreme {
    VuePrincipal vuePrincipal;
    DaoFactory daoFactory;
    User user;

    public ControlleurSupreme(VuePrincipal vuePrincipal_p, DaoFactory daoFactory_p){
        this.vuePrincipal = vuePrincipal_p;
        this.daoFactory = daoFactory_p;
        this.vuePrincipal.getControlleurSupreme(this);
        this.user = null;
    }


    public void requestConnection(String pseudo, String password, VueBase vue){
        User user_essaie = daoFactory.getClientDAO().connection(pseudo,password);
        if(user_essaie!=null){
            this.user = user;
            ((VueConnection) vue).changeErrorLabel("Vous êtes désormais connecté");
        }
        else{
            ((VueConnection) vue).changeErrorLabel("Erreur lors de la connection à votre compte");
        }
    }

    public void accederCompte(){
        if (this.user == null) {
            vuePrincipal.accederVue(new VueConnection(this));
        }
    }

    public void accederAcceuil(){
        vuePrincipal.accederVue(new VueAccueil(this));
    }

    public void accederCategorie(String nomCategorie) {
        VueBoutiqueCategorie vue = new VueBoutiqueCategorie(this, nomCategorie);
        vuePrincipal.accederVue(vue);
    }

    public User getUser(){
        return this.user;
    }
}
