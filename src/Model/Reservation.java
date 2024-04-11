package Model;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private final Voiture voiture;
    private Facture facture;
    private float tarif;
    private LocalDateTime dateFin;
    private LocalDateTime dateDebut;
    private int numeroReservation;
    private final Client client;
    private boolean etat;

    public Reservation(Voiture voiture, Client client, LocalDateTime dateFin, LocalDateTime dateDebut, int numeroReservation,boolean etat) {
        this.voiture = voiture;
        this.tarif = tarif;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
        this.numeroReservation = numeroReservation;
        this.etat=etat;
        this.client = client;
    }


    public float calculTarif(){
        // Calcul de la durée de location en jours
        long joursLoues = Duration.between(dateDebut, dateFin).toDays();

        // Calcul du coût total de la location
        float coutTotal = tarif * joursLoues;

        return coutTotal;

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
