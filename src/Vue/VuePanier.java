package Vue;

import Controleur.ControlleurSupreme;
import Modele.Article;
import Modele.Panier;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VuePanier extends VueBase {

    private VBox articlesContainer;
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

        // Container pour les articles
        articlesContainer = new VBox();
        articlesContainer.getStyleClass().add("panier-article-container");
        mainContainer.getChildren().add(articlesContainer);

        // Section total
        HBox totalSection = new HBox();
        totalSection.getStyleClass().add("total-section");
        totalLabel = new Label();
        totalSection.getChildren().add(totalLabel);
        mainContainer.getChildren().add(totalSection);

        Button boutonCommander = new Button("Commander");
        mainContainer.getChildren().add(boutonCommander);

        boutonCommander.setOnAction(e-> controlleurSupreme.commander());

        borderPane.setCenter(mainContainer);
        this.root = borderPane;

        actualiserPanier();
    }

    private void actualiserPanier() {
        articlesContainer.getChildren().clear();
        Panier panier = controlleurSupreme.getPanier();
        double total = 0;

        for (int i = 0; i < panier.getListeArticle().size(); i++) {
            Article article = panier.getListeArticle().get(i);
            int quantite = panier.getListeQuantite().get(i);
            total += article.getPrix() * quantite;

            // Création de la boîte pour chaque article
            HBox articleBox = new HBox();
            articleBox.getStyleClass().add("panier-article-box");
            articleBox.setSpacing(20);
            articleBox.setPadding(new Insets(15));

            // Partie gauche : Info article
            VBox articleInfo = new VBox();
            articleInfo.setSpacing(5);

            Label nameLabel = new Label(article.getNom());
            nameLabel.getStyleClass().add("article-name");

            Label priceLabel = new Label(String.format("%.2f €", article.getPrix()));
            priceLabel.getStyleClass().add("article-price");

            articleInfo.getChildren().addAll(nameLabel, priceLabel);

            // Partie droite : Contrôles quantité
            HBox controlsBox = new HBox();
            controlsBox.getStyleClass().add("quantity-controls");
            controlsBox.setSpacing(10);

            Spinner<Integer> quantitySpinner = new Spinner<>(1, article.getStock(), quantite);
            quantitySpinner.getStyleClass().add("quantity-field");

            Button updateButton = new Button("Modifier");
            updateButton.getStyleClass().add("update-button");
            updateButton.setOnAction(e -> {
                controlleurSupreme.getPanier().changerQuantite(article, quantitySpinner.getValue());
                actualiserPanier();
            });

            Button removeButton = new Button("Supprimer");
            removeButton.getStyleClass().add("remove-button");
            removeButton.setOnAction(e -> {
                controlleurSupreme.getPanier().supprimerArticle(article);
                actualiserPanier();
            });

            controlsBox.getChildren().addAll(quantitySpinner, updateButton, removeButton);

            // Assemblage
            articleBox.getChildren().addAll(articleInfo, controlsBox);
            articlesContainer.getChildren().add(articleBox);
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