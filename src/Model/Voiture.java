package Model;

import Dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Voiture {
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

    public void setNbKilometre(int nbKilometre) {
        this.nbKilometre = nbKilometre;
    }

    public int getModele_id() {
        return modele_id;
    }

    public void setModele_id(int modele_id) {
        this.modele_id = modele_id;
    }


    public void add(Voiture voiture) throws SQLException {
        VoitureDAO voitureDAO = new VoitureDAOImpl();
        voitureDAO.add(voiture);
    }

}
