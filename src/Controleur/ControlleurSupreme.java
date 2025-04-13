package Controleur;

import DAO.DaoFactory;
import Vue.VueBase;
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

    public void accederArmes() {
        Vue.VueArmes vueArmes = new Vue.VueArmes(this);
        vuePrincipal.accederVue(vueArmes);
        System.out.println("Page des armes");
    }

    public void accederPlastrons() {
        Vue.VuePlastrons vuePlastrons = new Vue.VuePlastrons(this);
        vuePrincipal.accederVue(vuePlastrons);
        System.out.println("Page des Plastrons");
    }

    public void accederPotions() {
        Vue.VuePotions vuePotions = new Vue.VuePotions(this);
        vuePrincipal.accederVue(vuePotions);
        System.out.println("Page des Potions");
    }

}
