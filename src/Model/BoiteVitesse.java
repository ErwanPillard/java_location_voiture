package Model;

public enum BoiteVitesse {
    AUTOMATIQUE("automatique"),
    MANUEL("manuel");
    private final String typeBoite;

    BoiteVitesse(String typeBoite) {
        this.typeBoite = typeBoite;
    }

    public String getTypeBoite() {
        return typeBoite;
    }
}
