package Vue;

import Controleur.ControlleurSupreme;
import Modele.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Classe représentant la vue d'accueil de l'application.
 * Affiche un message de bienvenue, une barre de menu principale et un arrière-plan personnalisé.
 */
public class VueAccueil extends VueBase {

    private BorderPane borderPane;
    private VBox contenuCentral;
    private Label titreAcceuil;

    /**
     * Constructeur de la vue d'accueil.
     *
     * @param controlleurSupreme_p Le contrôleur principal de l'application.
     */
    public VueAccueil(ControlleurSupreme controlleurSupreme_p){
        super(controlleurSupreme_p);
    }
    /**
     * Initialise les composants de la vue : menu, contenu central, arrière-plan et pied de page.
     */
    @Override
    protected void initialiserComposant(){
        // Conteneur principal qui sera donne comme root
        borderPane = new BorderPane();


        borderPane.getStylesheets().add(getClass().getResource("/src/resources/css/acceuil.css").toExternalForm());

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        // Creation du contenu au centre de la vue

        contenuCentral = new VBox(20);
        contenuCentral.setPadding(new Insets(30));
        contenuCentral.setAlignment(Pos.CENTER);

        // Titre Principal

        titreAcceuil = new Label(" ");
        titreAcceuil.getStyleClass().add("titre");


        // Ajout de tous les composants dans le conteneur central
        contenuCentral.getChildren().addAll(titreAcceuil);

        // Ajout du conteneur central au centre du conteneur Principal
        borderPane.setCenter(contenuCentral);


        // Ajout d'un pied de page possible
        HBox footer = new HBox();
        footer.setPadding(new Insets(10));
        footer.setAlignment(Pos.CENTER_RIGHT);
        Label versionLabel = new Label("Version 1.0");
        footer.getChildren().add(versionLabel);
        borderPane.setBottom(footer);


        Image image = new Image("resources/acceuil.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );

        borderPane.setBackground(new Background(backgroundImage));

        // Ajout du conteneur principal en tant que root pour pouvoir le donner sur demande
        this.root = borderPane;
    }
    /**
     * Configure les actions pour les composants interactifs.
     * (Non utilisé pour le moment dans la vue d'accueil.)
     */
    @Override
    protected void configurerActions() {

    }
    /**
     * Actualise le contenu de la vue.
     * Met à jour le titre pour simuler une redirection vers la page Compte.
     */
    @Override
    public void actualiser(){
        // Test d'actualisation
        titreAcceuil.setText("Allons sur la page Compte...");
    }

}
