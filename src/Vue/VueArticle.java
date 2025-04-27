package Vue;

import Controleur.ControlleurSupreme;
import Modele.Article;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
/**
 * VueArticle est une vue qui affiche en détail un article sélectionné.
 * Permet également à un utilisateur connecté d'ajouter une quantité de cet article à son panier.
 *
 * Cette vue présente les informations principales (nom, prix, stock) ainsi que les caractéristiques techniques.
 *
 * Auteurs : [Ton Nom]
 * Date : [Date du fichier]
 */
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
    /**
     * Initialise les composants graphiques de la vue.
     * Configure la structure de la page et ajoute les éléments principaux :
     * menu, conteneur d'informations, description et caractéristiques.
     */
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

    /**
     * Applique les styles CSS et les propriétés initiales aux composants principaux
     * (ex : rendre la description non éditable, configurer l'espacement du GridPane, etc.).
     */
    private void appliquerStylesInitiaux() {
        nomLabel.getStyleClass().add("nom-article");
        prixLabel.getStyleClass().add("prix");
        stockLabel.getStyleClass().add("stock");
        descriptionArea.setEditable(false);
        specsGrid.setHgap(15);
        specsGrid.setVgap(10);
    }
    /**
     * Configure les actions utilisateur de la vue :
     * - Permet d'ajouter l'article au panier en cliquant sur le bouton prévu à cet effet.
     */
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

    /**
     * Actualise l'affichage de la vue avec les données de l'article :
     * - Met à jour les labels (nom, prix, stock)
     * - Remplit la description et les caractéristiques techniques.
     * - Configure les bornes du sélecteur de quantité selon le stock disponible.
     */
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
    /**
     * Affiche une alerte de confirmation après l'ajout de l'article au panier.
     */
    private void afficherConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout réussi");
        alert.setHeaderText(null);
        alert.setContentText(quantiteSpinner.getValue() + " x " + article.getNom() + " ajouté(s) au panier !");
        alert.showAndWait();
    }
    /**
     * Convertit l'identifiant numérique du type d'article en son nom lisible.
     *
     * @param type Le type sous forme d'identifiant numérique.
     * @return Le nom correspondant au type d'article.
     */
    private String convertType(int type) {
        return switch (type) {
            case 1 -> "Arme";
            case 2 -> "Plastron";
            case 3 -> "Bottes";
            case 4 -> "Potion";
            default -> "Inconnu";
        };
    }
    /**
     * Convertit l'identifiant numérique de la classe d'article en son nom lisible.
     *
     * @param classe La classe sous forme d'identifiant numérique.
     * @return Le nom correspondant à la classe d'article.
     */
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
    /**
     * Convertit l'identifiant numérique de la matière de l'article en son nom lisible.
     *
     * @param matiere La matière sous forme d'identifiant numérique.
     * @return Le nom correspondant à la matière de l'article.
     */
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
    /**
     * Convertit l'identifiant numérique du niveau de solidité en son nom lisible.
     *
     * @param solidite Le niveau de solidité sous forme d'identifiant numérique.
     * @return Le nom correspondant au niveau de solidité de l'article.
     */
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