package Model;

import java.time.LocalDateTime;

public class Particulier extends Client{
    private String numeroPermis;
    private LocalDateTime birthDate;

    public Particulier(String nom, String prenom, String email, String motDePasse, int age, String telephone, double numeroAdhesion, TypeAdhesion typeAdhesion, String numeroPermis, LocalDateTime birthDate) {
        super(nom, prenom, email, motDePasse, age, telephone, numeroAdhesion, typeAdhesion);
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
    }

    public Particulier(String nom, String prenom, String email, String motDePasse, int age, String telephone, String numeroPermis, LocalDateTime birthDate){
        super(nom, prenom, email, motDePasse, age, telephone);
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
    }

    public String getNumeroPermis() {
        return numeroPermis;
    }

    public void setNumeroPermis(String numeroPermis) {
        this.numeroPermis = numeroPermis;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }
}
