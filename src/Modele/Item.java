package Modele;

public class Item {

    private int id_item;
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


    public Item(int id_item, String nom, int stock, String description, double prix, int type,
                int classe, int couleur, double taille, int matiere, int solidite, double poids) {
        this.id_item = id_item;
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
    }

    // Getters et Setters
    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
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
        return "Item{" +
                "id_item=" + id_item +
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

