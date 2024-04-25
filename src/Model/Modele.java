package Model;

import Dao.ModeleDAO;
import Dao.ModeleDAOImpl;

import java.sql.SQLException;


public class Modele {
    private int id;
    private String marque;
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

    public Modele(String marque,String nom, int nbPlace, int nbPorte, float tailleCoffre, String caracteristiques, int prixJournalier, float noteSatisfaction, Categorie  categorie, boolean attelage, BoiteVitesse boiteVitesse) {
        this.marque = marque;
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

    public Modele(int id, String marque,String nom, int nbPlace, int nbPorte, float tailleCoffre, String caracteristiques, int prixJournalier, float noteSatisfaction, Categorie  categorie, boolean attelage, BoiteVitesse boiteVitesse) {
        this.id = id;
        this.marque = marque;
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

    public String getMarque() {
        return marque;
    }

    public String getNom() {
        return nom;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public int getNbPorte() {
        return nbPorte;
    }

    public float getTailleCoffre() {
        return tailleCoffre;
    }

    public String getCaracteristiques() {
        return caracteristiques;
    }

    public int getPrixJournalier() {
        return prixJournalier;
    }

    public boolean isAttelage() {
        return attelage;
    }

    public float getNoteSatisfaction() {
        return noteSatisfaction;
    }

    public BoiteVitesse getBoiteVitesse() {
        return boiteVitesse;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public void setNbPorte(int nbPorte) {
        this.nbPorte = nbPorte;
    }

    public void setTailleCoffre(float tailleCoffre) {
        this.tailleCoffre = tailleCoffre;
    }

    public void setCaracteristiques(String caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    public void setPrixJournalier(int prixJournalier) {
        this.prixJournalier = prixJournalier;
    }

    public void setAttelage(boolean attelage) {
        this.attelage = attelage;
    }

    public void setNoteSatisfaction(float noteSatisfaction) {
        this.noteSatisfaction = noteSatisfaction;
    }

    public void setBoiteVitesse(BoiteVitesse boiteVitesse) {
        this.boiteVitesse = boiteVitesse;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Méthode pour récupérer tous les modèle de la base de données
     * @return Un tablea contenant tous les modèles de la base de données
     */
    public static String[] all() throws SQLException {
        ModeleDAO modeleDAO = new ModeleDAOImpl();
        return modeleDAO.all();
    }

    /**
     * Méthode pour récupérer l'id du modèle avec le nom
     * @return Un tablea contenant tous les modèles de la base de données
     */
    public static int getIdByName(String modelName) throws SQLException{
        ModeleDAO modeleDAO = new ModeleDAOImpl();
        return modeleDAO.getIdByName(modelName);
    }

    public static String getNameById(int id) throws SQLException{
        ModeleDAO modeleDAO = new ModeleDAOImpl();
        return modeleDAO.getNameById(id);
    }

    public static Modele getModeleById(int id) throws SQLException{
        ModeleDAO modeleDAO = new ModeleDAOImpl();
        return modeleDAO.getModeleById(id);
    }

    /**
     * Méthode pour ajouter modèle
     */
    public void add(Modele modele) throws SQLException {
        ModeleDAO modeleDAO = new ModeleDAOImpl();
        modeleDAO.add(modele);
    }

    public static void update(Modele modele) throws SQLException {
        ModeleDAO modeleDAO = new ModeleDAOImpl();
        modeleDAO.update(modele);
    }

}

