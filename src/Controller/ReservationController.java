package Controller;

import Dao.DatabaseManager;
import Model.SessionManager;
import Model.Voiture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class ReservationController {

    public static void confirmerReservation(String dateDebut, String dateFin, Voiture voiture, float montant) {
        LocalDate dateDebutLocalDate;
        LocalDate dateFinLocalDate;
        int idClient = SessionManager.getCurrentClient().getId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            dateDebutLocalDate = LocalDate.parse(dateDebut, formatter);
            dateFinLocalDate = LocalDate.parse(dateFin, formatter);

            try (Connection conn = DatabaseManager.getConnection()) {
                String sql = "INSERT INTO Reservation (dateDebutReservation, dateFinReservation, montant, etat, voiture_immatriculation, id_client) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setDate(1, java.sql.Date.valueOf(dateDebutLocalDate));
                    stmt.setDate(2, java.sql.Date.valueOf(dateFinLocalDate));
                    stmt.setFloat(3, montant);
                    stmt.setString(4, "Confirmée");
                    stmt.setString(5, voiture.getImmatriculation());
                    stmt.setInt(6, idClient);

                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Réservation confirmée avec succès !");
                    } else {
                        JOptionPane.showMessageDialog(null, "Échec de la confirmation de la réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur lors de la confirmation de la réservation : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur de format de date : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
