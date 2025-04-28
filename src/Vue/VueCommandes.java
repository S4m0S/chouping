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
/**
 * VueCommandes est la vue permettant d'afficher les commandes d'un utilisateur.
 * Elle hérite de la classe VueBase et est responsable de l'affichage d'un tableau
 * contenant les commandes avec des détails associés.
 * Cette classe permet à l'utilisateur de consulter ses commandes et d'afficher les
 * détails d'une commande sélectionnée.
 *
 * @see VueBase
 * @see Controleur.ControlleurSupreme
 * @see Modele.Commande
 */
public class VueCommandes extends VueBase {

    private TableView<Commande> tableCommandes;
    private ObservableList<Commande> commandesData;

    public VueCommandes(ControlleurSupreme controlleurSupreme) {
        super(controlleurSupreme);
        initialiserComposant();
        actualiser();
    }
    /**
     * Initialise les composants de l'interface graphique pour la vue des commandes.
     * Configure la structure du BorderPane, les éléments de l'interface et leur style,
     * ainsi que l'ajout des actions aux boutons et autres composants.
     */
    @Override
    protected void initialiserComposant() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        String cssPath = "/src/resources/css/VueCommandes.css";
        try {
            borderPane.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        } catch (Exception e) {
            System.err.println("Erreur chargement CSS: " + e.getMessage());
        }

        borderPane.setTop(new MenuPrincipal(controlleurSupreme).getMenuBar());

        // Titre
        Label titre = new Label("Vos Commandes");
        titre.getStyleClass().add("titre-commandes");

        // Tableau des commandes
        tableCommandes = new TableView<>();
        configurerColonnes();

        // Bouton de détail
        Button btnDetail = new Button("Voir le détail");
        btnDetail.getStyleClass().add("bouton-detail");
        btnDetail.setOnAction(e -> afficherDetailCommande());

        VBox contenu = new VBox(20, titre, tableCommandes, btnDetail);
        borderPane.setCenter(contenu);
        this.root = borderPane;
    }
    /**
     * Configure les colonnes du tableau des commandes.
     * Les colonnes incluent l'ID de la commande, la date d'achat et le nombre d'articles.
     */
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
    /**
     * Affiche les détails de la commande sélectionnée dans le tableau.
     * Si aucune commande n'est sélectionnée, une alerte est affichée à l'utilisateur.
     */
    private void afficherDetailCommande() {
        Commande commandeSelectionnee = tableCommandes.getSelectionModel().getSelectedItem();
        if (commandeSelectionnee != null) {
            controlleurSupreme.afficherDetailCommande(commandeSelectionnee);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une commande");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("dialog-pane");
            alert.showAndWait();
        }
    }
    /**
     * Configure les actions associées aux composants interactifs de la vue,
     * telles que les clics sur les lignes du tableau.
     * Ici, un double-clic sur une ligne de la table ouvre les détails de la commande.
     */
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
    /**
     * Actualise les données des commandes dans la vue en récupérant les commandes depuis le contrôleur.
     * Met à jour le tableau des commandes avec les nouvelles données.
     */
    @Override
    public void actualiser() {
        // Récupérer les commandes depuis le contrôleur
        ArrayList<Commande> commandes = controlleurSupreme.getCommandesUtilisateur();
        commandesData = FXCollections.observableArrayList(commandes);
        tableCommandes.setItems(commandesData);
    }
}