package Vue;

import Controleur.ControlleurSupreme;
import DAO.DaoFactory;
import DAO.articleDAO;
import Modele.Article;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class VueBoutiqueCategorie extends VueBase {

    private BorderPane borderPane;
    private VBox contenuCentral;
    private String nomCategorie;

    public VueBoutiqueCategorie(ControlleurSupreme controlleurSupreme_p, String nomCategorie_p) {
        super(controlleurSupreme_p);
        this.nomCategorie = nomCategorie_p;
    }

    @Override
    protected void initialiserComposant() {
        borderPane = new BorderPane();

        // Chemin vers le fichier CSS - à ajuster selon votre structure de projet
        String cssPath = "/resources/css/VueBoutiqueCategorie.css";
        try {
            String externalForm = getClass().getResource(cssPath).toExternalForm();
            borderPane.getStylesheets().add(externalForm);
            System.out.println("CSS chargé avec succès: " + cssPath);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du CSS: " + e.getMessage());
            System.err.println("Chemin CSS tenté: " + cssPath);
        }

        // Logs pour débogage
        System.out.println("Chargement de la catégorie: " + nomCategorie);

        // Barre de menu
        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        // Contenu central
        contenuCentral = new VBox(30);
        contenuCentral.setPadding(new Insets(40));
        contenuCentral.setAlignment(Pos.TOP_CENTER);

        Label titreCategorie = new Label("Catégorie : " + nomCategorie);
        titreCategorie.getStyleClass().add("category-title");

        TilePane grillearticles = new TilePane();
        grillearticles.getStyleClass().add("grid-articles");
        grillearticles.setPrefColumns(3);
        grillearticles.setAlignment(Pos.CENTER);

        // Chargement des articles depuis la BDD
        try {
            DaoFactory daoFactory = DaoFactory.getInstance("chouping", "root", "");
            articleDAO articleDAO = new articleDAO(daoFactory);
            ArrayList<Object> articles = articleDAO.getAll();

            int typeRecherche = getTypeCorrespondant(nomCategorie);
            System.out.println("Type de catégorie identifié: " + typeRecherche);
            System.out.println("Nombre d'articles trouvés: " + articles.size());

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

                    // Image
                    ImageView imageView = new ImageView();
                    try {
                        String imagePath = "/resources/articles/" + article.getNom().toLowerCase().replace(" ", "_") + ".png";
                        System.out.println("Tentative de chargement d'image: " + imagePath);
                        Image image = new Image(getClass().getResourceAsStream(imagePath));
                        if (image.isError()) {
                            System.out.println("Image non trouvée, utilisation de l'image par défaut");
                            imageView.setImage(new Image(getClass().getResourceAsStream("/resources/defaut.png")));
                        } else {
                            imageView.setImage(image);
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur de chargement d'image pour " + article.getNom() + ": " + e.getMessage());
                        imageView.setImage(new Image(getClass().getResourceAsStream("/resources/defaut.png")));
                    }
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);

                    Label nomLabel = new Label(article.getNom());
                    nomLabel.getStyleClass().add("article-name");

                    Label prixLabel = new Label("Prix : " + article.getPrix() + " €");
                    prixLabel.getStyleClass().add("article-price");

                    Button acheterBtn = new Button("Acheter");
                    acheterBtn.getStyleClass().add("buy-button");

                    acheterBtn.setOnAction(event -> {
                        // Action d'achat (à personnaliser)
                        System.out.println("Achat de l'article : " + article.getNom());
                    });

                    boxarticle.getChildren().addAll(imageView, nomLabel, prixLabel, acheterBtn);
                    grillearticles.getChildren().add(boxarticle);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des données: " + e.getMessage());
            e.printStackTrace();
        }

        contenuCentral.getChildren().addAll(titreCategorie, grillearticles);
        borderPane.setCenter(contenuCentral);

        // Pied de page
        HBox footer = new HBox();
        footer.getStyleClass().add("footer");
        footer.setAlignment(Pos.CENTER_RIGHT);
        Label versionLabel = new Label("Version 1.0");
        footer.getChildren().add(versionLabel);
        borderPane.setBottom(footer);

        // Fond d'écran facultatif
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/resources/backgrounds/shop_bg.png"));
            if (!backgroundImage.isError()) {
                BackgroundImage bgImage = new BackgroundImage(
                        backgroundImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(100, 100, true, true, true, false)
                );
                borderPane.setBackground(new Background(bgImage));
            } else {
                System.out.println("Erreur lors du chargement de l'image de fond");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image de fond: " + e.getMessage());
        }

        this.root = borderPane;
    }

    @Override
    protected void configurerActions() {
        // À remplir si besoin plus tard (panier, détails, etc.)
    }

    @Override
    public void actualiser() {
        // À implémenter si besoin de rafraîchir la vue
    }

    private int getTypeCorrespondant(String categorie) {
        if(categorie == null){
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
            // Ajouter plus de cas selon les catégories
            default:
                System.out.println("Catégorie non reconnue: " + categorie.toLowerCase());
                return -1;
        }
    }
}