package View.Employe.BaseClient;

import Model.Client;
import Model.Entreprise;
import Model.Voiture;
import View.Employe.VoitureJTable;
import View.layouts.Options;
import utils.PlaceholderTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientBaseView {
    private ParticulierJTable jTableListParticulier;
    private EntrepriseJTable jTableListEntreprise;
    private PlaceholderTextField searchFieldParticulier;
    private PlaceholderTextField searchFieldEntreprise;


    private static final JLabel labelId = new JLabel("Id : ");
    private static final JLabel labelTelephone = new JLabel("N°Téléphone : ");

    public ClientBaseView(JPanel jpBody){
        createView(jpBody);
        registerListeners();
    }
    public void createView(JPanel jpBody) {
        // Créez un JPanel pour contenir le tableau et le JPanel rightPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel tabPanel = new JPanel(); //Panel Tableau voiture
        JPanel filterPanel = new JPanel();
        JPanel categoriePanel = new JPanel();

        Color backgroundColor = new Color(123, 183, 191);
        mainPanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();

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

    private void registerListeners() {
        searchFieldParticulier.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ParticulierJTable.search(searchFieldParticulier.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ParticulierJTable.search(searchFieldParticulier.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events
            }
        });

        searchFieldEntreprise.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                EntrepriseJTable.search(searchFieldEntreprise.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                EntrepriseJTable.search(searchFieldEntreprise.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events
            }
        });
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

        // Création du champ de recherche pour Particulier
        searchFieldParticulier = new PlaceholderTextField("Search Particulier");
        searchFieldParticulier.setBorder(BorderFactory.createCompoundBorder(
                searchFieldParticulier.getBorder(),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        searchFieldParticulier.setPreferredSize(new Dimension(300, 25));

        // Création du champ de recherche pour Entreprise
        searchFieldEntreprise = new PlaceholderTextField("Search Entreprise");
        searchFieldEntreprise.setBorder(BorderFactory.createCompoundBorder(
                searchFieldEntreprise.getBorder(),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        searchFieldEntreprise.setPreferredSize(new Dimension(300, 25));

        JPanel labelAndSearchPanelParticulier = new JPanel(new BorderLayout());
        labelAndSearchPanelParticulier.add(jLabelParticulier, BorderLayout.WEST);
        labelAndSearchPanelParticulier.add(searchFieldParticulier, BorderLayout.EAST);
        labelAndSearchPanelParticulier.setBackground(Color.white);

        JPanel labelAndSearchPanelEntreprise = new JPanel(new BorderLayout());
        labelAndSearchPanelEntreprise.add(jLabelEntreprise, BorderLayout.WEST);
        labelAndSearchPanelEntreprise.add(searchFieldEntreprise, BorderLayout.EAST);
        labelAndSearchPanelEntreprise.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
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
        gbc.gridwidth = 1;
        tabPanel.add(labelAndSearchPanelParticulier, gbc);
        gbc.gridy = 2;
        tabPanel.add(labelAndSearchPanelEntreprise, gbc);

        return tabPanel;
    }




    protected static void displayModele(Client client) {
        int id = client.getId();
        //String telephone = Client.getTelephone();

        labelId.setText("Id : " + id);
        //labelTelephone.setText("N°téléphone : " + telephone);
    }


}
