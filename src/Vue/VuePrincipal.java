package Vue;
import Controleur.ControlleurSupreme;
import DAO.DaoFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class VuePrincipal extends Application{

    private Stage stage;
    private ControlleurSupreme controlleurSupreme;


    @Override
    public void start(Stage stage_p) {
        this.stage = stage_p;
        DaoFactory daoFactory = DaoFactory.getInstance("chouping","root","");

        ControlleurSupreme controlleurSupreme = new ControlleurSupreme(this,daoFactory);

        initialiser_fenetre();

    }

    private void initialiser_fenetre(){
        // Configuration du stage

        stage.setTitle("Chouping");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);


        javafx.scene.image.Image icone = new javafx.scene.image.Image("resources/global/icon.png");
        stage.getIcons().add(icone);

        afficherVueAcceuil();
    }

    /**
     * Sert à afficher la vue de l'acceuil au début
     */
    private void afficherVueAcceuil(){
        VueAccueil vueAccueil = new VueAccueil(controlleurSupreme);
        Scene scene = new Scene(vueAccueil.getRoot(),stage.getWidth(), stage.getHeight());

        // On peut ajouter un fichier CSS global ici
        // scene.getStylesheets().add(getClass().getResource("/styles/global.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
        stage.setWidth(1000);
        stage.setHeight(600);

    }

    public void accederVue(VueBase vue) {
        Scene scene = new Scene(vue.getRoot(), stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }



    /**
     * Pour afficher n'importe quel vue
     * @param la vue à afficher est à passer en paremetre
     */
    private void afficherVue(VueBase vue){
        Scene scene = new Scene(vue.getRoot(),stage.getWidth(),stage.getHeight());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Pour initialiser le controleur suppreme dans la Vue
     * @param controlleurSupreme_p est l'instance du controleur supreme
     */
    public void getControlleurSupreme(ControlleurSupreme controlleurSupreme_p){
        this.controlleurSupreme = controlleurSupreme_p;
    }

    Bibliotheque bibliotheque = new Bibliotheque();
    Scanner scanner = new Scanner(System.in);

        System.out.println("=== Système de Gestion de Bibliothèque ===");

    // Ajout de quelques livres par défaut
        bibliotheque.ajouterLivre(new Livre("978-2070360426", "L'Étranger", "Albert Camus", 1942, 5));
        bibliotheque.ajouterLivre(new Livre("978-2070360532", "Le Petit Prince", "Antoine de Saint-Exupéry", 1943, 3));
        bibliotheque.ajouterLivre(new Livre("978-2253010693", "1984", "George Orwell", 1949, 2));

    // Menu principal
        while (true) {
        System.out.println("\nMenu Principal:");
        System.out.println("1. Gestion des Livres");
        System.out.println("2. Gestion des Membres");
        System.out.println("3. Gestion des Emprunts");
        System.out.println("4. Statistiques");
        System.out.println("5. Quitter");
        System.out.print("Choix: ");

        int choix = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

        switch (choix) {
            case 1:
                menuGestionLivres(bibliotheque, scanner);
                break;
            case 2:
                menuGestionMembres(bibliotheque, scanner);
                break;
            case 3:
                menuGestionEmprunts(bibliotheque, scanner);
                break;
            case 4:
                afficherStatistiques(bibliotheque);
                break;
            case 5:
                System.out.println("Merci d'avoir utilisé le système. Au revoir!");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Option invalide. Veuillez réessayer.");
        }
    }
}

// Sous-menus et méthodes auxiliaires...
private static void menuGestionLivres(Bibliotheque bibliotheque, Scanner scanner) {
    while (true) {
        System.out.println("\nGestion des Livres:");
        System.out.println("1. Ajouter un livre");
        System.out.println("2. Supprimer un livre");
        System.out.println("3. Rechercher un livre");
        System.out.println("4. Afficher tous les livres");
        System.out.println("5. Retour au menu principal");
        System.out.print("Choix: ");

        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1:
                ajouterLivre(bibliotheque, scanner);
                break;
            case 2:
                supprimerLivre(bibliotheque, scanner);
                break;
            case 3:
                rechercherLivre(bibliotheque, scanner);
                break;
            case 4:
                bibliotheque.afficherTousLesLivres();
                break;
            case 5:
                return;
            default:
                System.out.println("Option invalide. Veuillez réessayer.");
        }
    }
}

private static void ajouterLivre(Bibliotheque bibliotheque, Scanner scanner) {
    System.out.print("ISBN: ");
    String isbn = scanner.nextLine();
    System.out.print("Titre: ");
    String titre = scanner.nextLine();
    System.out.print("Auteur: ");
    String auteur = scanner.nextLine();
    System.out.print("Année de publication: ");
    int annee = scanner.nextInt();
    System.out.print("Quantité disponible: ");
    int quantite = scanner.nextInt();

    Livre livre = new Livre(isbn, titre, auteur, annee, quantite);
    bibliotheque.ajouterLivre(livre);
    System.out.println("Livre ajouté avec succès!");
}

private static void supprimerLivre(Bibliotheque bibliotheque, Scanner scanner) {
    System.out.print("Entrez l'ISBN du livre à supprimer: ");
    String isbn = scanner.nextLine();
    bibliotheque.supprimerLivre(isbn);
}

