
public enum TypeAdhesion {
    NOUVEAUCLIENT(0,6, 0),
    BRONZE(6,12,5),
    ARGENT(12, 24, 10),
    OR(24, 100000, 15);
    private final int tempsAncieneteMin; // en mois
    private final int tempsAncieneteMax; // en mois
    private final int tauxRemise; // en %

    TypeAdhesion(int tempsAncieneteMin, int tempsAncieneteMax, int tauxRemise) {
        this.tempsAncieneteMin = tempsAncieneteMin;
        this.tempsAncieneteMax = tempsAncieneteMax;
        this.tauxRemise = tauxRemise;
    }
}


