package Vue;

import Controleur.ControlleurSupreme;
import DAO.DaoFactory;
import DAO.articleDAO;
import Modele.Article;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class VueBoutiqueCategorie extends VueBase {

    private BorderPane borderPane;
    private VBox contenuCentral;
    private String nomCategorie;

    public VueBoutiqueCategorie(ControlleurSupreme controlleurSupreme_p) {
        super(controlleurSupreme_p);
    }

    @Override
    protected void initialiserComposant() {
        this.nomCategorie = controlleurSupreme.getCategorieActuelle();
        borderPane = new BorderPane();

        String cssPath = "/src/resources/css/VueBoutiqueCategorie.css";
        try {
            String externalForm = getClass().getResource(cssPath).toExternalForm();
            borderPane.getStylesheets().add(externalForm);
            System.out.println("CSS chargé avec succès: " + cssPath);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du CSS: " + e.getMessage());
            System.err.println("Chemin CSS tenté: " + cssPath);
        }

        System.out.println("Chargement de la catégorie: " + this.nomCategorie);

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        contenuCentral = new VBox(30);
        contenuCentral.setPadding(new Insets(40));
        contenuCentral.setAlignment(Pos.TOP_CENTER);

        Label titreCategorie = new Label("Catégorie : " + nomCategorie);
        titreCategorie.getStyleClass().add("category-title");

        TilePane grillearticles = new TilePane();
        grillearticles.getStyleClass().add("grid-articles");
        grillearticles.setPrefColumns(3);
        grillearticles.setAlignment(Pos.CENTER);
        grillearticles.setHgap(20);
        grillearticles.setVgap(20);

        try {
            ArrayList<Article> articles = controlleurSupreme.getAllArticles();
            System.out.println(articles.size());
            int typeRecherche = getTypeCorrespondant(nomCategorie);

            for (Object obj : articles) {
                Article article = (Article) obj;

                if (article.getType() == typeRecherche) {
                    VBox boxarticle = new VBox(10);
                    boxarticle.getStyleClass().add("article-box");
                    boxarticle.setAlignment(Pos.CENTER);
                    boxarticle.setPadding(new Insets(10));
                    boxarticle.setMinWidth(200);
                    boxarticle.setMaxWidth(200);
                    boxarticle.setMinHeight(250);
                    boxarticle.setMaxHeight(250);

                    ImageView imageView = new ImageView();
                    try {
                        String imagePath = "/src/resources/articles/" + article.getNom().toLowerCase().replace(" ", "_") + ".jpg";
                        System.out.println("Tentative de chargement d'image: " + imagePath);
                        Image image = new Image(getClass().getResourceAsStream(imagePath));
                        if (image.isError()) {
                            imageView.setImage(new Image(getClass().getResourceAsStream("/src/resources/defaut.png")));
                        } else {
                            imageView.setImage(image);
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur de chargement d'image pour " + article.getNom() + ": " + e.getMessage());
                        imageView.setImage(new Image(getClass().getResourceAsStream("/src/resources/defaut.png")));
                    }

                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);


                    // 🎯 ZOOM au clic
                    final Article articleFinal = article;

                    imageView.setOnMouseEntered(e -> {
                        imageView.setEffect(new DropShadow(10, Color.BROWN));
                        imageView.setCursor(Cursor.HAND);
                        imageView.setScaleX(1.05);
                        imageView.setScaleY(1.05);
                    });
                    imageView.setOnMouseExited(e -> {
                        imageView.setEffect(null);
                        imageView.setScaleX(1.0);
                        imageView.setScaleY(1.0);
                    });



                    Label nomLabel = new Label(article.getNom());
                    nomLabel.getStyleClass().add("article-name");

                    Label prixLabel = new Label("Prix : " + article.getPrix() + " €");
                    prixLabel.getStyleClass().add("article-price");

                    Button acheterBtn = new Button("Acheter");
                    acheterBtn.getStyleClass().add("buy-button");

                    acheterBtn.setOnAction(e -> controlleurSupreme.accederVueArticle(article));

                    boxarticle.getChildren().addAll(imageView, nomLabel, prixLabel, acheterBtn);
                    grillearticles.getChildren().add(boxarticle);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des données: " + e.getMessage());
            e.printStackTrace();
        }

        contenuCentral.getChildren().addAll(titreCategorie, grillearticles);

        ScrollPane scrollPane = new ScrollPane(contenuCentral);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("content-scroll");
        borderPane.setCenter(scrollPane);

        HBox footer = new HBox();
        footer.getStyleClass().add("footer");
        footer.setAlignment(Pos.CENTER_RIGHT);
        Label versionLabel = new Label("Version 1.0");
        footer.getChildren().add(versionLabel);
        borderPane.setBottom(footer);

        try {
            // Texture de parchemin en fond par défaut
            Image backgroundImage = new Image(getClass().getResourceAsStream("/resources/backgrounds/parchment_texture.jpg"));
            BackgroundImage bgImage = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(100, 100, true, true, false, true)
            );
            borderPane.setBackground(new Background(bgImage));
        } catch (Exception e) {
            System.err.println("Erreur chargement texture parchemin: " + e.getMessage());
            borderPane.setStyle("-fx-background-color: linear-gradient(to bottom, #F5DEB3, #E6C9A8);");
        }

        this.root = borderPane;
    }

    @Override
    protected void configurerActions() {
        // à compléter si nécessaire
    }

    @Override
    public void actualiser() {
        // à compléter si nécessaire
    }

    private int getTypeCorrespondant(String categorie) {
        if (categorie == null) {
            System.out.println("Categorie Null");
            return -1;
        }
        switch (categorie.toLowerCase()) {
            case "armes":
                return 1;
            case "plastrons":
                return 2;
            case "potions":
                return 3;
            case "bottes":
                return 4;
            default:
                System.out.println("Catégorie non reconnue: " + categorie.toLowerCase());
                return -1;
        }
    }
}
