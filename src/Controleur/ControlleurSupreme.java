package Controleur;

import DAO.DaoFactory;
import Modele.*;
import Vue.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Contrôleur principal de l'application, gère la communication entre la Vue, les DAO et les Modèles.
 */
public class ControlleurSupreme {
    private VuePrincipal vuePrincipal;
    private DaoFactory daoFactory;
    private User user;
    private Client client;
    private Panier panier;
    private String categorieActuelle;
    /**
     * Constructeur du contrôleur principal.
     * @param vuePrincipal_p Vue principale de l'application
     * @param daoFactory_p Fabrique de DAO
     */
    public ControlleurSupreme(VuePrincipal vuePrincipal_p, DaoFactory daoFactory_p){
        this.vuePrincipal = vuePrincipal_p;
        this.daoFactory = daoFactory_p;
        this.vuePrincipal.getControlleurSupreme(this);
        this.user = null;
        this.client = null;
        this.panier = null;
    }

    /**
     * Tente de connecter un utilisateur.
     * @param pseudo Pseudo de l'utilisateur
     * @param password Mot de passe de l'utilisateur
     * @param vue Vue actuelle
     */
    public void requestConnection(String pseudo, String password, VueBase vue){
        User user_essaie = daoFactory.getClientDAO().connection(pseudo,password);
        if(user_essaie!=null){
            ((VueConnection) vue).changeErrorLabel("Vous êtes désormais connecté");
            this.user = user_essaie;

            switch (this.user.getUserType()){
                case 0:
                    this.client = (Client) daoFactory.getClientDAO().chercher(this.user.getId_user());
                    this.panier = new Panier();
                    vuePrincipal.accederVue(new VueClient(this));

            }

        }
        else{
            ((VueConnection) vue).changeErrorLabel("Erreur lors de la connection à votre compte");
        }
    }
    /**
     * Accède au compte de l'utilisateur.
     */
    public void accederCompte(){
        if (this.user == null) {
            vuePrincipal.accederVue(new VueConnection(this));
        }
        else{
            if (this.user.getUserType()==0){
                vuePrincipal.accederVue(new VueClient(this));
            }
            else {
                vuePrincipal.accederVue(new VueAdmin(this));
            }

        }
    }

    /**
     * Accède à la page de création de compte.
     */
    public void accederCreationCompte(){
        vuePrincipal.accederVue(new VueCreationCompte(this));
    }

    /**
     * Accède à la page d'accueil.
     */
    public void accederAcceuil(){
        vuePrincipal.accederVue(new VueAccueil(this));
    }
    /**
     * Accède à une catégorie spécifique.
     * @param nomCategorie_p Nom de la catégorie
     */
    public void accederCategorie(String nomCategorie_p) {
        this.categorieActuelle = nomCategorie_p;
        VueBoutiqueCategorie vue = new VueBoutiqueCategorie(this);
        vuePrincipal.accederVue(vue);
    }
    /**
     * Met à jour les informations du client.
     * @param client_p Nouveau client
     */
    public void updateClient(Client client_p){
        this.client = client_p;
        daoFactory.getClientDAO().modifier(client_p);
    }
    /**
     * Récupère le nom de la catégorie actuelle.
     * @return Nom de la catégorie actuelle
     */
    public String getCategorieActuelle(){
        return this.categorieActuelle;
    }
    /**
     * Récupère tous les articles disponibles.
     * @return Liste d'articles
     */
    public ArrayList<Article> getAllArticles(){
        ArrayList<Object> objectList = daoFactory.getarticleDAO().getAll();

        // Créer une nouvelle liste d'Articles
        ArrayList<Article> articleList = new ArrayList<>();

        // Convertir chaque élément
        for (Object obj : objectList) {
            if (obj instanceof Article) {
                articleList.add((Article) obj);
            }
        }

        return articleList;
    }

    /**
     * Accède au panier du client.
     */

    public void accederPanier(){
        if (this.client==null) {
            vuePrincipal.accederVue(new VueConnection(this));
        }
        else {
            vuePrincipal.accederVue(new VuePanier(this));
        }
    }
    /**
     * Récupère l'utilisateur actuel.
     * @return Utilisateur connecté
     */
    public User getUser(){
        return this.user;
    }
    /**
     * Récupère le client actuel.
     * @return Client connecté
     */
    public Client getClient(){
        return this.client;
    }
    /**
     * Crée un nouveau client.
     * @param nouveauClient Le client à créer
     * @return Succès de la création
     */
    public boolean creerClient(Client nouveauClient){
        int id_compte = daoFactory.getClientDAO().ajouter(nouveauClient);
        this.client = nouveauClient;
        this.client.setId_compte(id_compte);
        vuePrincipal.accederVue(new VueConnection(this));
        return true;
    }

    /**
     * Récupère le panier actuel.
     * @return Panier
     */
    public Panier getPanier(){return this.panier;}


