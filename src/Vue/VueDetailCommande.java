package Vue;

import Controleur.ControlleurSupreme;
import Modele.Article;
import Modele.Commande;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VueDetailCommande extends VueBase {

    public VueDetailCommande(ControlleurSupreme controlleurSupreme, Commande commande) {
        super(controlleurSupreme);
        initialiserComposant(commande);
    }

    private void initialiserComposant(Commande commande) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        // En-tête
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Label titre = new Label(String.format("Commande #%d - %s",
                commande.getId_commande(),
                sdf.format(commande.getDate_achat())));
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Liste des articles
        VBox listeArticles = new VBox(10);
        ArrayList<Article> articles = controlleurSupreme.getaArticlesCommande(commande);

        if (articles != null) {
            for (Article article : articles) {
                Label lblarticle = new Label(String.format("- %s (%.2f pièces d'or)",
                        article.getNom(), article.getPrix()));
                listeArticles.getChildren().add(lblarticle);
            }
        }

        // Conteneur principal
        VBox contenu = new VBox(20, titre, new Label("Articles commandés:"), listeArticles);
        ScrollPane scrollPane = new ScrollPane(contenu);
        scrollPane.setFitToWidth(true);

        borderPane.setCenter(scrollPane);

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        this.root = borderPane;
    }

    @Override
    protected void initialiserComposant() {
        
    }

    @Override
    protected void configurerActions() {
        // Pas d'actions spécifiques nécessaires
    }

    @Override
    public void actualiser() {
        // La vue est statique une fois initialisée
    }
}