package Vue;

import Controleur.ControlleurSupreme;
import Modele.User;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


public class VueBoutiqueCategorie extends VueBase {

    private String nomCategorie;

    public VueBoutiqueCategorie(ControlleurSupreme controlleurSupreme, String nomCategorie) {
        super(controlleurSupreme);
        this.nomCategorie = nomCategorie;
        initialiserComposant();
        configurerActions();
    }

    @Override
    protected void initialiserComposant() {
        BorderPane layout = new BorderPane();

        // Ajout du menu en haut
        MenuPrincipal menu = new MenuPrincipal(new User(0),controlleurSupreme);
        layout.setTop(menu.getMenuBar());

        // Contenu central
        Label titre = new Label("Page de la boutique - " + nomCategorie);
        titre.setStyle("-fx-font-size: 24px;");
        VBox contenu = new VBox(20, titre);
        contenu.setStyle("-fx-alignment: center; -fx-padding: 30;");
        layout.setCenter(contenu);

        this.root = layout;
    }


    @Override
    protected void configurerActions() {
        // Actions spécifiques si besoin
    }

    @Override
    public void actualiser() {
        // Mettre à jour la vue si nécessaire
    }
}
