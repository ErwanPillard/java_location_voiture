package Model;

public class Employe extends User{
    private String fonction;

    public Employe(String nom, String prenom, String email, String motsDePasse, int id, String fonction) {
        super(nom, prenom, email, motsDePasse, id);
        this.fonction = fonction;
    }
    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }
}
