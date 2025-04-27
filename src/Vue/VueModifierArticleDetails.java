package Vue;

import Controleur.ControlleurSupreme;
import Modele.Article;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
/**
 * VueModifierArticleDetails représente la vue permettant de modifier les détails d'un article existant.
 * Cette vue permet de visualiser et de modifier les informations d'un article comme le nom, le prix, le stock,
 * la description, et d'autres caractéristiques comme le type, la couleur, la matière, la taille, etc.
 * Elle offre aussi la possibilité de supprimer un article.
 */
public class VueModifierArticleDetails extends VueBase{
    private Article article;

    public VueModifierArticleDetails(ControlleurSupreme controlleurSupreme_p, Article article_p) {
        super(controlleurSupreme_p);
        this.article = article_p;
        initialiseComposant();
    }
    /**
     * Initialise les composants de la vue pour la modification des détails d'un article.
     * Cette méthode crée le formulaire avec les champs pré-remplis avec les valeurs actuelles de l'article.
     * Elle configure également les boutons pour sauvegarder, supprimer ou annuler la modification.
     */
    private void initialiseComposant(){

        // Create form fields with current values
        TextField champNom = new TextField(article.getNom());
        TextField champPrix = new TextField(String.valueOf(article.getPrix()));
        TextField champStock = new TextField(String.valueOf(article.getStock()));
        TextArea champDescription = new TextArea(article.getDescription());
        champDescription.setPrefRowCount(3);

        // Additional fields with current values
        ComboBox<String> comboType = new ComboBox<>(FXCollections.observableArrayList(
                "Type 1", "Type 2", "Type 3"
        ));
        comboType.getSelectionModel().select(article.getType() - 1);

        ComboBox<String> comboClasse = new ComboBox<>(FXCollections.observableArrayList(
                "Classe A", "Classe B", "Classe C"
        ));
        comboClasse.getSelectionModel().select(article.getClasse() - 1);

        ComboBox<String> comboCouleur = new ComboBox<>(FXCollections.observableArrayList(
                "Rouge", "Vert", "Bleu", "Noir", "Blanc"
        ));
        comboCouleur.getSelectionModel().select(article.getCouleur() - 1);

        TextField champTaille = new TextField(String.valueOf(article.getTaille()));

        ComboBox<String> comboMatiere = new ComboBox<>(FXCollections.observableArrayList(
                "Coton", "Polyester", "Laine", "Soie"
        ));
        comboMatiere.getSelectionModel().select(article.getMatiere() - 1);

        ComboBox<String> comboSolidite = new ComboBox<>(FXCollections.observableArrayList(
                "Fragile", "Standard", "Robuste", "Très robuste"
        ));
        comboSolidite.getSelectionModel().select(article.getSolidite() - 1);

        TextField champPoids = new TextField(String.valueOf(article.getPoids()));

        // Buttons
        Button boutonSauvegarder = new Button("Sauvegarder");
        Button boutonSupprimer = new Button("Supprimer");
        Button boutonAnnuler = new Button("Annuler");

        // Form layout
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));

        // Add form elements
        formGrid.add(new Label("Nom*:"), 0, 0);
        formGrid.add(champNom, 1, 0);
        formGrid.add(new Label("Prix*:"), 0, 1);
        formGrid.add(champPrix, 1, 1);
        formGrid.add(new Label("Stock*:"), 0, 2);
        formGrid.add(champStock, 1, 2);
        formGrid.add(new Label("Description:"), 0, 3);
        formGrid.add(champDescription, 1, 3);
        formGrid.add(new Label("Type:"), 0, 4);
        formGrid.add(comboType, 1, 4);
        formGrid.add(new Label("Classe:"), 0, 5);
        formGrid.add(comboClasse, 1, 5);
        formGrid.add(new Label("Couleur:"), 0, 6);
        formGrid.add(comboCouleur, 1, 6);
        formGrid.add(new Label("Taille:"), 0, 7);
        formGrid.add(champTaille, 1, 7);
        formGrid.add(new Label("Matière:"), 0, 8);
        formGrid.add(comboMatiere, 1, 8);
        formGrid.add(new Label("Solidité:"), 0, 9);
        formGrid.add(comboSolidite, 1, 9);
        formGrid.add(new Label("Poids:"), 0, 10);
        formGrid.add(champPoids, 1, 10);

        // Button bar
        HBox buttonBar = new HBox(10, boutonAnnuler,boutonSupprimer, boutonSauvegarder);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        // Main layout
        BorderPane mainPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane(formGrid);
        scrollPane.setFitToWidth(true);
        mainPane.setCenter(scrollPane);
        mainPane.setBottom(buttonBar);
        mainPane.setPadding(new Insets(10));

        // Configure actions
        boutonSauvegarder.setOnAction(e -> {
            try {

                // Validate required fields
                if (champNom.getText().isEmpty() || champPrix.getText().isEmpty() || champStock.getText().isEmpty()) {
                    showAlert("Champs requis", "Les champs marqués d'un * sont obligatoires.");
                    return;
                }

                // Update article object
                article.setNom(champNom.getText());
                article.setPrix(Double.parseDouble(champPrix.getText()));
                article.setStock(Integer.parseInt(champStock.getText()));
                article.setDescription(champDescription.getText());
                article.setType(comboType.getSelectionModel().getSelectedIndex() + 1);
                article.setClasse(comboClasse.getSelectionModel().getSelectedIndex() + 1);
                article.setCouleur(comboCouleur.getSelectionModel().getSelectedIndex() + 1);
                article.setTaille(champTaille.getText().isEmpty() ? 0 : Double.parseDouble(champTaille.getText()));
                article.setMatiere(comboMatiere.getSelectionModel().getSelectedIndex() + 1);
                article.setSolidite(comboSolidite.getSelectionModel().getSelectedIndex() + 1);
                article.setPoids(champPoids.getText().isEmpty() ? 0 : Double.parseDouble(champPoids.getText()));

                // Call controller to update article
                this.controlleurSupreme.modifierArticle(article);

                this.controlleurSupreme.afficherModifierArticle();


            } catch (NumberFormatException ex) {
                showAlert("Erreur de format", "Veuillez entrer des valeurs numériques valides pour les champs numériques.");
            }
        });

        boutonSupprimer.setOnAction(e->{
            this.controlleurSupreme.supprimerArticle(article);
            this.controlleurSupreme.afficherModifierArticle();
        });


        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        mainPane.setTop(menuPrincipal.getMenuBar());

        // Show the edit window
        this.root = mainPane;
    }

    /**
     * Affiche une alerte avec un titre et un message d'erreur.
     * Cette méthode est utilisée pour avertir l'utilisateur si des erreurs surviennent lors de la modification de l'article.
     *
     * @param title Le titre de l'alerte.
     * @param message Le message d'erreur à afficher.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    protected void initialiserComposant() {


    }

    @Override
    protected void configurerActions() {

    }

    @Override
    public void actualiser() {

    }
}
