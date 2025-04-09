package Vue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class VuePrincipal extends Application{

    private Stage stage;

    @Override
    public void start(Stage stage_p) {
        this.stage = stage_p;
        initialiser_fenetre();

    }

    private void initialiser_fenetre(){
        // Configuration du stage
        stage.setTitle("Chouping");
        stage.setMinHeight(1000);
        stage.setMaxHeight(1000);


        javafx.scene.image.Image icone = new javafx.scene.image.Image("assets/global/icon.png");
        stage.getIcons().add(icone);

        afficherVueAcceuil();
    }

    /**
     * Sert à afficher la vue de l'acceuil au début
     */
    private void afficherVueAcceuil(){
        VueAccueil vueAccueil = new VueAccueil();
        Scene scene = new Scene(vueAccueil.getRoot(),stage.getWidth(), stage.getHeight());

        stage.setScene(scene);
        stage.show();
    }


    /**
     * Pour afficher n'importe quel vue
     * @param la vue à afficher est à passer en paremetre
     */
    private void afficherVue(VueBase vue){
        Scene scene = new Scene(vue.getRoot(),stage.getWidth(),stage.getHeight());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
