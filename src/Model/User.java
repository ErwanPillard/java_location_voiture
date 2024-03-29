package Model;

public class User {
    private String nom;
    private String prenom;
    private String email;
    private String motsDePasse;
    private int id;

    public User(String nom, String prenom, String email, String motsDePasse, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motsDePasse = motsDePasse;
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

    public String getMotsDePasse() {
        return motsDePasse;
    }

    public void setMotsDePasse(String motsDePasse) {
        this.motsDePasse = motsDePasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}