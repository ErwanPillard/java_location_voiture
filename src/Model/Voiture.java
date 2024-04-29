package Model;

import Dao.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Voiture {
    private int id;
    private LocalDate dateMiseCirculation;
    private String immatriculation;
    private String couleur;
    private double nbKilometre;
    private int modele_id;

    public Voiture(LocalDate dateMiseCirculation, String immatriculation, String couleur, double nbKilometre, int modele_id) {
        this.dateMiseCirculation = dateMiseCirculation;
        this.immatriculation = immatriculation;
        this.couleur = couleur;
        this.nbKilometre = nbKilometre;
        this.modele_id= modele_id;
    }

    public LocalDate getDateMiseCirculation() {
        return dateMiseCirculation;
    }

    public void setDateMiseCirculation(LocalDate dateMiseCirculation) {
        this.dateMiseCirculation = dateMiseCirculation;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public double getNbKilometre() {
        return nbKilometre;
    }

    public void setNbKilometre(double nbKilometre) {
        this.nbKilometre = nbKilometre;
    }

    public int getModele_id() {
        return modele_id;
    }


    public void setModele_id(int modele_id) {
        this.modele_id = modele_id;
    }

    public Object[] toArray() {
        Modele modele;
        try {
            modele = Modele.getModeleById(modele_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //return new Object[] {this.immatriculation, this.dateMiseCirculation.toString(), this.nbKilometre, this.couleur, modele.getMarque(), modele.getNom(), modele.getNbPlace(), modele.getNbPorte(), modele.getTailleCoffre(), modele.getCaracteristiques(), modele.getPrixJournalier(), modele.getNoteSatisfaction(), modele.getCategorie(), modele.isAttelage() ? "Oui" : "Non", modele.getBoiteVitesse()};
        return new Object[] {this.immatriculation, this.dateMiseCirculation.toString(), this.nbKilometre, this.couleur};
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "dateMiseCirculation=" + dateMiseCirculation +
                ", immatriculation='" + immatriculation + '\'' +
                ", couleur='" + couleur + '\'' +
                ", nbKilometre=" + nbKilometre +
                ", modele_id=" + modele_id +
                '}';
    }


    public void add(Voiture voiture) throws SQLException {
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        voitureDAO.add(voiture);
    }

    /**
     * Méthode pour récupérer toutes les voitures de la base de données
     * @return Une liste contenant toutes les voitures de la base de données
     */
    public static List<Voiture> all() throws SQLException {
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        return voitureDAO.all();
    }

    public static List<Voiture> allFiltredCategorie(String categorie) throws SQLException {
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        return voitureDAO.allFiltredCategorie(categorie);
    }

    public static boolean immatExists(String immatriculation) throws SQLException{
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        return voitureDAO.immatExists(immatriculation);
    }

    public void delete() throws SQLException{
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        voitureDAO.delete(this);
    }

    public void update(Voiture voiture) throws SQLException{
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        voitureDAO.update(voiture);
    }

    public static Voiture findByImmat(String immatriculation) throws SQLException {
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        return voitureDAO.findByImmat(immatriculation);
    }

    public static byte[] getImageByImmatriculation(String immatriculation){
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        return voitureDAO.getImageByImmatriculation(immatriculation);
    }

    public static void addImage(String immatriculation, File imageFile) throws SQLException {
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        voitureDAO.addImage(immatriculation, imageFile);
    }

}
