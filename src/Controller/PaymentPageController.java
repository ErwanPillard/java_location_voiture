package Controller;

import Dao.DatabaseManager;
import Model.SessionManager;
import Model.Voiture;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentPageController {
    static int rowsInserted;
    public static void ajouterFacture(String dateDebut, String dateFin, Voiture voiture, float montant) {
        LocalDate dateEmission;
        LocalDate dateDebutLocalDate;
        LocalDate dateFinLocalDate;
        int idClient = SessionManager.getCurrentClient().getId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            dateEmission = LocalDate.now();
            dateDebutLocalDate = LocalDate.parse(dateDebut, formatter);
            dateFinLocalDate = LocalDate.parse(dateFin, formatter);

            try (Connection conn = DatabaseManager.getConnection()) {
                String sql = "INSERT INTO Facture (dateEmission, dateDebutReservation, dateFinReservation, montant, etat, voiture_immatriculation, id_client) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setDate(1, java.sql.Date.valueOf(dateEmission));
                    stmt.setDate(2, java.sql.Date.valueOf(dateDebutLocalDate));
                    stmt.setDate(3, java.sql.Date.valueOf(dateFinLocalDate));
                    stmt.setFloat(4, montant);
                    stmt.setString(5, "Payé");
                    stmt.setString(6, voiture.getImmatriculation());
                    stmt.setInt(7, idClient);

                    rowsInserted = stmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de facture' : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur de format de date : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
