package Model;

import java.util.Date;

public class User {
    private int id;
    private int age;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String numPermis;
    private Date dateNaissance;
    private String numSiret;
    private String fonctionEmploye;

    public User(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public User(int id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public User(int age, String numPermis, Date dateNaissance, String numSiret, String fonctionEmploye) {
        this.age = age;
        this.numPermis = numPermis;
        this.dateNaissance = dateNaissance;
        this.numSiret = numSiret;
        this.fonctionEmploye = fonctionEmploye;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumPermis() {
        return numPermis;
    }

    public void setNumPermis(String numPermis) {
        this.numPermis = numPermis;
    }

    public String getNumSiret() {
        return numSiret;
    }

    public void setNumSiret(String numSiret) {
        this.numSiret = numSiret;
    }

    public String getFonctionEmploye() {
        return fonctionEmploye;
    }

    public void setFonctionEmploye(String fonctionEmploye) {
        this.fonctionEmploye = fonctionEmploye;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
