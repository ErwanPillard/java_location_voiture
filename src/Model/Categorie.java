package Model;

public enum Categorie{
    BERLINE("Berline"),
    SUV("SUV"),
    FAMILIALE("Familiale"),
    UTILITAIRE("Utilitaire"),
    CITADINE("Citadine");
    private final String nomCategorie;

    Categorie(String nomCategorie){
        this.nomCategorie = nomCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }
}
