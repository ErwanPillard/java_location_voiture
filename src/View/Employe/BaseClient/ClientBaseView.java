package View.Employe.BaseClient;

import Model.Client;
import Model.Entreprise;
import View.layouts.Options;

import javax.swing.*;
import java.awt.*;

public class ClientBaseView {

    private ParticulierJTable jTableListParticulier;
    private EntrepriseJTable jTableListEntreprise;


    private static final JLabel labelId = new JLabel("Id : ");
    private static final JLabel labelTelephone = new JLabel("N°Téléphone : ");

    public ClientBaseView(JPanel jpBody){
        createView(jpBody);
    }
    public void createView(JPanel jpBody) {
        // Créez un JPanel pour contenir le tableau et le JPanel rightPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel tabPanel = new JPanel(); //Panel Tableau voiture
        JPanel filterPanel = new JPanel();
        JPanel categoriePanel = new JPanel();

        Color backgroundColor = new Color(55, 126, 34);
        mainPanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();

        /*gbc.gridx = 2;
        filterPanel.setBackground(Color.white);
        filterPanel.setPreferredSize(new Dimension(300, 200));
        mainPanel.add(filterPanel(filterPanel), gbc);

        gbc.gridy = 1;
        categoriePanel.setBackground(Color.white);
        categoriePanel.setPreferredSize(new Dimension(300, 400));
        mainPanel.add(categoriePanel(categoriePanel), gbc);*/


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER; // valeur par défaut - peut s'étendre sur une seule ligne.
        gbc.insets = new Insets(0, 0, 0, 20);
        mainPanel.add(tabPanel(tabPanel), gbc);


        // Ajoutez le JPanel principal à jpBody
        jpBody.removeAll(); // Supprimer tous les composants existants de jpBody
        jpBody.setLayout(new BorderLayout());
        jpBody.add(mainPanel, BorderLayout.CENTER); // Tableau et composants à droite
        jpBody.revalidate(); // Actualiser l'affichage
    }

    public JPanel tabPanel(JPanel tabPanel) {
        tabPanel.setLayout(new GridBagLayout());
        tabPanel.setBackground(Color.white);

        JLabel jLabelParticulier = new JLabel("Particulier");
        JLabel jLabelEntreprise = new JLabel("Entreprise");
        jLabelParticulier.setFont(new Font("Arial", Font.BOLD, 20)); // Police Arial en gras
        jLabelEntreprise.setFont(new Font("Arial", Font.BOLD, 20)); // Police Arial en gras

        // Création des JScrollPane pour chaque JTable
        JScrollPane jspListParticulier = new JScrollPane();
        JScrollPane jspListEntreprise = new JScrollPane();

        // Création des JTables
        jTableListParticulier = new ParticulierJTable();
        jTableListEntreprise = new EntrepriseJTable();

        // Ajout des JTables dans les JScrollPane
        jspListParticulier.setViewportView(jTableListParticulier);
        jspListParticulier.setPreferredSize(new Dimension(1000, 300));
        jspListParticulier.setMinimumSize(new Dimension(500, 300));

        jspListEntreprise.setViewportView(jTableListEntreprise);
        jspListEntreprise.setPreferredSize(new Dimension(1000, 300));
        jspListEntreprise.setMinimumSize(new Dimension(500, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 1;
        tabPanel.add(jspListParticulier, gbc);
        gbc.gridy = 3;
        // Ajout du JScrollPane pour la JTable Entreprise
        tabPanel.add(jspListEntreprise, gbc);

        gbc.insets = new Insets(10, 10, 10, 0); // Ajout d'un espace de 5 pixels entre les deux JScrollPanes
        gbc.gridy = 0;
        tabPanel.add(jLabelParticulier, gbc);
        gbc.gridy = 2;
        tabPanel.add(jLabelEntreprise, gbc);

        return tabPanel;
    }

    protected static void displayModele(Client client) {
        int id = client.getId();
        //String telephone = Client.getTelephone();

        labelId.setText("Id : " + id);
        //labelTelephone.setText("N°téléphone : " + telephone);
    }


}
