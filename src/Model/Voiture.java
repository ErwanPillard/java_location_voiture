package Model;

import Dao.*;

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

    public String[] toArray() {
        String modeleName;
        try {
            modeleName = Modele.getNameById(modele_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new String[] {this.immatriculation, this.dateMiseCirculation.toString(), String.valueOf(this.nbKilometre), this.couleur, modeleName};
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

}
