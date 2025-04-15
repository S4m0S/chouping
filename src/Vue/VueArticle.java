package Vue;

import Controleur.ControlleurSupreme;
import Modele.Article;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VueArticle extends VueBase {
    private Article article;
    private Spinner<Integer> quantiteSpinner;

    // Composants UI
    private HBox topContainer;
    private VBox infoContainer;
    private Label nomLabel, prixLabel, stockLabel;
    private TextArea descriptionArea;
    private GridPane specsGrid;

    public VueArticle(ControlleurSupreme controlleurSupreme_p, Article article_p) {
        super(controlleurSupreme_p);
        this.article = article_p;
        actualiser(); // Maintenant l'article est disponible
    }

    @Override
    protected void initialiserComposant() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.getStylesheets().add(getClass().getResource("/src/resources/css/article.css").toExternalForm());

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new MenuPrincipal(controlleurSupreme).getMenuBar());


        // Création des conteneurs vides
        topContainer = new HBox(20);
        infoContainer = new VBox(10);

        // Création des composants sans données
        nomLabel = new Label();
        prixLabel = new Label();
        stockLabel = new Label();
        quantiteSpinner = new Spinner<>(1, 1, 1); // Valeurs par défaut
        descriptionArea = new TextArea();
        specsGrid = new GridPane();

        // Structure de base
        topContainer.getChildren().addAll(new Pane(), infoContainer); // Pane vide pour l'image
        infoContainer.getChildren().addAll(
                nomLabel,
                prixLabel,
                stockLabel,
                new HBox(10, new Label("Quantité :"), quantiteSpinner),
                new Button("Ajouter au panier")
        );

        mainContainer.getChildren().addAll(
                topContainer,
                new Label("Description de l'article"),
                descriptionArea,
                new Label("Caractéristiques"),
                specsGrid
        );
        borderPane.setCenter(mainContainer);

        this.root = borderPane;

        // Appliquer les classes CSS
        appliquerStylesInitiaux();
    }

    private void appliquerStylesInitiaux() {
        nomLabel.getStyleClass().add("nom-article");
        prixLabel.getStyleClass().add("prix");
        stockLabel.getStyleClass().add("stock");
        descriptionArea.setEditable(false);
        specsGrid.setHgap(15);
        specsGrid.setVgap(10);
    }

    @Override
    protected void configurerActions() {
        Button ajouterButton = (Button) infoContainer.getChildren().get(4);
        ajouterButton.setOnAction(e -> {
            if (this.controlleurSupreme.getUser()!=null) {
                controlleurSupreme.getPanier().ajouterArticle(article, quantiteSpinner.getValue());
                afficherConfirmation();
            }

        });
    }

    @Override
    public void actualiser() {
        if (article != null) {
            // Peupler les données
            nomLabel.setText(article.getNom());
            prixLabel.setText(String.format("Prix : %.2f pièces d'or", article.getPrix()));
            stockLabel.setText("En stock : " + article.getStock());
            descriptionArea.setText(article.getDescription());

            // Configurer le spinner
            quantiteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    1, article.getStock(), 1));

            // Peupler les caractéristiques
            specsGrid.getChildren().clear();
            specsGrid.addRow(0, new Label("Type :"), new Label(convertType(article.getType())));
            specsGrid.addRow(1, new Label("Classe :"), new Label(convertClasse(article.getClasse())));
            specsGrid.addRow(2, new Label("Matière :"), new Label(convertMatiere(article.getMatiere())));
            specsGrid.addRow(3, new Label("Poids :"), new Label(String.format("%.1f kg", article.getPoids())));
            specsGrid.addRow(4, new Label("Solidité :"), new Label(convertSolidite(article.getSolidite())));
        }
    }

    private void afficherConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout réussi");
        alert.setHeaderText(null);
        alert.setContentText(quantiteSpinner.getValue() + " x " + article.getNom() + " ajouté(s) au panier !");
        alert.showAndWait();
    }

    private String convertType(int type) {
        return switch (type) {
            case 1 -> "Arme";
            case 2 -> "Plastron";
            case 3 -> "Bottes";
            case 4 -> "Potion";
            default -> "Inconnu";
        };
    }

    private String convertClasse(int classe) {
        return switch (classe) {
            case 0 -> "Toutes classes";
            case 1 -> "Mage";
            case 2 -> "Guerrier";
            case 3 -> "Paladin";
            case 4 -> "Voleur";
            default -> "Inconnue";
        };
    }

    private String convertMatiere(int matiere) {
        return switch (matiere) {
            case 1 -> "Tissu";
            case 2 -> "Cuir";
            case 3 -> "Acier";
            case 4 -> "Écailles";
            case 5 -> "Bois";
            case 6 -> "Verre";
            default -> "Inconnue";
        };
    }

    private String convertSolidite(int solidite) {
        return switch (solidite) {
            case 0 -> "-";
            case 1 -> "Fragile";
            case 2 -> "Délicat";
            case 3 -> "Standard";
            case 4 -> "Robuste";
            case 5 -> "Très solide";
            case 6 -> "Indestructible";
            default -> "Inconnue";
        };
    }
}