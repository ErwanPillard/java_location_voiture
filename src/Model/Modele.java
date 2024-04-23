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

    public Categorie getCategorie() {
        return categorie;
    }

    public BoiteVitesse getBoiteVitesse(){
        return boiteVitesse;
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

    public String getMarque() {
        return marque;
    }
}

