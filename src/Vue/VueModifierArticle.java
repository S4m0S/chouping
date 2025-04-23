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

public class VueModifierArticle extends VueBase {
    private ListView<Article> listeArticles;
    private Button boutonModifier;
    private Button boutonFermer;
    private VBox mainLayout;

    public VueModifierArticle(ControlleurSupreme controlleurSupreme_p) {
        super(controlleurSupreme_p);
    }

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



    @Override
    public void actualiser() {
        actualiserListeArticles();
    }

    private void actualiserListeArticles() {
        ArrayList<Article> articles = this.controlleurSupreme.getAllArticles();
        listeArticles.setItems(FXCollections.observableArrayList(articles));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}