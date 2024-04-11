package Model;

public class Entreprise extends Client {
    private int id;
    private String nom;
    private String numSiret;

    /*public Entreprise(String nom, String email, String motDePasse, int age, String telephone, double numeroAdhesion, TypeAdhesion typeAdhesion, String numSiret) {
        super(email, motDePasse, age, telephone, numeroAdhesion, typeAdhesion);
        this.nom = nom;
        this.numSiret = numSiret;
    }

    public Entreprise(int id, String nom, String email, String motDePasse, int age, String telephone, String numSiret) {
        super(email, motDePasse, age, telephone);
        this.nom = nom;
        this.numSiret = numSiret;
        this.id = id;
    }*/

    public Entreprise(int id, String nom, String numSiret) {
        super(id);
        this.nom = nom;
        this.numSiret = numSiret;
        this.id = id;
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

    public String getNumSiret() {
        return numSiret;
    }

    public void setNumSiret(String numSiret) {
        this.numSiret = numSiret;
    }
}
