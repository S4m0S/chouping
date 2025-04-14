package Vue;

import Controleur.ControlleurSupreme;
import Modele.Item;
import Modele.Panier;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VuePanier extends VueBase {

    private VBox itemsContainer;
    private Label totalLabel;

    public VuePanier(ControlleurSupreme controlleurSupreme) {
        super(controlleurSupreme);
    }

    @Override
    protected void initialiserComposant() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("panier-view");
        borderPane.getStylesheets().add(getClass().getResource("/src/resources/css/panier.css").toExternalForm());

        // Menu en haut
        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        // Centre - Contenu du panier
        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(20));

        // Titre
        Label titleLabel = new Label("Votre Panier");
        titleLabel.getStyleClass().add("panier-title");
        mainContainer.getChildren().add(titleLabel);

        // Container pour les items
        itemsContainer = new VBox();
        itemsContainer.getStyleClass().add("panier-item-container");
        mainContainer.getChildren().add(itemsContainer);

        // Section total
        HBox totalSection = new HBox();
        totalSection.getStyleClass().add("total-section");
        totalLabel = new Label();
        totalSection.getChildren().add(totalLabel);
        mainContainer.getChildren().add(totalSection);

        borderPane.setCenter(mainContainer);
        this.root = borderPane;

        actualiserPanier();
    }

    private void actualiserPanier() {
        itemsContainer.getChildren().clear();
        Panier panier = controlleurSupreme.getPanier();
        double total = 0;

        for (int i = 0; i < panier.getListeItem().size(); i++) {
            Item item = panier.getListeItem().get(i);
            int quantite = panier.getListeQuantite().get(i);
            total += item.getPrix() * quantite;

            // Création de la boîte pour chaque item
            HBox itemBox = new HBox();
            itemBox.getStyleClass().add("panier-item-box");
            itemBox.setSpacing(20);
            itemBox.setPadding(new Insets(15));

            // Partie gauche : Info item
            VBox itemInfo = new VBox();
            itemInfo.setSpacing(5);

            Label nameLabel = new Label(item.getNom());
            nameLabel.getStyleClass().add("item-name");

            Label priceLabel = new Label(String.format("%.2f €", item.getPrix()));
            priceLabel.getStyleClass().add("item-price");

            itemInfo.getChildren().addAll(nameLabel, priceLabel);

            // Partie droite : Contrôles quantité
            HBox controlsBox = new HBox();
            controlsBox.getStyleClass().add("quantity-controls");
            controlsBox.setSpacing(10);

            Spinner<Integer> quantitySpinner = new Spinner<>(1, item.getStock(), quantite);
            quantitySpinner.getStyleClass().add("quantity-field");

            Button updateButton = new Button("Modifier");
            updateButton.getStyleClass().add("update-button");
            updateButton.setOnAction(e -> {
                controlleurSupreme.getPanier().changerQuantite(item, quantitySpinner.getValue());
                actualiserPanier();
            });

            Button removeButton = new Button("Supprimer");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(e -> {
                controlleurSupreme.getPanier().supprimerItem(item);
                actualiserPanier();
            });

            controlsBox.getChildren().addAll(quantitySpinner, updateButton, removeButton);

            // Assemblage
            itemBox.getChildren().addAll(itemInfo, controlsBox);
            itemsContainer.getChildren().add(itemBox);
        }

        // Mise à jour du total
        totalLabel.setText(String.format("Total : %.2f €", total));
    }

    @Override
    protected void configurerActions() {
        // Pas besoin d'actions supplémentaires ici
    }

    @Override
    public void actualiser() {
        actualiserPanier();
    }
}