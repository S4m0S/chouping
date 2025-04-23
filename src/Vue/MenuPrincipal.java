package Vue;

import Modele.User;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import Controleur.ControlleurSupreme;


public class MenuPrincipal {
    private MenuBar menuBar;

    private ControlleurSupreme controlleurSupreme;

    public MenuPrincipal(ControlleurSupreme controlleurSupreme) {
        this.controlleurSupreme = controlleurSupreme;
        initialiserMenu();
    }

    public void initialiserMenu() {
        menuBar = new MenuBar();
        menuBar.getStylesheets().add(getClass().getResource("/src/resources/css/menu.css").toExternalForm());
        menuBar.setId("menu-bar");

        // Bouton Accueil
        Button boutonAccueil = new Button("Accueil");
        boutonAccueil.getStyleClass().addAll("menu-item", "bouton-item");

        // Menu Articles
        Menu menuItemAchat = new Menu("Articles");
        MenuItem armes = new MenuItem("Armes");
        MenuItem plastrons = new MenuItem("Plastrons");
        MenuItem potions = new MenuItem("Potions");
        MenuItem bottes = new MenuItem("Bottes");
        menuItemAchat.getItems().addAll(armes, plastrons, potions, bottes);
        menuItemAchat.getStyleClass().add("menu-item");

        // Bouton Compte (avec icône)
        ImageView vueIconCompte = new ImageView(new Image("resources/menu/compteIcon.png"));
        vueIconCompte.setFitHeight(20);
        vueIconCompte.setFitWidth(20);
        Button boutonCompte = new Button();
        boutonCompte.setGraphic(vueIconCompte);
        boutonCompte.getStyleClass().addAll("menu-item", "bouton-item");

        // Bouton Panier (avec icône)
        ImageView vueIconPanier = new ImageView(new Image("resources/paniers.png"));
        vueIconPanier.setFitHeight(20);
        vueIconPanier.setFitWidth(20);
        Button boutonPanier = new Button();
        boutonPanier.setGraphic(vueIconPanier);
        boutonPanier.getStyleClass().addAll("menu-item", "bouton-item");

        // Ajout des menus
        menuBar.getMenus().addAll(
                createMenuFromButton(boutonAccueil),
                menuItemAchat,
                createMenuFromButton(boutonCompte),
                createMenuFromButton(boutonPanier)
        );

        // Bouton "Mes Commandes" si connecté
        if (controlleurSupreme.getClient() != null) {
            Button boutonCommandes = new Button("Mes Commandes");
            boutonCommandes.getStyleClass().addAll("menu-item", "bouton-item");
            boutonCommandes.setOnAction(e -> controlleurSupreme.afficherCommande());
            menuBar.getMenus().add(createMenuFromButton(boutonCommandes));
        }

        // Actions
        boutonAccueil.setOnAction(e -> controlleurSupreme.accederAcceuil());
        boutonCompte.setOnAction(e -> controlleurSupreme.accederCompte());
        boutonPanier.setOnAction(e -> controlleurSupreme.accederPanier());
        armes.setOnAction(e -> controlleurSupreme.accederCategorie("Armes"));
        bottes.setOnAction(e -> controlleurSupreme.accederCategorie("Bottes"));
        plastrons.setOnAction(e -> controlleurSupreme.accederCategorie("Plastrons"));
        potions.setOnAction(e -> controlleurSupreme.accederCategorie("Potions"));
        if (controlleurSupreme.getUser()!=null && controlleurSupreme.getUser().getUserType()==1){
            Button boutonAjouterArticle = new Button("Créer Article");
            boutonAjouterArticle.getStyleClass().addAll("menu-item","bouton-item");
            boutonAjouterArticle.setOnAction(e-> controlleurSupreme.afficherAjouterArticle());
            this.menuBar.getMenus().add(createMenuFromButton(boutonAjouterArticle));

            Button boutonModifierArticle = new Button("Modifier Article");
            boutonModifierArticle.getStyleClass().addAll("menu-item","bouton-item");
            boutonModifierArticle.setOnAction(e-> controlleurSupreme.afficherModifierArticle());
            this.menuBar.getMenus().add(createMenuFromButton(boutonModifierArticle));
        }


    }



    private Menu createMenuFromButton(Button button) {
        Menu menu = new Menu();
        menu.setGraphic(button);
        return menu;
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }
}
