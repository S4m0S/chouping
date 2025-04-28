package Vue;

import Controleur.ControlleurSupreme;
import Modele.Article;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
/**
 * VueModifierArticle représente la vue permettant de modifier un article existant.
 * Cette vue affiche une liste d'articles et permet à l'utilisateur de sélectionner un article
 * pour le modifier. Elle fournit également un bouton pour fermer la vue.
 */
public class VueModifierArticle extends VueBase {
    private ListView<Article> listeArticles;
    private Button boutonModifier;
    private Button boutonFermer;
    private VBox mainLayout;

    public VueModifierArticle(ControlleurSupreme controlleurSupreme_p) {
        super(controlleurSupreme_p);
    }
    /**
     * Initialise les composants de la vue de modification d'article.
     * Cette méthode crée la liste des articles, les boutons de modification et de fermeture,
     * et configure la mise en page.
     */
    @Override
    protected void initialiserComposant() {
        // Create components


        listeArticles = new ListView<>();
        listeArticles.setCellFactory(param -> new ListCell<Article>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getNom());
                }
            }
        });

        actualiser();

        boutonModifier = new Button("Modifier");
        boutonFermer = new Button("Fermer");

        // Layout
        HBox buttonBox = new HBox(10, boutonModifier, boutonFermer);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10));

        BorderPane mainLayout = new BorderPane();
        VBox content = new VBox(10, listeArticles, buttonBox);
        content.setPadding(new Insets(15));

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);


        mainLayout.setTop(menuPrincipal.getMenuBar());


        mainLayout.setCenter(content);

        // Create scene
        this.root = mainLayout;
    }
    /**
     * Configure les actions des composants de la vue, notamment l'action du bouton Modifier.
     * Lorsqu'un article est sélectionné dans la liste et que le bouton Modifier est cliqué,
     * la vue de modification des détails de l'article est affichée.
     */
    @Override
    protected void configurerActions() {
        boutonModifier.setOnAction(e -> {
            Article selected = listeArticles.getSelectionModel().getSelectedItem();
            if (selected != null) {
                this.controlleurSupreme.vueModifierArticleDetail(selected);
            } else {
                showAlert("Aucun article sélectionné", "Veuillez sélectionner un article à modifier.");
            }
        });


    }


    /**
     * Actualise la vue en mettant à jour la liste des articles.
     * Cette méthode est appelée pour actualiser les données affichées dans la liste des articles.
     */
    @Override
    public void actualiser() {
        actualiserListeArticles();
    }
    /**
     * Met à jour la liste des articles affichée dans la vue.
     * Cette méthode récupère tous les articles via le contrôleur et les affiche dans la liste.
     */
    private void actualiserListeArticles() {
        ArrayList<Article> articles = this.controlleurSupreme.getAllArticles();
        listeArticles.setItems(FXCollections.observableArrayList(articles));
    }
    /**
     * Affiche une alerte avec un titre et un message d'erreur.
     * Cette méthode est utilisée pour avertir l'utilisateur d'une erreur, par exemple si aucun article
     * n'est sélectionné.
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
}