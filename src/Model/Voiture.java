package Model;

import java.time.LocalDate;

public class Voiture {
    private LocalDate dateMiseCirculation;
    private int imatriculation;
    private String couleur;
    private int nbKilometre;
    private  Modele modele;

    public Voiture(LocalDate dateMiseCirculation, int imatriculation, String couleur, int nbKilometre, Modele modele) {
        this.dateMiseCirculation = dateMiseCirculation;
        this.imatriculation = imatriculation;
        this.couleur = couleur;
        this.nbKilometre = nbKilometre;
        this.modele= modele;
    }

}
