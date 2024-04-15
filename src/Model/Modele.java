package Model;

import Dao.DatabaseManager;
import Dao.ModeleDAO;
import Dao.ModeleDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

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

    public Modele(String nom, int nbPlace, int nbPorte, float tailleCoffre, String caracteristiques, int prixJournalier, float noteSatisfaction, Categorie  categorie, boolean attelage, BoiteVitesse boiteVitesse) {
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

    /*public BoiteVitesse getBoiteVitesse() {
        return boiteVitesse;
    }

    public void setBoiteVitesse(BoiteVitesse boiteVitesse) {
        this.boiteVitesse = boiteVitesse;
    }*/

    public Categorie getCategorie() {
        return categorie;
    }

    public BoiteVitesse getBoiteVitesse(){
        return boiteVitesse;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Méthode pour récupérer tous les modèle de la base de données
     * @return Un tablea contenant tous les modèles de la base de données
     */
    public static String[] all() throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ModeleDAO modeleDAO = new ModeleDAOImpl(connection);
        return modeleDAO.all();
    }

    /**
     * Méthode pour récupérer l'id du modèle avec le nom
     * @return Un tablea contenant tous les modèles de la base de données
     */
    public static int getIdByName(String modelName) throws SQLException{
        Connection connection = DatabaseManager.getConnection();
        ModeleDAO modeleDAO = new ModeleDAOImpl(connection);
        return modeleDAO.getIdByName(modelName);
    }

}

