package Model;

public enum BoiteVitesse {
    AUTOMATIC("automatique"),
    MANUEL("manuel");
    private final String typeBoite;

    BoiteVitesse(String typeBoite) {
        this.typeBoite = typeBoite;
    }
}
