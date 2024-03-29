import java.time.LocalDate;

public class Reservation {
    private Voiture voiture;
    private Facture facture;
    private float tarif;
    private LocalDate dateFin;
    private LocalDate dateDebut;
    private int numeroReservation;
    // private Client client;
    private boolean etat;

    public Reservation(Voiture voiture, float tarif, LocalDate dateFin, LocalDate dateDebut, int numeroReservation, Facture facture ,boolean etat) {
        this.voiture = voiture;
        this.tarif = tarif;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
        this.numeroReservation = numeroReservation;
        this.facture = facture;
        this.etat=etat;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getNumeroReservation() {
        return numeroReservation;
    }

    public void setNumeroReservation(int numeroReservation) {
        this.numeroReservation = numeroReservation;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
