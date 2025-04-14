package Vue;

import Controleur.ControlleurSupreme;
import Modele.Client;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.sql.Date;
import java.time.LocalDate;

public class VueCreationCompte extends VueBase {

    private TextField nomField, mailField;
    private Spinner<Integer> classeSpinner;
    private Spinner<Double> monnaieSpinner;
    private DatePicker dateNaissancePicker;
    private Button submitButton;

    public VueCreationCompte(ControlleurSupreme controlleurSupreme) {
        super(controlleurSupreme);
    }

    @Override
    protected void initialiserComposant() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("create-client-view");
        borderPane.getStylesheets().add(getClass().getResource("/src/resources/css/creationCompte.css").toExternalForm());

        // Menu en haut
        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        // Centre - Formulaire de création
        GridPane formGrid = new GridPane();
        formGrid.getStyleClass().add("create-client-form-grid");

        // Titre
        Label titleLabel = new Label("Nouveau Client");
        titleLabel.getStyleClass().add("create-client-title");
        GridPane.setColumnSpan(titleLabel, 2);
        formGrid.add(titleLabel, 0, 0);

        // Champs du formulaire
        addFormField(formGrid, "Nom*:", nomField = new TextField(), 1);
        addFormField(formGrid, "Email*:", mailField = new TextField(), 2);
        addFormField(formGrid, "Classe:", classeSpinner = new Spinner<>(1, 3, 1), 3);
        addFormField(formGrid, "Monnaie initiale:", monnaieSpinner = new Spinner<>(0.0, 10000.0, 0.0, 0.5), 4);
        addFormField(formGrid, "Date de naissance:", dateNaissancePicker = new DatePicker(LocalDate.now().minusYears(18)), 5);

        // Bouton de soumission
        submitButton = new Button("Créer le client");
        submitButton.getStyleClass().add("create-client-submit-button");
        GridPane.setColumnSpan(submitButton, 2);
        formGrid.add(submitButton, 0, 6);

        borderPane.setCenter(formGrid);
        this.root = borderPane;
    }

    private void addFormField(GridPane grid, String labelText, Control field, int row) {
        Label label = new Label(labelText);
        label.getStyleClass().add("create-client-label");

        if (field instanceof TextField) {
            field.getStyleClass().add("create-client-field");
        } else if (field instanceof Spinner) {
            field.getStyleClass().add("create-client-spinner");
        } else {
            field.getStyleClass().add("create-client-date-picker");
        }

        grid.add(label, 0, row);
        grid.add(field, 1, row);
    }

    @Override
    protected void configurerActions() {
        submitButton.setOnAction(e -> {
            // Validation des champs obligatoires
            if (nomField.getText().isEmpty() || mailField.getText().isEmpty()) {
                showAlert("Erreur", "Les champs marqués d'un * sont obligatoires");
                return;
            }

            // Création du nouveau client
            Client nouveauClient = new Client(
                    nomField.getText(),
                    mailField.getText(),
                    classeSpinner.getValue(),
                    monnaieSpinner.getValue(),
                    Date.valueOf(dateNaissancePicker.getValue())
            );

            // Enregistrement via le contrôleur
            if (controlleurSupreme.creerClient(nouveauClient)) {
                showAlert("Succès", "Client créé avec succès");
                resetForm();
            } else {
                showAlert("Erreur", "Échec de la création du client");
            }
        });
    }

    private void resetForm() {
        nomField.clear();
        mailField.clear();
        classeSpinner.getValueFactory().setValue(1);
        monnaieSpinner.getValueFactory().setValue(0.0);
        dateNaissancePicker.setValue(LocalDate.now().minusYears(18));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void actualiser() {
        // Réinitialisation du formulaire si nécessaire
        resetForm();
    }
}