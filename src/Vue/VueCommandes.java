package Vue;

import Controleur.ControlleurSupreme;
import Modele.Commande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VueCommandes extends VueBase {

    private TableView<Commande> tableCommandes;
    private ObservableList<Commande> commandesData;

    public VueCommandes(ControlleurSupreme controlleurSupreme) {
        super(controlleurSupreme);
        initialiserComposant();
        actualiser();
    }

    @Override
    protected void initialiserComposant() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        borderPane.setTop(new MenuPrincipal(controlleurSupreme).getMenuBar());

        // Titre
        Label titre = new Label("Vos Commandes");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Tableau des commandes
        tableCommandes = new TableView<>();
        configurerColonnes();

        // Bouton de détail
        Button btnDetail = new Button("Voir le détail");
        btnDetail.setOnAction(e -> afficherDetailCommande());

        VBox contenu = new VBox(20, titre, tableCommandes, btnDetail);
        borderPane.setCenter(contenu);
        this.root = borderPane;
    }

    private void configurerColonnes() {
        TableColumn<Commande, Integer> colId = new TableColumn<>("N° Commande");
        colId.setCellValueFactory(new PropertyValueFactory<>("id_commande"));

        TableColumn<Commande, Date> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_achat"));

        TableColumn<Commande, Integer> colNbItems = new TableColumn<>("Articles");
        colNbItems.setCellValueFactory(cellData -> {
            int[] items = cellData.getValue().getItems();
            return javafx.beans.binding.Bindings.createIntegerBinding(
                    () -> items != null ? items.length : 0
            ).asObject();
        });

        tableCommandes.getColumns().addAll(colId, colDate, colNbItems);
        tableCommandes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void afficherDetailCommande() {
        Commande commandeSelectionnee = tableCommandes.getSelectionModel().getSelectedItem();
        if (commandeSelectionnee != null) {
            controlleurSupreme.afficherDetailCommande(commandeSelectionnee);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une commande");
            alert.showAndWait();
        }
    }

    @Override
    protected void configurerActions() {
        // Double-clic sur une ligne
        tableCommandes.setRowFactory(tv -> {
            TableRow<Commande> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    afficherDetailCommande();
                }
            });
            return row;
        });
    }

    @Override
    public void actualiser() {
        // Récupérer les commandes depuis le contrôleur
        ArrayList<Commande> commandes = controlleurSupreme.getCommandesUtilisateur();
        commandesData = FXCollections.observableArrayList(commandes);
        tableCommandes.setItems(commandesData);
    }
}