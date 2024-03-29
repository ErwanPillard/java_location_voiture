package Model;

import java.time.LocalDateTime;

public class Reservation {
    private Voiture voiture;
    private Facture facture;
    private float tarif;
    private LocalDateTime dateFin;
    private LocalDateTime dateDebut;
    private int numeroReservation;
    private Client client;
    private boolean etat;

    public Reservation(Voiture voiture, Client client,float tarif, LocalDateTime dateFin, LocalDateTime dateDebut, int numeroReservation, Facture facture ,boolean etat) {
        this.voiture = voiture;
        this.tarif = tarif;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
        this.numeroReservation = numeroReservation;
        this.facture = facture;
        this.etat=etat;
        this.client = client;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getNumeroReservation() {
        return numeroReservation;
    }

    public void setNumeroReservation(int numeroReservation) {
        this.numeroReservation = numeroReservation;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
