package Model;


import java.time.LocalDateTime;

public class Facture {
    private int numFacture;
    private LocalDateTime dateEmission;
    private LocalDateTime dateDebutReservation;
    private LocalDateTime dateFinReservation;
    private float montant;
    private String etat;
    private String voiture_immatriculation;
    private int id_client;
    private String objet;

    public Facture(int numFacture, LocalDateTime date, LocalDateTime dateDebutReservation, LocalDateTime dateFinReservation, float montant, String etat, String voiture_immatriculation, int id_client, String objet) {
        this.numFacture = numFacture;
        this.dateEmission = date;
        this.dateDebutReservation = dateDebutReservation;
        this.dateFinReservation = dateFinReservation;
        this.montant = montant;
        this.etat = etat;
        this.voiture_immatriculation = voiture_immatriculation;
        this.id_client = id_client;
        this.objet = objet;
    }

    public int getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(int numFacture) {
        this.numFacture = numFacture;
    }

    public LocalDateTime getDate() {
        return dateEmission;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public LocalDateTime getDateDebutReservation() {
        return dateDebutReservation;
    }

    public LocalDateTime getDateFinReservation() {
        return dateFinReservation;
    }

    public void setDate(LocalDateTime date) {
        this.dateEmission = date;
    }

    public void setDateDebutReservation(LocalDateTime dateDebutReservation) {
        this.dateDebutReservation = dateDebutReservation;
    }

    public void setDateFinReservation(LocalDateTime dateFinReservation) {
        this.dateFinReservation = dateFinReservation;
    }

    public String getVoiture_immatriculation() {
        return voiture_immatriculation;
    }

    public void setVoiture_immatriculation(String voiture_immatriculation) {
        this.voiture_immatriculation = voiture_immatriculation;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