private static void rechercherLivre(Bibliotheque bibliotheque, Scanner scanner) {
    System.out.println("Rechercher par:");
    System.out.println("1. ISBN");
    System.out.println("2. Titre");
    System.out.println("3. Auteur");
    System.out.print("Choix: ");
    int critere = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Terme de recherche: ");
    String terme = scanner.nextLine();

    List<Livre> resultats = new ArrayList<>();
    switch (critere) {
        case 1:
            resultats = bibliotheque.rechercherParISBN(terme);
            break;
        case 2:
            resultats = bibliotheque.rechercherParTitre(terme);
            break;
        case 3:
            resultats = bibliotheque.rechercherParAuteur(terme);
            break;
        default:
            System.out.println("Option invalide.");
            return;
    }

    if (resultats.isEmpty()) {
        System.out.println("Aucun livre trouvé.");
    } else {
        System.out.println("\nRésultats de la recherche:");
        for (Livre livre : resultats) {
            System.out.println(livre);
        }
    }
}

// Méthodes similaires pour les autres menus (gestion membres, emprunts, etc.)
// ...
}

// Classe représentant la bibliothèque
class Bibliotheque {
    private Map<String, Livre> livres;
    private Map<Integer, Membre> membres;
    private List<Emprunt> emprunts;
    private int dernierIdMembre = 0;

    public Bibliotheque() {
        this.livres = new HashMap<>();
        this.membres = new HashMap<>();
        this.emprunts = new ArrayList<>();
    }

    public void ajouterLivre(Livre livre) {
        if (livres.containsKey(livre.getIsbn())) {
            // Si le livre existe déjà, augmenter la quantité
            Livre existant = livres.get(livre.getIsbn());
            existant.setQuantiteDisponible(existant.getQuantiteDisponible() + livre.getQuantiteDisponible());
        } else {
            livres.put(livre.getIsbn(), livre);
        }
    }

    public void supprimerLivre(String isbn) {
        if (livres.containsKey(isbn)) {
            livres.remove(isbn);
            System.out.println("Livre supprimé avec succès.");
        } else {
            System.out.println("Livre non trouvé.");
        }
    }

    public List<Livre> rechercherParISBN(String isbn) {
        List<Livre> resultats = new ArrayList<>();
        for (Livre livre : livres.values()) {
            if (livre.getIsbn().contains(isbn)) {
                resultats.add(livre);
            }
        }
        return resultats;
    }

    public List<Livre> rechercherParTitre(String titre) {
        List<Livre> resultats = new ArrayList<>();
        for (Livre livre : livres.values()) {
            if (livre.getTitre().toLowerCase().contains(titre.toLowerCase())) {
                resultats.add(livre);
            }
        }
        return resultats;
    }

    public List<Livre> rechercherParAuteur(String auteur) {
        List<Livre> resultats = new ArrayList<>();
        for (Livre livre : livres.values()) {
            if (livre.getAuteur().toLowerCase().contains(auteur.toLowerCase())) {
                resultats.add(livre);
            }
        }
        return resultats;
    }

    public void afficherTousLesLivres() {
        if (livres.isEmpty()) {
            System.out.println("Aucun livre dans la bibliothèque.");
            return;
        }

        System.out.println("\nListe des livres:");
        for (Livre livre : livres.values()) {
            System.out.println(livre);
        }
    }

    // Autres méthodes pour la gestion des membres et des emprunts
    // ...
}

// Classe représentant un livre
class Livre {
    private String isbn;
    private String titre;
    private String auteur;
    private int anneePublication;
    private int quantiteDisponible;

    public Livre(String isbn, String titre, String auteur, int anneePublication, int quantiteDisponible) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.quantiteDisponible = quantiteDisponible;
    }

    // Getters et setters
    public String getIsbn() { return isbn; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public int getAnneePublication() { return anneePublication; }
    public int getQuantiteDisponible() { return quantiteDisponible; }
    public void setQuantiteDisponible(int quantite) { this.quantiteDisponible = quantite; }

    @Override
    public String toString() {
        return String.format("%s par %s (%d) - ISBN: %s - Disponibles: %d",
                titre, auteur, anneePublication, isbn, quantiteDisponible);
    }
}

// Classe représentant un membre de la bibliothèque
class Membre {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    public Membre(int id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // Getters
    public int getId() { return id; }
    public String getNomComplet() { return prenom + " " + nom; }
    // Autres getters et setters...
}

// Classe représentant un emprunt
class Emprunt {
    private Livre livre;
    private Membre membre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;
    private boolean estRetourne;

    public Emprunt(Livre livre, Membre membre, LocalDate dateEmprunt) {
        this.livre = livre;
        this.membre = membre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateEmprunt.plusDays(14); // 2 semaines pour retour
        this.estRetourne = false;
    }

    public void retourner() {
        this.estRetourne = true;
        this.dateRetourEffective = LocalDate.now();
    }

    public boolean estEnRetard() {
        return !estRetourne && LocalDate.now().isAfter(dateRetourPrevue);
    }

    public long calculerAmende() {
        if (!estEnRetard() || estRetourne) return 0;

        long joursRetard = ChronoUnit.DAYS.between(dateRetourPrevue, LocalDate.now());
        return joursRetard * 1; // 1€ par jour de retard
    }

    public static void main(String[] args) {
        launch(args);
    }

}
