package Modele;

public class Article {

    private int id_article;
    private String nom;
    private int stock;
    private String description;


    private double prix;
    private int type;
    private int classe;
    private int couleur;
    private double taille;
    private int matiere;
    private int solidite;
    private double poids;
    private int promo;
    private int pack;

    public Article(int id_article, String nom, int stock, String description, double prix, int type,
                   int classe, int couleur, double taille, int matiere, int solidite, double poids) {
        this.id_article = id_article;
        this.nom = nom;
        this.stock = stock;
        this.description = description;
        this.prix = prix;
        this.type = type;
        this.classe = classe;
        this.couleur = couleur;
        this.taille = taille;
        this.matiere = matiere;
        this.solidite = solidite;
        this.poids = poids;
        this.promo = 100;
        this.pack = 0;
    }

    // Getters et Setters


    public void setPromo(int promo) {
        this.promo = promo;
    }

    public int getPromo() {
        return promo;
    }

    public void setPack(int pack) {
        this.pack = pack;
    }

    public int getPack() {
        return pack;
    }

    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public int getMatiere() {
        return matiere;
    }

    public void setMatiere(int matiere) {
        this.matiere = matiere;
    }

    public int getSolidite() {
        return solidite;
    }

    public void setSolidite(int solidite) {
        this.solidite = solidite;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id_article=" + id_article +
                ", nom='" + nom + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", type=" + type +
                ", classe=" + classe +
                ", couleur=" + couleur +
                ", taille=" + taille +
                ", matiere=" + matiere +
                ", solidite=" + solidite +
                ", poids=" + poids +
                '}';
    }
}

