package Vue;

import Controleur.ControlleurSupreme;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class VueArmes extends VueBase {

    private BorderPane layout;
    private Label labelTitre;

    public VueArmes(ControlleurSupreme controlleurSupreme_p) {
        super(controlleurSupreme_p);
    }

    @Override
    protected void initialiserComposant() {
        layout = new BorderPane();

        MenuPrincipal menu = new MenuPrincipal(controlleurSupreme);
        layout.setTop(menu.getMenuBar());

        labelTitre = new Label("Bienvenue dans la page des Armes");
        layout.setCenter(labelTitre);

        this.root = layout;
    }

    @Override
    protected void configurerActions() {
        // Rien pour l’instant
    }

    @Override
    public void actualiser() {
        labelTitre.setText("Actualisation de la page Armes");
    }
}
