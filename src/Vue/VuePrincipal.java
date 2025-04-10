package Vue;
import Controleur.ControlleurSupreme;
import DAO.DaoFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class VuePrincipal extends Application{

    private Stage stage;
    private ControlleurSupreme controlleurSupreme;


    @Override
    public void start(Stage stage_p) {
        this.stage = stage_p;
        DaoFactory daoFactory = DaoFactory.getInstance("chouping","root","");

        ControlleurSupreme controlleurSupreme = new ControlleurSupreme(this,daoFactory);

        initialiser_fenetre();

    }

    private void initialiser_fenetre(){
        // Configuration du stage

        stage.setTitle("Chouping");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);


        javafx.scene.image.Image icone = new javafx.scene.image.Image("assets/global/icon.png");
        stage.getIcons().add(icone);

        afficherVueAcceuil();
    }

    /**
     * Sert à afficher la vue de l'acceuil au début
     */
    private void afficherVueAcceuil(){
        VueAccueil vueAccueil = new VueAccueil(controlleurSupreme);
        Scene scene = new Scene(vueAccueil.getRoot(),stage.getWidth(), stage.getHeight());

        // On peut ajouter un fichier CSS global ici
        // scene.getStylesheets().add(getClass().getResource("/styles/global.css").toExternalForm());

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

    /**
     * Pour initialiser le controleur suppreme dans la Vue
     * @param controlleurSupreme_p est l'instance du controleur supreme
     */
    public void getControlleurSupreme(ControlleurSupreme controlleurSupreme_p){
        this.controlleurSupreme = controlleurSupreme_p;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
