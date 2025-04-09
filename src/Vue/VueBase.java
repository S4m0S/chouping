package Vue;

import javafx.scene.Parent;

public abstract class VueBase {

    protected Parent root;

    public VueBase(){
        initialiserComposant();
    }

    protected abstract void initialiserComposant();


    public Parent getRoot() {
        return root;
    }

    public abstract void actualiser();
}
