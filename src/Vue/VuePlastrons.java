package Vue;

import Controleur.ControlleurSupreme;
import Modele.User;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class VuePlastrons extends VueBase {

    private BorderPane layout;
    private Label labelTitre;

    public VuePlastrons(ControlleurSupreme controlleurSupreme_p) {
        super(controlleurSupreme_p);
    }

    @Override
    protected void initialiserComposant() {
        layout = new BorderPane();

        MenuPrincipal menu = new MenuPrincipal(new User(0),controlleurSupreme);
        layout.setTop(menu.getMenuBar());

        labelTitre = new Label("Bienvenue dans la page des Plastrons");
        layout.setCenter(labelTitre);

        this.root = layout;
    }

    @Override
    protected void configurerActions() {
        // Rien pour l’instant
    }

    @Override
    public void actualiser() {
        labelTitre.setText("Actualisation de la page des Plastrons");
    }
}
