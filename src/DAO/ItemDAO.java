package DAO;

import Modele.Item;
import java.util.ArrayList;



/**
 * Interface ItemDAO pour définir les méthodes d'accès aux données de la table items.
 */
public interface ItemDAO {

    /**
     * Récupérer tous les items présents dans la base de données.
     * @return une liste d'objets Item.
     */
    public ArrayList<Item> getAll();

    /**
     * Ajouter un nouvel item dans la base de données.
     * @param item objet Item à insérer.
     */
    public void ajouter(Item item);

    /**
     * Chercher un item en fonction de son identifiant.
     * @param id identifiant de l'item.
     * @return l'objet Item trouvé ou null si l'id n'existe pas.
     */
    public Item chercher(int id);

    /**
     * Modifier les informations d'un item existant.
     * @param item objet Item contenant les nouvelles informations.
     * @return l'objet Item mis à jour.
     */
    public Item modifier(Item item);

    /**
     * Supprimer un item de la base de données.
     * @param item objet Item à supprimer.
     */
    public void supprimer(Item item);
}
