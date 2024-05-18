package View.Employe;

import Dao.DatabaseManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RevenueChart extends ApplicationFrame {

    public RevenueChart(String title) {
        super(title);

        // Créer le dataset pour le chiffre d'affaires en fonction du temps
        DefaultCategoryDataset dataset = createDataset();

        // Créer le graphique en ligne
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Chiffre d'Affaires Réalisé",
                "Temps",
                "Chiffre d'Affaires (€)",
                dataset
        );

        // Créer le panel pour le graphique
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        // Ajouter le panel à la frame
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Charger les données du chiffre d'affaires par mois
        loadRevenueData(dataset);

        return dataset;
    }

    private void loadRevenueData(DefaultCategoryDataset dataset) {
        String sql = "SELECT MONTH(dateEmission) AS mois, SUM(montant) AS chiffre_affaires " +
                "FROM Facture " +
                "GROUP BY MONTH(dateEmission)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("mois");
                double revenue = rs.getDouble("chiffre_affaires");
                dataset.addValue(revenue, "Chiffre d'Affaires", String.valueOf(month));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données du chiffre d'affaires : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RevenueChart chart = new RevenueChart("Chiffre d'Affaires Réalisé");
            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);
        });
    }
}
