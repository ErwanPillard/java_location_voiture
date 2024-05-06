package View.Employe;

import Dao.DatabaseManager;
import Model.OffreReduction;
import Model.TypeAdhesion;
import View.layouts.Options;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OffreReductionView {
    private OffreReductionJTable jTableList;


    public OffreReductionView(JPanel jpBody) {
        createView(jpBody);

    }

    public JPanel tabPanel(JPanel tabPanel) {

        tabPanel.setLayout(new GridBagLayout());
        //Jtable into JScroll
        jTableList = new OffreReductionJTable();
        JScrollPane jspList = new JScrollPane();

        jspList.setViewportView(jTableList);
        jspList.setPreferredSize(new Dimension(900, 700));
        jspList.setMinimumSize(new Dimension(500, 500));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 1; // seul composant de sa colonne, il est donc le dernier.
        gbc.gridheight = GridBagConstraints.REMAINDER; // valeur par défaut - peut s'étendre sur une seule ligne.

        tabPanel.add(jspList, gbc);

        return tabPanel;
    }

    public void createView(JPanel jpBody) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(235, 237, 239));

        JPanel tabPanel = new JPanel(new GridBagLayout()); //Panel Tableau voiture

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER; // valeur par défaut - peut s'étendre sur une seule ligne.
        mainPanel.add(tabPanel(tabPanel), gbc);

        jpBody.removeAll();
        jpBody.setLayout(new BorderLayout());
        jpBody.add(mainPanel, BorderLayout.CENTER);
        jpBody.revalidate();
    }



    // Méthode pour récupérer les offres de réduction depuis la base de données
    private ArrayList<OffreReduction> getOffresFromDatabase() {
        ArrayList<OffreReduction> offres = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM OffreReduction";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");
                LocalDate dateDebut = resultSet.getDate("dateDebut").toLocalDate();
                LocalDate dateFin = resultSet.getDate("dateFin").toLocalDate();
                float pourcentageReduction = resultSet.getFloat("pourcentageReduction");
                TypeAdhesion typeAdhesion = TypeAdhesion.valueOf(resultSet.getString("typeAdhesion"));
                OffreReduction offre = new OffreReduction(nom, description, dateDebut, dateFin, pourcentageReduction, typeAdhesion);
                offres.add(offre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offres;
    }
}
