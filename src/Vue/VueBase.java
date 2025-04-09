package Vue;

import Controleur.ControlleurSupreme;
import javafx.scene.Parent;

public abstract class VueBase {

    protected Parent root;
    protected ControlleurSupreme controlleurSupreme;

    public VueBase(ControlleurSupreme controlleurSupreme_p){
        this.controlleurSupreme = controlleurSupreme_p;
        initialiserComposant();
        configurerActions();
    }

    protected abstract void initialiserComposant();

    protected abstract void configurerActions();

    public Parent getRoot() {
        return root;
    }



    public abstract void actualiser();
}
