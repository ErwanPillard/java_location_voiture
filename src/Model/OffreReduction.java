package Model;
import java.time.LocalDate;

public class OffreReduction {
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private float pourcentageReduction;
    private TypeAdhesion typeAdhesion;

    // Constructeur
    public OffreReduction(String nom, String description, LocalDate dateDebut, LocalDate dateFin,
                          float pourcentageReduction, TypeAdhesion typeAdhesion) {
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.pourcentageReduction = pourcentageReduction;
        this.typeAdhesion = typeAdhesion;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public float getPourcentageReduction() {
        return pourcentageReduction;
    }

    public void setPourcentageReduction(float pourcentageReduction) {
        this.pourcentageReduction = pourcentageReduction;
    }

    public TypeAdhesion getIdTypeAdhesion() {
        return typeAdhesion;
    }

    public void setIdTypeAdhesion(TypeAdhesion idTypeAdhesion) {
        this.typeAdhesion = idTypeAdhesion;
    }
}