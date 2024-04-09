package Model;

import java.time.LocalDate;

public class Particulier extends Client{
    private String numeroPermis;
    private LocalDate birthDate;
    private String nom;
    private String prenom;

    public Particulier(String nom, String prenom, String email, String motDePasse, int age, String telephone, double numeroAdhesion, TypeAdhesion typeAdhesion, String numeroPermis, LocalDate birthDate) {
        super(email, motDePasse, age, telephone, numeroAdhesion, typeAdhesion);
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
    }

    public Particulier(String nom, String prenom, String email, String motDePasse, int age, String telephone, String numeroPermis, LocalDate birthDate){
        super(email, motDePasse, age, telephone);
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
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
}
