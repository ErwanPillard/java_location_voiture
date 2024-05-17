package View.Employe;

import Dao.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InfoVoitureView extends JDialog {
    private JTable table;
    private String[] columnNames = {"Num réservation", "Début", "Fin", "Montant", "Nom Client"};

    public InfoVoitureView(Frame parent, String immatriculation) {
        super(parent, "Réservations en cours", true);
        setLayout(new BorderLayout());

        // Création du tableau
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Chargement des données
        loadData(immatriculation);

        setSize(800, 600);
        setLocationRelativeTo(parent);
    }

    private void loadData(String immatriculation) {
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

            // Extraction des données du ResultSet et remplissage du tableau
            Object[][] data = extractData(rs);
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Object[][] extractData(ResultSet rs) throws SQLException {
        rs.last();
        int rowCount = rs.getRow();
        rs.beforeFirst();

        Object[][] data = new Object[rowCount][5];
        int row = 0;
        while (rs.next()) {
            data[row][0] = rs.getInt("numReservation");
            data[row][1] = rs.getDate("dateDebutReservation");
            data[row][2] = rs.getDate("dateFinReservation");
            data[row][3] = rs.getFloat("montant");
            data[row][4] = rs.getString("nomClient");
            row++;
        }
        return data;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InfoVoitureView(null, "AB123CD").setVisible(true);
        });
    }
}
