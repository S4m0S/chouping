package Vue;

import Controleur.ControlleurSupreme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class VueConnection extends VueBase {

    private VBox contenuCentral;
    private Button loginButton;
    private PasswordField passwordField;
    private TextField pseudoField;
    private Label errorLabel;
    private Button creerCompteButton;

    public VueConnection(ControlleurSupreme controlleurSupreme_p) {
        super(controlleurSupreme_p);
    }

    @Override
    protected void initialiserComposant() {
        // Création du fond avec l'image
        Image backgroundImage = new Image("file:src/resources/background-login.jpg.png");
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );

        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(background));
        borderPane.getStylesheets().add(getClass().getResource("/resources/css/connection.css").toExternalForm());

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        // Conteneur principal avec transparence
        contenuCentral = new VBox(20);
        contenuCentral.setPadding(new Insets(30));
        contenuCentral.setAlignment(Pos.CENTER);
        contenuCentral.setBackground(new Background(new BackgroundFill(
                Color.rgb(20, 20, 30, 0.7),
                new CornerRadii(15),
                Insets.EMPTY
        )));
        contenuCentral.setBorder(new Border(new BorderStroke(
                Color.web("#5a4a2f"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(15),
                new BorderWidths(3)
        )));
        contenuCentral.setMaxWidth(400);

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
        errorLabel.getStyleClass().add("erreur-connexion");

        creerCompteButton = new Button("Créer un compte");
        creerCompteButton.getStyleClass().add("lien-inscription");

        // Ajout des composants au VBox
        contenuCentral.getChildren().addAll(
                titre,
                labelUsername,
                pseudoField,
                labelPassword,
                passwordField,
                loginButton,
                errorLabel,
                creerCompteButton
        );

        // Centrer le contenu
        StackPane centerPane = new StackPane(contenuCentral);
        centerPane.setPadding(new Insets(50));
        borderPane.setCenter(centerPane);

        this.root = borderPane;
    }

    @Override
    protected void configurerActions() {
        loginButton.setOnAction(e ->
                controlleurSupreme.requestConnection(pseudoField.getText(), passwordField.getText(), this)
        );
        creerCompteButton.setOnAction(e ->
                controlleurSupreme.accederCreationCompte());
    }

    public void changeErrorLabel(String s) {
        this.errorLabel.setText(s);
    }

    @Override
    public void actualiser() {
        // Réinitialiser les champs si nécessaire
        pseudoField.setText("");
        passwordField.setText("");
        errorLabel.setText("");
    }
}