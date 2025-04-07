package Vue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;


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
//        MenuBar menuBar = new creerMenuBar();
//        root.setTop(menuBar);

        VBox contenuPrincipal = new VBox(10);
        contenuPrincipal.setStyle("-fx-padding: 20px;");

        Label titreLabel = new Label("Bienvenue dans mon application");



        root.setCenter(contenuPrincipal);


        Scene scene = new Scene(root, 800, 600);

        // Configuration du stage
        stage.setTitle("Mon Application JavaFX");
        stage.setScene(scene);
        stage.show();




    }

    public static void main(String[] args) {
        launch(args);
    }
}
