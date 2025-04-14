package Vue;

import Controleur.ControlleurSupreme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class VueConnection extends VueBase{

    private VBox contenuCentral;
    private Button loginButton;
    private PasswordField passwordField;
    private TextField pseudoField;
    private Label errorLabel;
    private Button creerCompteButton;


    public VueConnection(ControlleurSupreme controlleurSupreme_p){
        super(controlleurSupreme_p);
    }

    @Override
    protected void initialiserComposant() {
        BorderPane borderPane = new BorderPane();

        borderPane.getStylesheets().add(getClass().getResource("/src/resources/css/connection.css").toExternalForm());

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());


        contenuCentral = new VBox(20);
        contenuCentral.setPadding(new Insets(30));
        contenuCentral.setAlignment(Pos.CENTER);



// Création des composants

        Label titre = new Label("Connexion");
        titre.getStyleClass().add("titre-connexion");

        Label labelUsername = new Label("Nom d'utilisateur:");
        labelUsername.getStyleClass().add("label-connexion");

        pseudoField = new TextField();
        pseudoField.getStyleClass().add("champ-connexion");
        pseudoField.setPromptText("Entrez votre nom d'utilisateur");

        Label labelPassword = new Label("Mot de passe:");
        labelPassword.getStyleClass().add("label-connexion");

        passwordField = new PasswordField();
        passwordField.getStyleClass().add("champ-connexion");
        passwordField.setPromptText("Entrez votre mot de passe");

        loginButton = new Button("Se connecter");
        loginButton.getStyleClass().add("bouton-connexion");

        errorLabel = new Label("");
        errorLabel.getStyleClass().add("label-connexion");

        creerCompteButton = new Button("Creer un compte");
        creerCompteButton.getStyleClass().add("bouton-connexion");

        // Ajout des composants au VBox
        contenuCentral.getChildren().addAll(
                titre,
                labelUsername,
                pseudoField,
                labelPassword,
                passwordField,
                loginButton,
                creerCompteButton,
                errorLabel
        );


        borderPane.setCenter(contenuCentral);

        this.root = borderPane;
    }

    @Override
    protected void configurerActions() {
        loginButton.setOnAction( e->
                controlleurSupreme.requestConnection(pseudoField.getText(),passwordField.getText(),this)
        );
        creerCompteButton.setOnAction(e->
                controlleurSupreme.accederCreationCompte());
    }

    public void changeErrorLabel(String s){
        this.errorLabel.setText(s);
    }

    @Override
    public void actualiser() {

    }
}
