package View.Employe;

import Controller.VoitureController;
import Model.BoiteVitesse;
import Model.Categorie;
import Model.Modele;
import Model.Voiture;
import View.layouts.Options;
import utils.PlaceholderTextField;
import utils.RoundBorder;
import utils.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static utils.Util.addFormField;

public class ParkAutoView2{

    private JComboBox<String> filterCategorieComboBox;
    private JComboBox<String> filterBoiteVitesseComboBox;
    private PlaceholderTextField searchField;
    private static JLabel imageLabel;
    private static JButton editButton;

    private VoitureJTable jTableList;

    public ParkAutoView2(JPanel jpBody){
        createView(jpBody);
        registerListeners();
    }


    private void registerListeners(){
        filterCategorieComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) filterCategorieComboBox.getSelectedItem();
                String selectedGearbox = (String) filterBoiteVitesseComboBox.getSelectedItem();
                ArrayList<Voiture> filteredCars = setupFilterListener(selectedCategory, selectedGearbox);
                jTableList.updateTable(filteredCars);
            }
        });

        filterBoiteVitesseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedCategory = (String) filterCategorieComboBox.getSelectedItem();
                String selectedGearbox = (String) filterBoiteVitesseComboBox.getSelectedItem();
                ArrayList<Voiture> filteredCars = setupFilterListener(selectedCategory, selectedGearbox);
                jTableList.updateTable(filteredCars);
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                VoitureJTable.search(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                VoitureJTable.search(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events
            }
        });
    }

    private ArrayList<Voiture> setupFilterListener(String selectedCategory, String selectedGearbox) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Voiture v JOIN Modele m ON v.modele_id = m.id WHERE 1=1");

        if (!selectedCategory.equals("--Non spécifié--")) {
            queryBuilder.append(" AND m.categorie = '").append(selectedCategory).append("'");
        }

        if (!selectedGearbox.equals("--Non spécifié--")) {
            queryBuilder.append(" AND m.boiteVitesse = '").append(selectedGearbox).append("'");
        }

        String query = queryBuilder.toString();

        try {
            return (ArrayList<Voiture>) VoitureController.getInstance().allFiltredCategorie(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }


    public void createView(JPanel jpBody) {
        // Créez un JPanel pour contenir le tableau et le JPanel rightPanel
        JPanel mainPanel = new JPanel();

        // Utilisation du GridBagLayout
        mainPanel.setLayout(new GridBagLayout());

        //Jtable into JScroll
        jTableList = new VoitureJTable();
        JScrollPane jspList = new JScrollPane();
        jspList.setViewportView(jTableList);

        jspList.setPreferredSize(new Dimension(900, 700));
        jspList.setMinimumSize(new Dimension(500, 500));

        // Création du champ de recherche avec une icône de loupe
        searchField = new PlaceholderTextField("Search");
        searchField.setBorder(BorderFactory.createCompoundBorder(
                searchField.getBorder(),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)));

        JLabel labelTriezParCategorie = new JLabel("Triez par catégorie");

        JLabel labelTriezParBoiteVitesse = new JLabel("Auto / Manuelle ?");

        String[] categories1 = new String[Categorie.values().length + 1];
        categories1[0] = "--Non spécifié--"; // Texte par défaut
        for (int i = 0; i < Categorie.values().length; i++) {
            categories1[i + 1] = Categorie.values()[i].getNomCategorie();
        }

        String[] categories2 = new String[BoiteVitesse.values().length + 1];
        categories2[0] = "--Non spécifié--"; // Texte par défaut
        for (int i = 0; i < BoiteVitesse.values().length; i++) {
            categories2[i + 1] = BoiteVitesse.values()[i].getTypeBoite().toUpperCase();
        }

        // Initialisez votre JComboBox avec les catégories obtenues
        filterCategorieComboBox = new JComboBox<>(categories1);
        filterBoiteVitesseComboBox = new JComboBox<>(categories2);

        //JLabel
        JLabel labelId = new JLabel("Id : ");
        JLabel labelMarque = new JLabel("Marque : ");
        JLabel labelNom = new JLabel("Nom : ");
        JLabel labelNbPlaces = new JLabel("Nombre de places : ");
        JLabel labelNbPortes = new JLabel("Nombre de portes : ");
        JLabel labelTailleCoffre = new JLabel("Taille du coffre : ");
        JLabel labelCaracteristiques = new JLabel("Caractéristiques : ");
        JLabel labelPrixJournalier = new JLabel("Prix journalier : ");
        JLabel labelNoteSatisfaction = new JLabel("Note de satisfaction : ");
        JLabel labelCategorie = new JLabel("Catégorie : ");
        JLabel labelAttelage = new JLabel("Attelage : ");
        JLabel labelBoiteVitesse = new JLabel("Boite de vitesse : ");

        Color backgroundColor = new Color(241, 243, 245);
        mainPanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();


        JLabel titleLabel = new JLabel("ParkAuto View");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);


        JButton btnVoiture = new JButton("Ajoute voiture");

        gbc.gridx = 0;
        gbc.gridy = 2; // la grille commence en (0, 0)

        mainPanel.add(btnVoiture, gbc);

        JButton btnModele = new JButton("Ajoute modèle");

        gbc.gridy++; // la grille commence en (0, 0)

        mainPanel.add(btnModele, gbc);

        gbc.gridx++;
        gbc.gridy = 0;

        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // seul composant de sa colonne, il est donc le dernier.
        gbc.gridheight = GridBagConstraints.REMAINDER; // valeur par défaut - peut s'étendre sur une seule ligne.

        gbc.anchor = GridBagConstraints.LINE_START; // ou BASELINE_LEADING mais pas WEST.

        gbc.insets = new Insets(0, 15, 0, 40);

        gbc.gridy = 1; // la grille commence en (0, 0)

        mainPanel.add(jspList, gbc);

        gbc.gridx++;
        gbc.gridy = 0;

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1; // valeur par défaut.


        gbc.weightx = 1.;
        gbc.weighty = 1.;

        gbc.fill = GridBagConstraints.BOTH;

        gbc.anchor = GridBagConstraints.LINE_START; // pas WEST.

        mainPanel.add(searchField, gbc);

        // Increment the gridy for the next component
        gbc.gridy++;

        // Add labels one below the other
        mainPanel.add(labelTriezParCategorie, gbc);
        gbc.gridy++;
        mainPanel.add(filterCategorieComboBox, gbc);
        gbc.gridy++;
        mainPanel.add(labelTriezParBoiteVitesse, gbc);
        gbc.gridy++;
        mainPanel.add(filterBoiteVitesseComboBox, gbc);
        gbc.gridy++;
        mainPanel.add(labelId, gbc);
        gbc.gridy++;
        mainPanel.add(labelMarque, gbc);
        gbc.gridy++;
        mainPanel.add(labelNom, gbc);
        gbc.gridy++;
        mainPanel.add(labelNbPlaces, gbc);
        gbc.gridy++;
        mainPanel.add(labelNbPortes, gbc);
        gbc.gridy++;
        mainPanel.add(labelTailleCoffre, gbc);
        gbc.gridy++;
        mainPanel.add(labelCaracteristiques, gbc);
        gbc.gridy++;
        mainPanel.add(labelPrixJournalier, gbc);
        gbc.gridy++;
        mainPanel.add(labelNoteSatisfaction, gbc);
        gbc.gridy++;
        mainPanel.add(labelCategorie, gbc);
        gbc.gridy++;
        mainPanel.add(labelAttelage, gbc);
        gbc.gridy++;
        mainPanel.add(labelBoiteVitesse, gbc);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Ajoutez le JPanel principal à jpBody
        jpBody.removeAll(); // Supprimer tous les composants existants de jpBody
        jpBody.setLayout(new BorderLayout());
        jpBody.add(new Options(jTableList), BorderLayout.SOUTH); // Options en bas
        jpBody.add(mainPanel, BorderLayout.CENTER); // Tableau et composants à droite
        jpBody.revalidate(); // Actualiser l'affichage
    }




}
