package Vue;

import Controleur.ControlleurSupreme;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VueAjouterArticle extends VueBase {
    // Basic fields
    private TextField champNom;
    private TextField champPrix;
    private TextField champStock;
    private TextArea champDescription;

    // Additional fields
    private ComboBox<String> comboType;
    private ComboBox<String> comboClasse;
    private ComboBox<String> comboCouleur;
    private TextField champTaille;
    private ComboBox<String> comboMatiere;
    private ComboBox<String> comboSolidite;
    private TextField champPoids;

    // Buttons
    private Button boutonAjouter;
    private Button boutonAnnuler;

    public VueAjouterArticle(ControlleurSupreme controlleurSupreme_p) {
        super(controlleurSupreme_p);
    }

    @Override
    protected void initialiserComposant() {
        // Create basic fields
        champNom = new TextField();
        champPrix = new TextField();
        champStock = new TextField();
        champDescription = new TextArea();
        champDescription.setPrefRowCount(3);

        // Create additional fields with ComboBoxes
        comboType = new ComboBox<>(FXCollections.observableArrayList(
                "Type 1", "Type 2", "Type 3" // Replace with your actual types
        ));

        comboClasse = new ComboBox<>(FXCollections.observableArrayList(
                "Classe A", "Classe B", "Classe C" // Replace with your actual classes
        ));

        comboCouleur = new ComboBox<>(FXCollections.observableArrayList(
                "Rouge", "Vert", "Bleu", "Noir", "Blanc" // Replace with your actual colors
        ));

        champTaille = new TextField();

        comboMatiere = new ComboBox<>(FXCollections.observableArrayList(
                "Coton", "Polyester", "Laine", "Soie" // Replace with your actual materials
        ));

        comboSolidite = new ComboBox<>(FXCollections.observableArrayList(
                "Fragile", "Standard", "Robuste", "Très robuste" // Replace with your actual solidity levels
        ));

        champPoids = new TextField();

        // Create buttons
        boutonAjouter = new Button("Ajouter");
        boutonAnnuler = new Button("Annuler");

        // Form layout using GridPane
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));

        // Add form elements - Basic information
        formGrid.add(new Label("Nom*:"), 0, 0);
        formGrid.add(champNom, 1, 0);
        formGrid.add(new Label("Prix*:"), 0, 1);
        formGrid.add(champPrix, 1, 1);
        formGrid.add(new Label("Stock*:"), 0, 2);
        formGrid.add(champStock, 1, 2);
        formGrid.add(new Label("Description:"), 0, 3);
        formGrid.add(champDescription, 1, 3);

        // Add form elements - Additional information
        formGrid.add(new Label("Type:"), 0, 4);
        formGrid.add(comboType, 1, 4);
        formGrid.add(new Label("Classe:"), 0, 5);
        formGrid.add(comboClasse, 1, 5);
        formGrid.add(new Label("Couleur:"), 0, 6);
        formGrid.add(comboCouleur, 1, 6);
        formGrid.add(new Label("Taille:"), 0, 7);
        formGrid.add(champTaille, 1, 7);
        formGrid.add(new Label("Matière:"), 0, 8);
        formGrid.add(comboMatiere, 1, 8);
        formGrid.add(new Label("Solidité:"), 0, 9);
        formGrid.add(comboSolidite, 1, 9);
        formGrid.add(new Label("Poids:"), 0, 10);
        formGrid.add(champPoids, 1, 10);

        // Button bar
        HBox buttonBar = new HBox(10);
        buttonBar.getChildren().addAll(boutonAnnuler, boutonAjouter);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        // Main layout with scroll pane for long forms
        BorderPane mainPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane(formGrid);
        scrollPane.setFitToWidth(true);
        mainPane.setCenter(scrollPane);
        mainPane.setBottom(buttonBar);
        mainPane.setPadding(new Insets(10));

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        mainPane.setTop(menuPrincipal.getMenuBar());

        // Create scene
        this.root = mainPane;
    }

    @Override
    protected void configurerActions() {
        boutonAjouter.setOnAction(e -> {
            try {
                // Validate required fields
                if (champNom.getText().isEmpty() || champPrix.getText().isEmpty() || champStock.getText().isEmpty()) {
                    showAlert("Champs requis", "Les champs marqués d'un * sont obligatoires.");
                    return;
                }

                // Get basic information
                String nom = champNom.getText();
                double prix = Double.parseDouble(champPrix.getText());
                int stock = Integer.parseInt(champStock.getText());
                String description = champDescription.getText();

                // Get additional information with default values if empty
                int type = comboType.getSelectionModel().getSelectedIndex() + 1; // Assuming IDs start at 1
                int classe = comboClasse.getSelectionModel().getSelectedIndex() + 1;
                int couleur = comboCouleur.getSelectionModel().getSelectedIndex() + 1;
                double taille = champTaille.getText().isEmpty() ? 0 : Double.parseDouble(champTaille.getText());
                int matiere = comboMatiere.getSelectionModel().getSelectedIndex() + 1;
                int solidite = comboSolidite.getSelectionModel().getSelectedIndex() + 1;
                double poids = champPoids.getText().isEmpty() ? 0 : Double.parseDouble(champPoids.getText());

                // Call controller to add article
                // Note: You'll need to adjust this based on your controller's actual method signature
                this.controlleurSupreme.ajouterArticle(

                        nom,
                        stock,
                        description,
                        prix,
                        type,
                        classe,
                        couleur,
                        taille,
                        matiere,
                        solidite,
                        poids
                );

                // Clear form after submission
                clearForm();
            } catch (NumberFormatException ex) {
                showAlert("Erreur de format", "Veuillez entrer des valeurs numériques valides pour les champs numériques.");
            }
        });

        boutonAnnuler.setOnAction(e -> {
           clearForm();
        });
    }

    private void clearForm() {
        champNom.clear();
        champPrix.clear();
        champStock.clear();
        champDescription.clear();
        comboType.getSelectionModel().clearSelection();
        comboClasse.getSelectionModel().clearSelection();
        comboCouleur.getSelectionModel().clearSelection();
        champTaille.clear();
        comboMatiere.getSelectionModel().clearSelection();
        comboSolidite.getSelectionModel().clearSelection();
        champPoids.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void actualiser() {
        // Implementation if needed for data refresh
    }

    public void show() {
        Stage stage = (Stage) champNom.getScene().getWindow();
        stage.show();
    }
}