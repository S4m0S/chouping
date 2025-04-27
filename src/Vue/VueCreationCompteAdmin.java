package Vue;

import Controleur.ControlleurSupreme;
import Modele.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
/**
 * VueCreationCompteAdmin représente la vue pour la création d'un compte administrateur.
 * Elle inclut les composants de l'interface utilisateur et les actions nécessaires pour créer un nouvel utilisateur admin.
 *
 * Cette vue est utilisée par le contrôleur pour interagir avec l'utilisateur et gérer la création de comptes.
 * Elle fournit un formulaire pour saisir un pseudo et un mot de passe pour le compte admin.
 */
public class VueCreationCompteAdmin extends VueBase {

    private TextField pseudoField;
    private PasswordField passwordField;
    private Button submitButton;

    public VueCreationCompteAdmin(ControlleurSupreme controlleurSupreme) {
        super(controlleurSupreme);
    }
    /**
     * Initialise les composants de la vue de création de compte admin.
     * Cette méthode configure la mise en page, les styles et les champs du formulaire.
     */
    @Override
    protected void initialiserComposant() {
        // Main container with background
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add(getClass().getResource("/src/resources/css/adminCreation.css").toExternalForm());

        // Menu at the top (if needed)
        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        // Center - Admin creation form
        GridPane formGrid = new GridPane();
        formGrid.getStyleClass().add("admin-form-grid");
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(30));
        formGrid.setAlignment(Pos.CENTER);

        // Title
        Label titleLabel = new Label("Création Compte Admin");
        titleLabel.getStyleClass().add("admin-title");
        GridPane.setColumnSpan(titleLabel, 2);
        formGrid.add(titleLabel, 0, 0);

        // Username field
        addFormField(formGrid, "Pseudo Admin*:", pseudoField = new TextField(), 1);
        pseudoField.setPromptText("Entrez le pseudo admin");

        // Password field
        addFormField(formGrid, "Mot de passe*:", passwordField = new PasswordField(), 2);
        passwordField.setPromptText("Entrez le mot de passe");

        // Submit button
        submitButton = new Button("Créer le compte admin");
        submitButton.getStyleClass().add("admin-submit-button");
        GridPane.setColumnSpan(submitButton, 2);
        formGrid.add(submitButton, 0, 3);

        // Center the form in a container
        StackPane formContainer = new StackPane(formGrid);
        formContainer.setPadding(new Insets(50));

        // Add to main layout
        borderPane.setCenter(formContainer);
        this.root = borderPane;
    }
    /**
     * Ajoute un champ de formulaire avec une étiquette au GridPane fourni.
     *
     * @param grid Le GridPane où le champ sera ajouté.
     * @param labelText Le texte à afficher dans l'étiquette.
     * @param field Le champ de formulaire (TextField, PasswordField, etc.) à ajouter.
     * @param row L'index de la ligne où le champ sera ajouté dans le GridPane.
     */
    private void addFormField(GridPane grid, String labelText, Control field, int row) {
        Label label = new Label(labelText);
        label.getStyleClass().add("admin-label");

        if (field instanceof TextField) {
            field.getStyleClass().add("admin-text-field");
        } else if (field instanceof PasswordField) {
            field.getStyleClass().add("admin-password-field");
        }

        grid.add(label, 0, row);
        grid.add(field, 1, row);
    }
    /**
     * Configure les actions associées aux composants, telles que le bouton de soumission.
     * Cette méthode gère la soumission du formulaire, la validation des entrées et la création d'un nouvel utilisateur admin.
     */
    @Override
    protected void configurerActions() {
        submitButton.setOnAction(e -> {
            if (pseudoField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
                return;
            }

            // Create admin user (assuming role 1 is for admin)
            User adminUser = new User(pseudoField.getText(), 1);
            adminUser.setPassword(passwordField.getText());

            if (controlleurSupreme.creerUtilisateur(adminUser)) {
                showAlert("Succès", "Compte admin créé avec succès", Alert.AlertType.INFORMATION);
                resetForm();
            } else {
                showAlert("Erreur", "Échec de la création du compte admin", Alert.AlertType.ERROR);
            }
        });
    }
    /**
     * Affiche une alerte avec un titre, un message et un type d'alerte.
     *
     * @param title Le titre de l'alerte.
     * @param message Le message à afficher dans l'alerte.
     * @param type Le type d'alerte (ERROR, INFORMATION, etc.).
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply CSS if needed
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/src/resources/css/adminCreation.css").toExternalForm());

        alert.showAndWait();
    }
    /**
     * Réinitialise les champs du formulaire à leur état initial.
     */
    private void resetForm() {
        pseudoField.clear();
        passwordField.clear();
    }

    @Override
    public void actualiser() {
        resetForm();
    }
}