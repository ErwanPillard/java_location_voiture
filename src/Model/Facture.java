package Model;

import java.time.LocalDate;

public class Facture {
    private int numFacture;
    private LocalDate date;
    private float montant;
    private boolean etat;

    public Facture(int numFacture, LocalDate date, float montant, boolean etat) {
        this.numFacture = numFacture;
        this.date = date;
        this.montant = montant;
        this.etat = etat;
    }




}
