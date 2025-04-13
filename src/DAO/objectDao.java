package DAO;

import java.util.ArrayList;
import java.util.Objects;

public interface objectDao {

    /**
     * Permet de récuperer
     * @return : liste de tous les objects d'un certain type
     */
    public ArrayList<Object> getAll();

    public void ajouter(Object object_p);



    public Object chercher(int object_id);


    public void modifier(Object object_p);


    public void supprimer(Object object_p);

}
