package Controleur;

import DAO.DaoFactory;
import Vue.VueBase;
import Vue.VueBoutiqueCategorie;
import Vue.VuePrincipal;

public class ControlleurSupreme {
    VuePrincipal vuePrincipal;
    DaoFactory daoFactory;

    public ControlleurSupreme(VuePrincipal vuePrincipal_p, DaoFactory daoFactory_p){
        this.vuePrincipal = vuePrincipal_p;
        this.daoFactory = daoFactory_p;
        vuePrincipal.getControlleurSupreme(this);
    }

    public void accederCompte(VueBase vue){
        System.out.println("Bien acceder au compte");
        vue.actualiser();
    }

    public void accederCategorie(String nomCategorie) {
        VueBoutiqueCategorie vue = new VueBoutiqueCategorie(this, nomCategorie);
        vuePrincipal.accederVue(vue);
    }

}
