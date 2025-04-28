package Vue;

import Controleur.ControlleurSupreme;
import Modele.Article;
import Modele.Panier;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Vue représentant le panier d'un utilisateur.
 * Cette vue permet à l'utilisateur de voir et de modifier les articles présents dans son panier,
 * ainsi que d'afficher le total de la commande.
 * Elle permet également de commander les articles du panier.
 */
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

        // Modification de l'action du bouton commander
        boutonCommander.setOnAction(e -> showCreditCardPopup());

        borderPane.setCenter(mainContainer);
        this.root = borderPane;

        actualiserPanier();
    }

    /**
     * Affiche une popup de saisie de carte de crédit
     */
    private void showCreditCardPopup() {
        // Création de la boîte de dialogue
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Paiement");
        dialog.setHeaderText("Veuillez entrer vos informations de paiement");

        // Configuration des boutons
        ButtonType validerButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(validerButtonType, ButtonType.CANCEL);

        // Création du formulaire
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField numeroCarte = new TextField();
        numeroCarte.setPromptText("Numéro de carte");
        TextField dateExpiration = new TextField();
        dateExpiration.setPromptText("MM/AA");
        TextField codeSecurite = new TextField();
        codeSecurite.setPromptText("CVV");

        grid.add(new Label("Numéro de carte:"), 0, 0);
        grid.add(numeroCarte, 1, 0);
        grid.add(new Label("Date d'expiration:"), 0, 1);
        grid.add(dateExpiration, 1, 1);
        grid.add(new Label("Code de sécurité:"), 0, 2);
        grid.add(codeSecurite, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Résultat de la boîte de dialogue
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == validerButtonType) {
                return "Paiement accepté";
            }
            return null;
        });

        // Affichage de la boîte de dialogue et gestion de la réponse
        dialog.showAndWait().ifPresent(result -> {
            // On ne vérifie pas réellement les infos de la carte
            // On considère que le paiement est accepté
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Commande validée");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Votre commande a été passée avec succès !");
            confirmation.showAndWait();

            // Appel au contrôleur pour valider la commande
            controlleurSupreme.commander();
        });
    }

    private void actualiserPanier() {
        articlesContainer.getChildren().clear();
        Panier panier = controlleurSupreme.getPanier();
        double total = 0;

        for (int i = 0; i < panier.getListeArticle().size(); i++) {
            Article article = panier.getListeArticle().get(i);
            int quantite = panier.getListeQuantite().get(i);
            total += controlleurSupreme.getRealPrice(article,quantite);

            HBox articleBox = new HBox();
            articleBox.getStyleClass().add("panier-article-box");
            articleBox.setSpacing(20);
            articleBox.setPadding(new Insets(15));

            VBox articleInfo = new VBox();
            articleInfo.setSpacing(5);

            Label nameLabel = new Label(article.getNom());
            nameLabel.getStyleClass().add("article-name");

            Label priceLabel = new Label("");
            priceLabel.getStyleClass().add("article-price");

            articleInfo.getChildren().addAll(nameLabel, priceLabel);

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

            priceLabel.setText(String.format("%.2f €", controlleurSupreme.getRealPrice(article,quantitySpinner.getValue())));

            controlsBox.getChildren().addAll(quantitySpinner, updateButton, removeButton);

            articleBox.getChildren().addAll(articleInfo, controlsBox);
            articlesContainer.getChildren().add(articleBox);
        }

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