package View.Employe;

import Dao.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoVoitureJTable extends JTable {

    private String[] columnNames = {"Num réservation", "Début", "Fin", "Montant", "Nom Client"};

    public InfoVoitureJTable() {
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        setModel(model);
        getTableHeader().setReorderingAllowed(false);
    }

    void updateTable(String immatriculation) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        clearTable();
        loadData(immatriculation, model);
    }

    private void loadData(String immatriculation, DefaultTableModel model) {
        String sql = "SELECT r.numReservation, r.dateDebutReservation, r.dateFinReservation, r.montant, " +
                "CONCAT(p.nom, ' ', p.prenom) AS nomClient " +
                "FROM Reservation r " +
                "JOIN Client c ON r.id_client = c.id " +
                "JOIN Particulier p ON c.id = p.id " +
                "WHERE r.voiture_immatriculation = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, immatriculation);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] rowData = new Object[5];
                rowData[0] = rs.getInt("numReservation");
                rowData[1] = rs.getDate("dateDebutReservation");
                rowData[2] = rs.getDate("dateFinReservation");
                rowData[3] = rs.getFloat("montant");
                rowData[4] = rs.getString("nomClient");
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(0); // Effacer toutes les lignes du modèle
    }
}
