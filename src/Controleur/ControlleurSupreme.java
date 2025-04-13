package Controleur;

import DAO.DaoFactory;
import Modele.Client;
import Modele.User;
import Vue.*;

public class ControlleurSupreme {
    VuePrincipal vuePrincipal;
    DaoFactory daoFactory;
    User user;
    Client client;

    public ControlleurSupreme(VuePrincipal vuePrincipal_p, DaoFactory daoFactory_p){
        this.vuePrincipal = vuePrincipal_p;
        this.daoFactory = daoFactory_p;
        this.vuePrincipal.getControlleurSupreme(this);
        this.user = null;
        this.client = null;
    }


    public void requestConnection(String pseudo, String password, VueBase vue){
        User user_essaie = daoFactory.getClientDAO().connection(pseudo,password);
        if(user_essaie!=null){
            ((VueConnection) vue).changeErrorLabel("Vous êtes désormais connecté");
            this.user = user_essaie;
            this.client = (Client) daoFactory.getClientDAO().chercher(this.user.getId_user());
            if (this.client==null){
                System.out.println("Erreurs");
            }
            switch (this.user.getUserType()){
                case 0:
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

    public User getUser(){
        return this.user;
    }

    public Client getClient(){
        return this.client;
    }


}
