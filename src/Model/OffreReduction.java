package Model;

import Dao.OffreReductionDAO;
import Dao.OffreReductionDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OffreReduction {
    private int id;
    private String nom;
    private String description;
    private static LocalDate dateDebut;
    private static LocalDate dateFin;
    private float pourcentageReduction;
    private String typeAdhesion;

    // Constructeur
    public OffreReduction(String nom, String description, LocalDate dateDebut, LocalDate dateFin,
                          float pourcentageReduction, String typeAdhesion) {
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.pourcentageReduction = pourcentageReduction;
        this.typeAdhesion = typeAdhesion;
    }

    public OffreReduction(int id, String nom, String description, LocalDate dateDebut, LocalDate dateFin,
                          float pourcentageReduction, String typeAdhesion) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.pourcentageReduction = pourcentageReduction;
        this.typeAdhesion = typeAdhesion;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

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

    public static LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        OffreReduction.dateDebut = dateDebut;
    }

    public static LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        OffreReduction.dateFin = dateFin;
    }

    public float getPourcentageReduction() {
        return pourcentageReduction;
    }

    public void setPourcentageReduction(float pourcentageReduction) {
        this.pourcentageReduction = pourcentageReduction;
    }

    public String getTypeAdhesion() {
        return typeAdhesion;
    }

    public void setTypeAdhesion(String typeAdhesion) {
        this.typeAdhesion = typeAdhesion;
    }

    public Object[] toArray() {
        return new Object[] {this.nom, this.description, this.dateDebut, this.dateFin, this.pourcentageReduction, this.typeAdhesion};
    }

    public static List<OffreReduction> all() throws SQLException {
        OffreReductionDAO offreReductionDAO = new OffreReductionDAOImpl();
        return offreReductionDAO.all();
    }

    public void add(OffreReduction offreReduction) throws SQLException {
        OffreReductionDAO offreReductionDAO = new OffreReductionDAOImpl();
        offreReductionDAO.add(offreReduction);
    }
}
