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
/**
 * VueConnection est la vue permettant à l'utilisateur de se connecter à l'application.
 * Elle hérite de la classe VueBase et affiche un écran de connexion avec un champ pour le nom d'utilisateur,
 * un champ pour le mot de passe, et un bouton pour se connecter.
 * L'utilisateur peut aussi accéder à la page de création de compte à partir de cette vue.
 *
 * @see VueBase
 * @see Controleur.ControlleurSupreme
 */
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
    /**
     * Initialise les composants de l'interface graphique pour la vue de connexion.
     * Configure le fond d'écran, les styles, et la disposition des éléments (champs de saisie, boutons, etc.).
     */
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
    /**
     * Configure les actions associées aux boutons de la vue de connexion.
     * Le bouton de connexion appelle la méthode de connexion du contrôleur.
     * Le bouton de création de compte permet d'accéder à la page de création de compte.
     */
    @Override
    protected void configurerActions() {
        loginButton.setOnAction(e ->
                controlleurSupreme.requestConnection(pseudoField.getText(), passwordField.getText(), this)
        );
        creerCompteButton.setOnAction(e ->
                controlleurSupreme.accederCreationCompte());
    }
    /**
     * Change le texte du label d'erreur dans la vue de connexion.
     * Ce texte peut être utilisé pour afficher un message d'erreur à l'utilisateur.
     *
     * @param s Le message d'erreur à afficher.
     */
    public void changeErrorLabel(String s) {
        this.errorLabel.setText(s);
    }
    /**
     * Actualise la vue en réinitialisant les champs de saisie et le label d'erreur.
     * Cette méthode est appelée pour effacer les informations lorsque l'utilisateur
     * quitte la vue ou lorsqu'il y a un nouvel essai de connexion.
     */
    @Override
    public void actualiser() {
        // Réinitialiser les champs si nécessaire
        pseudoField.setText("");
        passwordField.setText("");
        errorLabel.setText("");
    }
}