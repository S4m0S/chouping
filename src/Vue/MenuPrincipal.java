package Vue;

import Modele.User;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Controleur.ControlleurSupreme;


public class MenuPrincipal {
    private MenuBar menuBar;
    private User user;

    private ControlleurSupreme controlleurSupreme;

    public MenuPrincipal(User user_p,ControlleurSupreme controlleurSupreme) {
        this.controlleurSupreme = controlleurSupreme;
        initialiserMenu();
        this.user = user_p;
    }


    public void initialiserMenu(){
        this.menuBar = new MenuBar();

        Menu menuItemAchat = new Menu("Item");
        MenuItem armes = new MenuItem("Armes");
        MenuItem plastrons = new MenuItem("Plastrons");
        MenuItem potions = new MenuItem("Potions");
        MenuItem bottes = new MenuItem("Bottes");

        menuItemAchat.getItems().addAll(armes, plastrons, potions, bottes);

        menuItemAchat.setStyle("-fx-text-fill: white;"+
                "menu-item .label { -fx-text-fill: white; }");

        Image imageIconCompte = new Image("/src/resources/menu/compteIcon.png");
        ImageView vueIconCompte = new ImageView(imageIconCompte);

        vueIconCompte.setFitHeight(20);
        vueIconCompte.setFitWidth(20);

        Menu menuCompte = new Menu();
        menuCompte.setGraphic(vueIconCompte);

        menuBar.setStyle("-fx-background-color: transparent;");

        armes.setOnAction(e -> {
            controlleurSupreme.accederArmes(); // Appelle le contrôleur pour changer de vue
        });




        menuCompte.setText("");





        this.menuBar.getMenus().addAll(menuItemAchat,menuCompte);
    }

    public MenuBar getMenuBar(){
        return this.menuBar;
    }
}
