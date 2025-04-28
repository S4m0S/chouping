package Vue;

import Controleur.ControlleurSupreme;
import Modele.Client;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * VueClient est une vue permettant d'afficher et de modifier les informations du client connecté.
 *
 * Cette vue contient :
 * - Un formulaire pré-rempli avec les données du client
 * - Un bouton pour enregistrer les modifications
 *
 * Auteurs : [Ton Nom]
 * Date : [Date du fichier]
 */

public class VueClient extends VueBase{

    private Client client;
    private TextField nomField, mailField;
    private Spinner<Integer> classeSpinner;
    private Spinner<Double> monnaieSpinner;
    private DatePicker dateNaissancePicker;
    private Button saveButton;
    private Button disconnect;

    public VueClient(ControlleurSupreme controlleurSupreme_p){
        super(controlleurSupreme_p);
        this.client = controlleurSupreme_p.getClient();
    }
    /**
     * Initialise tous les composants graphiques de la vue Client.
     */
    @Override
    protected void initialiserComposant() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("client-view"); // Style racine

        // Chargement du CSS
        borderPane.getStylesheets().add(
                getClass().getResource("/src/resources/css/client.css").toExternalForm()
        );

        // Menu en haut
        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        borderPane.setTop(menuPrincipal.getMenuBar());


        // Centre - Formulaire client
        GridPane formGrid = new GridPane();
        formGrid.getStyleClass().add("client-form-grid"); // Style pour le GridPane

        // Titre
        Label titleLabel = new Label("Profil Client");
        titleLabel.getStyleClass().add("client-title");
        GridPane.setColumnSpan(titleLabel, 2);
        formGrid.add(titleLabel, 0, 0);

        // Champs du formulaire avec styles
        addFormField(formGrid, "Nom:", nomField = new TextField(this.controlleurSupreme.getClient().getNom()), 1);
        addFormField(formGrid, "Email:", mailField = new TextField(this.controlleurSupreme.getClient().getMail()), 2);
        addFormField(formGrid, "Classe:", classeSpinner = new Spinner<>(1, 3, this.controlleurSupreme.getClient().getClasse()), 3);
        addFormField(formGrid, "Monnaie:", monnaieSpinner = new Spinner<>(0.0, 10000.0, this.controlleurSupreme.getClient().getMonnaie(), 0.5), 4);
        addFormField(formGrid, "Date de naissance:", dateNaissancePicker = new DatePicker(this.controlleurSupreme.getClient().getDate_naissance().toLocalDate()), 5);

        // Bouton de sauvegarde

        disconnect = new Button("Se décconecter");
        GridPane.setColumnSpan(disconnect, 3);
        formGrid.add(disconnect, 0, 7);

        saveButton = new Button("Enregistrer les modifications");
        saveButton.getStyleClass().add("client-save-button");
        GridPane.setColumnSpan(saveButton, 2);
        formGrid.add(saveButton, 0, 6);

        borderPane.setCenter(formGrid);
        this.root = borderPane;
    }
    /**
     * Ajoute un champ de formulaire avec un label dans un GridPane.
     *
     * @param grid Le GridPane où ajouter les éléments
     * @param labelText Le texte du label
     * @param field Le champ de saisie (TextField, Spinner ou DatePicker)
     * @param row La ligne où insérer les éléments
     */
    // Méthode utilitaire pour ajouter des champs avec style
    private void addFormField(GridPane grid, String labelText, Control field, int row) {
        Label label = new Label(labelText);
        label.getStyleClass().add("client-label");
        field.getStyleClass().add(field instanceof TextField ? "client-text-field" :
                field instanceof Spinner ? "client-spinner" :
                        "client-date-picker");

        grid.add(label, 0, row);
        grid.add(field, 1, row);
    }


    @Override
    protected void configurerActions() {
        disconnect.setOnAction(e-> {
            controlleurSupreme.disonnection();
        });

        saveButton.setOnAction(e -> {
            // Mettre à jour l'objet client avec les nouvelles valeurs
            client.setNom(nomField.getText());
            client.setMail(mailField.getText());
            client.setClasse(classeSpinner.getValue());
            client.setMonnaie(monnaieSpinner.getValue());
            client.setDate_naissance(java.sql.Date.valueOf(dateNaissancePicker.getValue()));

            // Ici vous devrez appeler votre DAO pour sauvegarder en base
            controlleurSupreme.updateClient(client);

            // Feedback à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Les modifications ont été enregistrées !");
            alert.showAndWait();
        });
    }

    @Override
    public void actualiser() {

    }
}
