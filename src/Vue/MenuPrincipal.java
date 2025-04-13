package Vue;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
