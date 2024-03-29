package Model;

import java.time.LocalDate;

public class Facture {
    private int numFacture;
    private LocalDate date;
    private float montant;
    private boolean etat; //true si facture paye

    public Facture(int numFacture, LocalDate date, float montant, boolean etat) {
        this.numFacture = numFacture;
        this.date = date;
        this.montant = montant;
        this.etat = etat;
    }

    public int getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(int numFacture) {
        this.numFacture = numFacture;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
