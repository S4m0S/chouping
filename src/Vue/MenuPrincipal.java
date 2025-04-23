package Vue;

import Modele.User;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.skin.MenuBarSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Controleur.ControlleurSupreme;


public class MenuPrincipal {
    private MenuBar menuBar;

    private ControlleurSupreme controlleurSupreme;

    public MenuPrincipal(ControlleurSupreme controlleurSupreme) {
        this.controlleurSupreme = controlleurSupreme;
        initialiserMenu();
    }


    public void initialiserMenu(){
        this.menuBar = new MenuBar();


        menuBar.getStylesheets().add(getClass().getResource("/src/resources/css/menu.css").toExternalForm());
        menuBar.getStyleClass().add("menu-bar");

        Button boutonAcceuil = new Button("Acceuil");



        boutonAcceuil.getStyleClass().addAll("menu-item","bouton-item");

        Menu menuItemAchat = new Menu("Article");
        javafx.scene.control.MenuItem armes = new MenuItem("Armes");
        javafx.scene.control.MenuItem plastrons = new MenuItem("Plastrons");
        javafx.scene.control.MenuItem potions = new MenuItem("Potions");
        javafx.scene.control.MenuItem bottes = new MenuItem("Bottes");

        menuItemAchat.getItems().addAll(armes, plastrons, potions, bottes);

        menuItemAchat.getStyleClass().add("menu-item");


        Image imageIconCompte = new Image("resources/menu/compteIcon.png");
        ImageView vueIconCompte = new ImageView(imageIconCompte);


        vueIconCompte.setFitHeight(20);
        vueIconCompte.setFitWidth(20);

        Button boutonCompte = new Button();
        boutonCompte.setGraphic(vueIconCompte);
        boutonCompte.getStyleClass().addAll("menu-item","bouton-item");


        Button boutonPanier = new Button("Panier");

        boutonPanier.getStyleClass().addAll("menu-item","bouton-item");



        armes.setOnAction(e -> controlleurSupreme.accederCategorie("Armes"));
        bottes.setOnAction(e -> controlleurSupreme.accederCategorie("Bottes"));
        plastrons.setOnAction(e -> controlleurSupreme.accederCategorie("Plastrons"));
        potions.setOnAction(e -> controlleurSupreme.accederCategorie("Potions"));
        boutonAcceuil.setOnAction(e -> {
            controlleurSupreme.accederAcceuil();
        });
        boutonCompte.setOnAction(e-> controlleurSupreme.accederCompte());
        boutonPanier.setOnAction(e-> controlleurSupreme.accederPanier());


        this.menuBar.getMenus().addAll(createMenuFromButton(boutonAcceuil),menuItemAchat, createMenuFromButton(boutonCompte),createMenuFromButton(boutonPanier));


        if (controlleurSupreme.getClient()!=null){
            Button boutonCommandes = new Button("Mes Commandes");
            boutonCommandes.getStyleClass().addAll("menu-item","bouton-item");
            boutonCommandes.setOnAction(e-> controlleurSupreme.afficherCommande());
            this.menuBar.getMenus().add(createMenuFromButton(boutonCommandes));
        }

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

        configurerCentrage();

    }

    private void configurerCentrage() {
        // Force le centrage horizontal
        this.menuBar.setMaxWidth(Double.MAX_VALUE);
        MenuBarSkin skin = new MenuBarSkin(menuBar);
        skin.getNode().setStyle("-fx-alignment: CENTER;");
        menuBar.setSkin(skin);
    }

    private Menu createMenuFromButton(Button button) {
        Menu menu = new Menu();
        menu.setGraphic(button);
        return menu;
    }

    public MenuBar getMenuBar(){
        return this.menuBar;
    }
}
