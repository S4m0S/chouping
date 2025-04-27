package Vue;

import Controleur.ControlleurSupreme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class VueAjouterPack extends VueBase {

    public VueAjouterPack(ControlleurSupreme controlleurSupreme) {
        super(controlleurSupreme);
    }

    @Override
    protected void initialiserComposant() {
        // Création des champs de formulaire
        TextField champNbArticles = new TextField();
        champNbArticles.setPromptText("Nombre d'articles dans le pack");

        TextField champPrix = new TextField();
        champPrix.setPromptText("Prix du pack");

        // Boutons
        Button boutonAjouter = new Button("Ajouter le pack");

        // Configuration du layout
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));

        // Ajout des éléments au formulaire
        formGrid.add(new Label("Nombre d'articles*:"), 0, 0);
        formGrid.add(champNbArticles, 1, 0);
        formGrid.add(new Label("Prix*:"), 0, 1);
        formGrid.add(champPrix, 1, 1);

        // Barre de boutons
        HBox buttonBar = new HBox(10, boutonAjouter);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        // Layout principal
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(formGrid);
        mainPane.setBottom(buttonBar);
        mainPane.setPadding(new Insets(10));

        // Menu
        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        mainPane.setTop(menuPrincipal.getMenuBar());

        this.root = mainPane;
    }

    @Override
    protected void configurerActions() {
        // Récupération des composants
        GridPane formGrid = (GridPane) ((BorderPane) root).getCenter();
        TextField champNbArticles = (TextField) formGrid.getChildren().get(1);
        TextField champPrix = (TextField) formGrid.getChildren().get(3);

        HBox buttonBar = (HBox) ((BorderPane) root).getBottom();
        Button boutonAjouter = (Button) buttonBar.getChildren().get(0);


        // Configuration des actions
        boutonAjouter.setOnAction(e -> {
            try {
                // Validation des champs
                if (champNbArticles.getText().isEmpty() || champPrix.getText().isEmpty()) {
                    afficherAlerte("Champs requis", "Tous les champs marqués d'un * sont obligatoires");
                    return;
                }

                int nbArticles = Integer.parseInt(champNbArticles.getText());
                double prix = Double.parseDouble(champPrix.getText());

                if (nbArticles <= 0 || prix <= 0) {
                    afficherAlerte("Valeur invalide", "Le nombre d'articles et le prix doivent être supérieurs à 0");
                    return;
                }

                // Appel au contrôleur pour ajouter le pack
                controlleurSupreme.ajouterPack(nbArticles, prix);

            } catch (NumberFormatException ex) {
                afficherAlerte("Format invalide", "Veuillez entrer des nombres valides");
            }
        });

    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void actualiser() {
        // Pas besoin d'actualisation pour cette vue
    }
}