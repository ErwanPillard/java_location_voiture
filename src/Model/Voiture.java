package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Voiture {
    private LocalDateTime dateMiseCirculation;
    private String immatriculation;
    private String couleur;
    private int nbKilometre;
    private  Modele modele;

    public Voiture(LocalDateTime dateMiseCirculation, String immatriculation, String couleur, int nbKilometre, Modele modele) {
        this.dateMiseCirculation = dateMiseCirculation;
        this.immatriculation = immatriculation;
        this.couleur = couleur;
        this.nbKilometre = nbKilometre;
        this.modele= modele;
    }

    public LocalDateTime getDateMiseCirculation() {
        return dateMiseCirculation;
    }

    public void setDateMiseCirculation(LocalDateTime dateMiseCirculation) {
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

    public int getNbKilometre() {
        return nbKilometre;
    }

    public void setNbKilometre(int nbKilometre) {
        this.nbKilometre = nbKilometre;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }
}
