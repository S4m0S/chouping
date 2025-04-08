package DAO;

import java.util.ArrayList;

public class clientDAO implements objectDao{

    private DaoFactory daoFactory;

    public clientDAO(DaoFactory daoFactory_p){
        this.daoFactory = daoFactory_p;
    }

    @Override
    public ArrayList<Object> getAll(){
        ArrayList<Object> liste_Client= new ArrayList<Object>();



        return liste_Client;
    }

    @Override
    public void ajouter(Object object_p){

    }


    @Override
    public Object chercher(int object_id){

        return new Object();
    }

    @Override
    public void modifier(Object object_p){

    }

    @Override
    public void supprimer(Object object_p){

    }

}
