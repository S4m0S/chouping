package Vue;
import javafx.geometry.Insets;
import Controleur.ControlleurSupreme;
import Modele.Client;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.sql.Date;
import java.time.LocalDate;

public class VueCreationCompte extends VueBase {

    private TextField nomField, mailField;
    private Spinner<Integer> classeSpinner;
    private Spinner<Double> monnaieSpinner;
    private DatePicker dateNaissancePicker;
    private Button submitButton;
    private VBox contenuCentral;

    public VueCreationCompte(ControlleurSupreme controlleurSupreme) {
        super(controlleurSupreme);
    }

    @Override
    protected void initialiserComposant() {
        // Création du fond avec l'image

        contenuCentral = new VBox(30);
        contenuCentral.setPadding(new Insets(40));
        contenuCentral.setAlignment(Pos.TOP_CENTER);

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
        borderPane.getStylesheets().add(getClass().getResource("/resources/css/creationCompte.css").toExternalForm());

        // Menu en haut
        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());

        // Centre - Formulaire de création dans un conteneur stylisé
        GridPane formGrid = new GridPane();
        formGrid.getStyleClass().add("create-client-form-grid");

        // Ajout de padding et d'espacement
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(30));

        // Conteneur pour effets visuels
        StackPane formContainer = new StackPane(formGrid);
        formContainer.setPadding(new Insets(50));

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

        contenuCentral.getChildren().add(formContainer);

        ScrollPane scrollPane = new ScrollPane(contenuCentral);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("content-scroll");
        borderPane.setCenter(scrollPane);

        this.root = borderPane;
    }

    private void addFormField(GridPane grid, String labelText, Control field, int row) {
        Label label = new Label(labelText);
        label.getStyleClass().add("create-client-label");

        if (field instanceof TextField) {
            field.getStyleClass().add("create-client-field");
            ((TextField) field).setPromptText("Entrez " + labelText.replace("*", "").toLowerCase());
        } else if (field instanceof Spinner) {
            field.getStyleClass().add("create-client-spinner");
            // Style spécifique pour les Spinner
            ((Spinner<?>) field).getEditor().getStyleClass().add("spinner-text-field");
        } else {
            field.getStyleClass().add("create-client-date-picker");
            // Style spécifique pour le DatePicker
            ((DatePicker) field).getEditor().getStyleClass().add("date-picker-text-field");
        }

        grid.add(label, 0, row);
        grid.add(field, 1, row);
    }

    @Override
    protected void configurerActions() {
        submitButton.setOnAction(e -> {
            if (nomField.getText().isEmpty() || mailField.getText().isEmpty()) {
                showStyledAlert("Erreur", "Les champs marqués d'un * sont obligatoires", Alert.AlertType.ERROR);
                return;
            }

            Client nouveauClient = new Client(
                    nomField.getText(),
                    mailField.getText(),
                    classeSpinner.getValue(),
                    monnaieSpinner.getValue(),
                    Date.valueOf(dateNaissancePicker.getValue())
            );

            if (controlleurSupreme.creerClient(nouveauClient)) {
                showStyledAlert("Succès", "Client créé avec succès", Alert.AlertType.INFORMATION);
                resetForm();
            } else {
                showStyledAlert("Erreur", "Échec de la création du client", Alert.AlertType.ERROR);
            }
        });
    }

    private void showStyledAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Appliquer le style CSS à l'alerte
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/resources/css/creationCompte.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    private void resetForm() {
        nomField.clear();
        mailField.clear();
        classeSpinner.getValueFactory().setValue(1);
        monnaieSpinner.getValueFactory().setValue(0.0);
        dateNaissancePicker.setValue(LocalDate.now().minusYears(18));
    }

    @Override
    public void actualiser() {
        resetForm();
    }
}