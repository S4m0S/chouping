package Controleur;

import DAO.DaoFactory;
import Vue.VueBase;
import Vue.VuePrincipal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance("chouping","root","");

        VuePrincipal vuePrincipal = new VuePrincipal();

        ControlleurSupreme controlleurSupreme = new ControlleurSupreme(vuePrincipal,daoFactory);

    }
}