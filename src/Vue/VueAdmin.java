package Vue;

import Controleur.ControlleurSupreme;
import Modele.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class VueAdmin extends VueBase {
    private Label adminInfoLabel;
    private ListView<User> adminsListView;
    private Button deleteButton;
    private Button refreshButton;
    private Button createAdminButton;

    public VueAdmin(ControlleurSupreme controlleurSupreme_p) {
        super(controlleurSupreme_p);
    }

    @Override
    protected void initialiserComposant() {
        // Main container
        BorderPane mainPane = new BorderPane();

        // Essayez d'abord sans CSS pour diagnostiquer
        mainPane.getStylesheets().add(getClass().getResource("/src/resources/css/adminDashboard.css").toExternalForm());

        // Top - Admin information
        VBox adminInfoBox = new VBox(10);
        adminInfoBox.setPadding(new Insets(20));
        adminInfoBox.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("Tableau de bord Administrateur");
        titleLabel.setFont(Font.font(20));
        // titleLabel.getStyleClass().add("dashboard-title");

        adminInfoLabel = new Label();
        adminInfoLabel.setFont(Font.font(14));
        // adminInfoLabel.getStyleClass().add("admin-info");

        adminInfoBox.getChildren().addAll(titleLabel, adminInfoLabel);
        mainPane.setTop(adminInfoBox);

        // Center - List of admins
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(20));

        Label adminsLabel = new Label("Administrateurs du système");
        // adminsLabel.getStyleClass().add("section-title");

        adminsListView = new ListView<>();
        adminsListView.setPrefHeight(400); // Taille fixe pour test
        // adminsListView.getStyleClass().add("admin-list");

        adminsListView.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User admin, boolean empty) {
                super.updateItem(admin, empty);
                if (empty || admin == null) {
                    setText(null);
                    setStyle(""); // Reset du style
                } else {
                    setText(admin.getPseudo() + " (ID: " + admin.getId_user() + ")");
                    setStyle("-fx-text-fill: black;"); // Couleur forcée pour test
                }
            }
        });

        centerBox.getChildren().addAll(adminsLabel, adminsListView);
        mainPane.setCenter(centerBox);

        // Bottom - Action buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        deleteButton = new Button("Supprimer l'admin sélectionné");
        // deleteButton.getStyleClass().add("danger-button");

        refreshButton = new Button("Actualiser la liste");
        // refreshButton.getStyleClass().add("action-button");

        createAdminButton = new Button("Créer un nouvel admin");
        // createAdminButton.getStyleClass().add("action-button");

        buttonBox.getChildren().addAll(deleteButton, refreshButton, createAdminButton);
        mainPane.setBottom(buttonBox);

        MenuPrincipal menuPrincipal = new MenuPrincipal(controlleurSupreme);
        mainPane.setTop(menuPrincipal.getMenuBar());

        this.root = mainPane;

        // Appeler actualiser() APRÈS que tout le layout est construit
        actualiser();
    }

    @Override
    protected void configurerActions() {
        // Delete selected admin
        deleteButton.setOnAction(e -> {
            User selectedAdmin = adminsListView.getSelectionModel().getSelectedItem();
            if (selectedAdmin != null) {
                // Prevent self-deletion
                if (selectedAdmin.getId_user() == controlleurSupreme.getUser().getId_user()) {
                    showAlert("Erreur", "Vous ne pouvez pas supprimer votre propre compte!", Alert.AlertType.ERROR);
                    return;
                }

                // Confirm deletion
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation de suppression");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer l'admin " + selectedAdmin.getPseudo() + "?");

                if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                    if (controlleurSupreme.supprimerUtilisateur(selectedAdmin.getId_user())) {
                        showAlert("Succès", "Admin supprimé avec succès", Alert.AlertType.INFORMATION);
                        actualiser();
                    } else {
                        showAlert("Erreur", "Échec de la suppression", Alert.AlertType.ERROR);
                    }
                }
            } else {
                showAlert("Erreur", "Veuillez sélectionner un admin à supprimer", Alert.AlertType.ERROR);
            }
        });

        // Refresh list
        refreshButton.setOnAction(e -> actualiser());

        // Create new admin
        createAdminButton.setOnAction(e -> {
            this.controlleurSupreme.vueCreerNouveauAdmin();
        });
    }

    @Override
    public void actualiser() {
        // Update current admin info
        User currentAdmin = controlleurSupreme.getUser();
        adminInfoLabel.setText("Connecté en tant que: " + currentAdmin.getPseudo() + " (ID: " + currentAdmin.getId_user() + ")");

        // Load all admins
        ArrayList<User> admins = controlleurSupreme.getTousLesAdmins();
        adminsListView.setItems(FXCollections.observableArrayList(admins));
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}