package Controller;

import Model.Voiture;

import java.sql.SQLException;

public class VoitureController {
    private static final VoitureController instance = new VoitureController();
    public static VoitureController getInstance() {
        return instance;
    }

    public void addVoiture(Voiture voiture) throws SQLException {
        if (voiture != null){
            voiture.add(voiture);
            System.out.println("OK2");
        }
    }

}
