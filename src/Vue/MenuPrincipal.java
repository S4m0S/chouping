package Vue;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuPrincipal {
    private MenuBar menuBar;

    public MenuPrincipal(){

    }

    public void initialiserMenu(){
        this.menuBar = new MenuBar();

        Menu menuItemAchat = new Menu("Item");
        javafx.scene.control.MenuItem armes = new MenuItem("Armes");
        javafx.scene.control.MenuItem plastron = new MenuItem("Plastron");


        menuItemAchat.getItems().addAll(armes, plastron);

        this.menuBar.getMenus().add(menuItemAchat);
    }

    public MenuBar getMenuBar(){
        return this.menuBar;
    }
}
