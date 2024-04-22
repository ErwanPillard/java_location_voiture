package Model;

import Dao.ClientDAO;
import Dao.ClientDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;

public class Particulier extends Client {
    private int id;
    private String nom;
    private String prenom;
    private String numeroPermis;
    private LocalDate birthDate;

/*
    public Particulier(String nom, String prenom, String email, String motDePasse, int age, String telephone, double numeroAdhesion, TypeAdhesion typeAdhesion, String numeroPermis, LocalDate birthDate) {
        super(email, motDePasse, telephone, numeroAdhesion, typeAdhesion);
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
        this.age = age;
    }*/

    public Particulier(String nom, String prenom, String email, String motDePasse, String telephone, String numeroPermis, LocalDate birthDate) {
        super(email, motDePasse, telephone);
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
    }

    public Particulier(int id, String nom, String prenom, String numeroPermis, LocalDate birthDate) {
        super(id);
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroPermis() {
        return numeroPermis;
    }

    public void setNumeroPermis(String numeroPermis) {
        this.numeroPermis = numeroPermis;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void add(Particulier particulier) throws SQLException {
        ClientDAO clientDAO = new ClientDAOImpl();
        clientDAO.addParticulier(particulier);
    }
}
