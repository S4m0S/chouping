package Vue;

import Controleur.ControlleurSupreme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class VueAccueil extends VueBase {

    private BorderPane borderPane;
    private VBox contenuCentral;
    private Label titreAcceuil;
    private Button boutonVueCompte;


    public VueAccueil(ControlleurSupreme controlleurSupreme_p){
        super(controlleurSupreme_p);
    }

    @Override
    protected void initialiserComposant(){
        // Conteneur principal qui sera donne comme root
        borderPane = new BorderPane();

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        // Creation du contenu au centre de la vue

        contenuCentral = new VBox(20);
        contenuCentral.setPadding(new Insets(30));
        contenuCentral.setAlignment(Pos.CENTER);

        // Titre Principal

        titreAcceuil = new Label("Bienvenue sur notre site Chouping");
        titreAcceuil.setStyle("");
        // fonctione qui si lier a un fichier CSS
        // titreAcceuil.getStyleClass().add("titre-principal");

        boutonVueCompte = new Button("Voir Mon Compte");
        //boutonVueCompte.setStyle("");


        // Ajout de tous les composants dans le conteneur central
        contenuCentral.getChildren().addAll(titreAcceuil,boutonVueCompte);

        // Ajout du conteneur central au centre du conteneur Principal
        borderPane.setCenter(contenuCentral);


        // Ajout d'un pied de page possible
        HBox footer = new HBox();
        footer.setPadding(new Insets(10));
        footer.setAlignment(Pos.CENTER_RIGHT);
        Label versionLabel = new Label("Version 1.0");
        footer.getChildren().add(versionLabel);
        borderPane.setBottom(footer);


        // Ajout du conteneur principal en tant que root pour pouvoir le donner sur demande
        this.root = borderPane;
    }

    @Override
    protected void configurerActions(){
        boutonVueCompte.setOnAction(event -> {
            ((ControlleurSupreme) controlleurSupreme).accederCompte(this);
        });
    }

    @Override
    public void actualiser(){
        // Test d'actualisation
        titreAcceuil.setText("Allons sur la page Compte...");
    }

}
