package Vue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class vuePrincipal extends Application{

    private Stage stage;

    @Override
    public void start(Stage stage_p) throws Exception {
        this.stage = stage_p;
        initialiser_fenetre();
    }

    private void initialiser_fenetre(){
        // Conteneur principal
        BorderPane root = new BorderPane();

        // Barre de menu
        javafx.scene.control.MenuBar menuBar = creerMenuBar();
        root.setTop(menuBar);

        VBox conteneurPrincipal = new VBox(10);
        conteneurPrincipal.setStyle("-fx-padding: 20px;");

        javafx.scene.control.Label titreLabel = new javafx.scene.control.Label("Bienvenue dans mon armurerie");
        titreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");


        javafx.scene.image.Image icone = new javafx.scene.image.Image("assets/global/icon.png");
        stage.getIcons().add(icone);

        conteneurPrincipal.getChildren().add(titreLabel);
        root.setCenter(conteneurPrincipal);


        Scene scene = new Scene(root, 800, 600);

        // Configuration du stage
        stage.setTitle("Chouping");
        stage.setScene(scene);
        stage.show();
    }

    public javafx.scene.control.MenuBar creerMenuBar(){
        MenuBar menubar = new MenuBar();

        Menu menuItemAchat = new Menu("Item");
        javafx.scene.control.MenuItem armes = new MenuItem("Armes");
        javafx.scene.control.MenuItem plastron = new MenuItem("Plastron");


        menuItemAchat.getItems().addAll(armes, plastron);

        menubar.getMenus().add(menuItemAchat);


        return menubar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
