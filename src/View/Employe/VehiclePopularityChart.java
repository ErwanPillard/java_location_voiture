package View.Employe;

import Dao.DatabaseManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiclePopularityChart extends JFrame {

    public VehiclePopularityChart() {
        // Créer les datasets
        DefaultPieDataset modelDataset = new DefaultPieDataset();
        DefaultPieDataset categoryDataset = new DefaultPieDataset();
        DefaultPieDataset parkDataset = new DefaultPieDataset();

        // Charger les données dans les datasets
        loadModelData(modelDataset);
        loadCategoryData(categoryDataset);
        loadParkData(parkDataset);

        // Créer les charts
        JFreeChart modelChart = createChart(modelDataset, "Popularité des Modèles de Véhicules");
        JFreeChart categoryChart = createChart(categoryDataset, "Popularité des Catégories de Véhicules");
        JFreeChart parkChart = createChart(parkDataset, "Répartition des Catégories du Parc Auto");

        // Créer les panels pour les charts
        ChartPanel modelChartPanel = new ChartPanel(modelChart);
        ChartPanel categoryChartPanel = new ChartPanel(categoryChart);
        ChartPanel parkChartPanel = new ChartPanel(parkChart);

        // Configurer les panels
        modelChartPanel.setPreferredSize(new Dimension(400, 300));
        categoryChartPanel.setPreferredSize(new Dimension(400, 300));
        parkChartPanel.setPreferredSize(new Dimension(400, 300));

        // Utiliser un layout pour afficher les charts côte à côte
        setLayout(new GridLayout(1, 3));
        add(modelChartPanel);
        add(categoryChartPanel);
        add(parkChartPanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadModelData(DefaultPieDataset dataset) {
        String sql = "SELECT m.nom AS voiture_modele, COUNT(*) AS count " +
                "FROM Reservation r " +
                "JOIN Voiture v ON r.voiture_immatriculation = v.immatriculation " +
                "JOIN Modele m ON v.modele_id = m.id " +
                "GROUP BY m.nom";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String model = rs.getString("voiture_modele");
                int count = rs.getInt("count");
                dataset.setValue(model, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données des modèles : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCategoryData(DefaultPieDataset dataset) {
        String sql = "SELECT m.categorie AS voiture_categorie, COUNT(*) AS count " +
                "FROM Reservation r " +
                "JOIN Voiture v ON r.voiture_immatriculation = v.immatriculation " +
                "JOIN Modele m ON v.modele_id = m.id " +
                "GROUP BY m.categorie";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String category = rs.getString("voiture_categorie");
                int count = rs.getInt("count");
                dataset.setValue(category, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données des catégories : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadParkData(DefaultPieDataset dataset) {
        String sql = "SELECT m.categorie AS voiture_categorie, COUNT(*) AS count " +
                "FROM Voiture v " +
                "JOIN Modele m ON v.modele_id = m.id " +
                "GROUP BY m.categorie";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String category = rs.getString("voiture_categorie");
                int count = rs.getInt("count");
                dataset.setValue(category, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données du parc auto : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JFreeChart createChart(DefaultPieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart(
                title,
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("Aucune donnée à afficher");
        plot.setCircular(true);
        plot.setLabelGap(0.02);

        return chart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VehiclePopularityChart chart = new VehiclePopularityChart();
            chart.pack();
            chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chart.setVisible(true);
        });
    }
}
