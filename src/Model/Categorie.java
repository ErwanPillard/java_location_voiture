package Model;

public enum Categorie{
    BERLINE("berline"),
    SUV("SUV"),
    FAMILLIALE("familliale"),
    UTILITAIRE("utilitaire"),
    CITADINE("citadine");
    private final String nomCatergorie;
    Categorie(String nomCatergorie){
        this.nomCatergorie= nomCatergorie;
    }
}
