package Controller;

import Controller.listeners.MailEvent;
import Controller.listeners.VoitureListener;
import Dao.DatabaseManager;
import Model.BoiteVitesse;
import Model.Categorie;
import Model.Modele;
import Model.Voiture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoitureController {

    private List<VoitureListener> voitureListeners = new ArrayList<VoitureListener>();

    private static final VoitureController instance = new VoitureController();

    public static VoitureController getInstance() {
        return instance;
    }

    public void addVoiture(Voiture voiture) throws SQLException {
        if (voiture != null) {
            voiture.add(voiture);
            notifyListeners(voiture);
        }
    }

    public Voiture findByImmat(String immatriculation) throws SQLException {
        return Voiture.findByImmat(immatriculation);
    }

    public void remove(String immatriculation) throws SQLException {
        Voiture voiture = Voiture.findByImmat(immatriculation);
        voiture.delete();
    }

    public void update(Voiture voiture) throws SQLException {
        voiture.update(voiture);
    }

    public List<Voiture> allVoitures() throws SQLException {
        return Voiture.all();
    }

    public List<Voiture> allFiltredCategorie(String categorie) throws SQLException {
        return Voiture.allFiltredCategorie(categorie);
    }

    public boolean immatExists(String immat) throws SQLException {
        return Voiture.immatExists(immat);
    }

    public synchronized void addUserListener(VoitureListener l) {
        if (!voitureListeners.contains(l)) {
            voitureListeners.add(l);
        }
    }

    private void notifyListeners(Voiture voiture) {
        MailEvent<Voiture> event = new MailEvent<Voiture>(voiture);
        for (VoitureListener listener : voitureListeners) {
            listener.voitureadd(event);
        }
    }

    public List<Voiture> findVoituresByModel(String model) throws SQLException {
        // Premièrement, récupérer l'ID du modèle
        String fetchModeleQuery = "SELECT id FROM Modele WHERE nom = ?";
        String fetchVoituresQuery = "SELECT * FROM Voiture WHERE modele_id = ?";

        Connection conn = DatabaseManager.getConnection();
        List<Voiture> voitures = new ArrayList<>();

        if (model.equals("Modèle")){
            try {
                voitures = allVoitures();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return voitures;
        }

        try (PreparedStatement fetchModeleStmt = conn.prepareStatement(fetchModeleQuery)) {
            fetchModeleStmt.setString(1, model);
            try (ResultSet rsModele = fetchModeleStmt.executeQuery()) {
                if (rsModele.next()) {
                    int id_modele = rsModele.getInt("id");

                    // Une fois l'ID récupéré, rechercher les voitures correspondantes
                    try (PreparedStatement stmt = conn.prepareStatement(fetchVoituresQuery)) {
                        stmt.setInt(1, id_modele);

                        try (ResultSet rs = stmt.executeQuery()) {
                            while (rs.next()) {
                                Voiture voiture = new Voiture(
                                        rs.getDate("dateMiseEnCirculation").toLocalDate(),
                                        rs.getString("immatriculation"),
                                        rs.getString("couleur"),
                                        rs.getDouble("nbKilometre"),
                                        rs.getInt("modele_id")
                                );
                                voitures.add(voiture);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la récupération des voitures ou des modèles : " + e.getMessage(), e);
        }
        return voitures;
    }
}
