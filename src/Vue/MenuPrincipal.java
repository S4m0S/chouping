package Vue;

import Modele.User;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuPrincipal {
    private MenuBar menuBar;
    private User user;

    public MenuPrincipal(User user_p){
        initialiserMenu();
        this.user = user_p;
    }

    public void initialiserMenu(){
        this.menuBar = new MenuBar();

        Menu menuItemAchat = new Menu("Item");
        javafx.scene.control.MenuItem armes = new MenuItem("Armes");
        javafx.scene.control.MenuItem plastrons = new MenuItem("Plastrons");
        javafx.scene.control.MenuItem potions = new MenuItem("Potions");
        javafx.scene.control.MenuItem bottes = new MenuItem("Bottes");

        menuItemAchat.getItems().addAll(armes, plastrons, potions, bottes);


        Image imageIconCompte = new Image("/src/assets/menu/compteIcon.png");
        ImageView vueIconCompte = new ImageView(imageIconCompte);

        vueIconCompte.setFitHeight(20);
        vueIconCompte.setFitWidth(20);

        Menu menuCompte = new Menu();
        menuCompte.setGraphic(vueIconCompte);

        if()


        menuCompte.setText("");





        this.menuBar.getMenus().addAll(menuItemAchat,menuCompte);
    }

    public MenuBar getMenuBar(){
        return this.menuBar;
    }
}
