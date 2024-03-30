package Model;


import java.time.LocalDateTime;

public class Facture {
    private int numFacture;
    private LocalDateTime date;
    private float montant;
    private boolean etat; //true si facture paye

    public Facture(int numFacture, LocalDateTime date, float montant, boolean etat) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
