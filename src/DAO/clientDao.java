package DAO;

import Modele.Client;

import java.util.ArrayList;

public class clientDao extends objectDao{

    private DaoFactory daoFactory;

    public clientDao(DaoFactory daoFactory_p){
        this.daoFactory = daoFactory_p;
    }

    @Override
    public ArrayList<Object> getAll(){
        ArrayList<Object> liste_Client= new ArrayList<Object>();



        return liste_Client;
    }


}
