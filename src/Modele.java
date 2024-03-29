
public class Modele {
    private int id;
    private String nom;
    private int nbPlace;
    private int nbPorte;
    private float tailleCoffre;
    private String caracteristiques;
    private int prixJournalier;
    private boolean attelage;
    private float noteSatisfaction;
    private BoiteVitesse boiteVitesse;
    private Categorie categorie;

    public Modele(int id, String nom, int nbPlace, int nbPorte, float tailleCoffre, String caracteristiques, int prixJournalier, boolean attelage, float noteSatisfaction, BoiteVitesse boiteVitesse, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.nbPlace = nbPlace;
        this.nbPorte = nbPorte;
        this.tailleCoffre = tailleCoffre;
        this.caracteristiques = caracteristiques;
        this.prixJournalier = prixJournalier;
        this.attelage = attelage;
        this.noteSatisfaction = noteSatisfaction;
        this.boiteVitesse = boiteVitesse;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public int getNbPorte() {
        return nbPorte;
    }

    public void setNbPorte(int nbPorte) {
        this.nbPorte = nbPorte;
    }

    public float getTailleCoffre() {
        return tailleCoffre;
    }

    public void setTailleCoffre(float tailleCoffre) {
        this.tailleCoffre = tailleCoffre;
    }

    public String getCaracteristiques() {
        return caracteristiques;
    }

    public void setCaracteristiques(String caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    public int getPrixJournalier() {
        return prixJournalier;
    }

    public void setPrixJournalier(int prixJournalier) {
        this.prixJournalier = prixJournalier;
    }

    public boolean isAttelage() {
        return attelage;
    }

    public void setAttelage(boolean attelage) {
        this.attelage = attelage;
    }

    public float getNoteSatisfaction() {
        return noteSatisfaction;
    }

    public void setNoteSatisfaction(float noteSatisfaction) {
        this.noteSatisfaction = noteSatisfaction;
    }

    public BoiteVitesse getBoiteVitesse() {
        return boiteVitesse;
    }

    public void setBoiteVitesse(BoiteVitesse boiteVitesse) {
        this.boiteVitesse = boiteVitesse;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}

