package Vue;

import Controleur.ControlleurSupreme;
import javafx.scene.Parent;
/**
 * VueBase est une classe abstraite servant de modèle commun pour toutes les vues de l'application.
 *
 * Elle définit la structure minimale qu'une vue doit suivre :
 * - Initialiser ses composants graphiques
 * - Configurer ses actions utilisateur
 * - Pouvoir être actualisée dynamiquement
 *
 * Auteurs : [Ton Nom]
 * Date : [Date du fichier]
 */
public abstract class VueBase {

    protected Parent root;
    protected ControlleurSupreme controlleurSupreme;

    public VueBase(ControlleurSupreme controlleurSupreme_p){
        this.controlleurSupreme = controlleurSupreme_p;
        initialiserComposant();
        configurerActions();
    }
    /**
     * Méthode abstraite que chaque vue doit implémenter pour créer
     * et agencer ses composants graphiques.
     */
    protected abstract void initialiserComposant();
    /**
     * Méthode abstraite que chaque vue doit implémenter pour définir
     * ses actions et événements liés aux composants graphiques.
     */
    protected abstract void configurerActions();
    /**
     * Retourne la racine graphique de la vue (Parent) pour affichage.
     *
     * @return La racine de la vue.
     */
    public Parent getRoot() {
        return root;
    }


    /**
     * Méthode abstraite permettant à une vue d'actualiser son contenu
     * en fonction des données (par exemple après une mise à jour d'un modèle).
     */
    public abstract void actualiser();
}