    /**
     * Accède à la vue d'un article.
     * @param article Article concerné
     */
    public void accederVueArticle(Article article){
        vuePrincipal.accederVue(new VueArticle(this, article));
    }
    /**
     * Récupère les commandes passées par l'utilisateur connecté.
     * @return Liste des commandes
     */
    public ArrayList<Commande> getCommandesUtilisateur(){
        if (this.client != null){
            ArrayList<Object> objectList = daoFactory.getCommandeDAO().getAll();

            ArrayList<Commande> list_commande = new ArrayList<>();

            // Convertir chaque élément
            for (Object obj : objectList) {
                if (obj instanceof Commande) {
                    if (((Commande) obj).getId_compte() == client.getId_compte()) {
                        list_commande.add((Commande) obj);
                    }
                }
            }
            return list_commande;

        }
        return null;
    }
    /**
     * Affiche les détails d'une commande.
     * @param commande Commande sélectionnée
     */
    public void afficherDetailCommande(Commande commande){
        vuePrincipal.accederVue(new VueDetailCommande(this, commande));
    }
    /**
     * Affiche la liste des commandes.
     */
    public void afficherCommande(){
        vuePrincipal.accederVue(new VueCommandes(this));
    }
    /**
     * Accède à la vue d'ajout d'un article.
     */
    public void afficherAjouterArticle(){
        vuePrincipal.accederVue(new VueAjouterArticle(this));
    }
    /**
     * Accède à la vue de modification des articles.
     */
    public void afficherModifierArticle(){
        vuePrincipal.accederVue(new VueModifierArticle(this));
    }
    /**
     * Affiche les détails d'un article pour modification.
     * @param article Article à modifier
     */
    public void vueModifierArticleDetail(Article article){
        vuePrincipal.accederVue(new VueModifierArticleDetails(this,article));
    }
    /**
     * Accède à la vue de création d'un administrateur.
     */
    public void vueCreerNouveauAdmin(){
        vuePrincipal.accederVue(new VueCreationCompteAdmin(this));
    }
    /**
     * Récupère les articles associés à une commande.
     * @param commande Commande concernée
     * @return Liste des articles
     */
    public ArrayList<Article> getaArticlesCommande(Commande commande) {
        return daoFactory.getCommandeDAO().getItemsDetailsCommande(commande.getId_commande());
    }

    /**
     * Supprime un article.
     * @param article_supprimer Article à supprimer
     */
    public void supprimerArticle(Article article_supprimer){
        daoFactory.getarticleDAO().supprimer(article_supprimer);
    }
    /**
     * Crée une commande à partir du panier.
     */
    public void commander(){
        Commande new_commande = new Commande(client.getId_compte());
        int[] id_article = new int[panier.getListeArticle().size()];
        int[] nb_article = new int[panier.getListeQuantite().size()];

        for (int i = 0; i < panier.getListeArticle().size(); i++) {
                 id_article[i] = (panier.getListeArticle().get(i).getId_article());
                 nb_article[i] = (panier.getListeQuantite().get(i));
        }




        new_commande.setItems(id_article);
        new_commande.setNb_items(nb_article);



        daoFactory.getCommandeDAO().ajouter(new_commande);
    }
    /**
     * Récupère tous les administrateurs.
     * @return Liste d'administrateurs
     */
    public ArrayList<User> getTousLesAdmins(){
        ArrayList<User> listUser =  daoFactory.getClientDAO().getAllUser();
        ArrayList<User> AdminList = new ArrayList<User>();



        for (int i = 0; i < listUser.size(); i++) {
            User user_p = listUser.get(i);
            System.out.println(user_p.getUserType());
            if (user_p.getUserType()==1){

                User admin = new User(user_p.getId_user(),user_p.getPseudo(), user_p.getUserType());
                admin.setPassword(user_p.getPassword());
                AdminList.add(admin);
            }
        }
        return AdminList;
    }
    /**
     * Ajoute un nouvel article.
     */

    public void ajouterArticle(String nom, int stock, String description, double prix, int type, int classe, int couleur, double taille, int matiere, int solidite, double poids){
        Article articleToAdd = new Article(-1, nom,stock,description,prix,type, classe,couleur, taille, matiere, solidite,poids);

        int id_article = daoFactory.getarticleDAO().ajouter(articleToAdd);
        articleToAdd.setId_article(id_article);
    }
    /**
     * Modifie un article existant.
     * @param article_a_modifier Article à modifier
     */
    public void modifierArticle(Article article_a_modifier){
        daoFactory.getarticleDAO().modifier(article_a_modifier);
    }
    /**
     * Crée un nouvel utilisateur administrateur.
     * @param new_User Nouvel utilisateur
     * @return Succès de la création
     */
    public boolean creerUtilisateur(User new_User){
        daoFactory.getClientDAO().ajouterAdmin(new_User);
        return true;
    }
    /**
     * Supprime un administrateur.
     * @param user_id ID de l'utilisateur à supprimer
     * @return Succès de la suppression
     */
    public boolean supprimerUtilisateur(int user_id){
        daoFactory.getClientDAO().supprimerAdmin(user_id);
        return true;
    }

    public void afficherAjouterPack(){
        vuePrincipal.accederVue(new VueAjouterPack(this));
    }

    public void ajouterPack(int nb_article, double prix ){

        daoFactory.getarticleDAO().ajouterPack(nb_article,prix);
    }

    public int getNumberPack(){
        ArrayList<Integer> liste_pack = daoFactory.getarticleDAO().getAllPack();
        return liste_pack.size();
    }

    public double getRealPrice(Article article, int nb_article){
        return daoFactory.getarticleDAO().getRealPrice(article.getId_article(),nb_article);
    }

    public void disonnection(){
        this.user = null;
        this.client = null;
        vuePrincipal.accederVue(new VueAccueil(this));
    }
}
