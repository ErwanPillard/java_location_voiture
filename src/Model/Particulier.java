package Model;

import java.time.LocalDate;

public class Particulier extends Client {
    private int id;
    private String nom;
    private String prenom;
    private String numeroPermis;
    private LocalDate birthDate;
    private int age;


    /*public Particulier(String nom, String prenom, String email, String motDePasse, int age, String telephone, double numeroAdhesion, TypeAdhesion typeAdhesion, String numeroPermis, LocalDate birthDate) {
        super(email, motDePasse, age, telephone, numeroAdhesion, typeAdhesion);
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
    }

    public Particulier(int id, String nom, String prenom, String email, String motDePasse, String telephone, String numeroPermis, LocalDate birthDate, int age) {
        super(email, motDePasse, age, telephone);
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
    }*/

    public Particulier(int id, String nom, String prenom, String numeroPermis, LocalDate birthDate, int age) {
        super(id);
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.birthDate = birthDate;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
