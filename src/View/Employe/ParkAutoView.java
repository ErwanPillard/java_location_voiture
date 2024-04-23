package View.Employe;

import Controller.VoitureController;
import Model.Categorie;
import View.Employe.VoitureJTable;

import Model.Voiture;
import View.layouts.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static utils.Util.addFormField;

public class ParkAutoView{

    JComboBox<String> filterCategorieComboBox;

    VoitureJTable jTableList;

    public ParkAutoView(JPanel jpBody){
        createView(jpBody);
        registerListeners();
    }


    private void registerListeners(){
        filterCategorieComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String selectedCategory = (String) filterCategorieComboBox.getSelectedItem();

                if (selectedCategory.equals("--Non spécifié--")){
                    jTableList.updateTableWithCategory(jTableList.loadAll());
                }else {
                    String query = String.format("SELECT * FROM Voiture v JOIN Modele m ON v.modele_id = m.id WHERE m.categorie = '%s'", selectedCategory);
                    setupCategoryFilterListener(query);
                }
            }
        });
    }

    private void setupCategoryFilterListener(String query) {
        try {
            ArrayList<Voiture> Cars = (ArrayList<Voiture>) VoitureController.getInstance().allFiltredCategorie(query);
            jTableList.updateTableWithCategory(Cars);
        } catch (SQLException ex) {
            // Gérez les erreurs liées à la récupération des voitures de la catégorie "Berline" depuis la base de données
            ex.printStackTrace();
        }
    }

    public void filtrePanel(JPanel mainPanel){
        // Créez un nouveau JPanel pour contenir les composants de droite
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(20, 1)); // 2 lignes, 1 colonne

        String[] categories = new String[Categorie.values().length + 1];
        categories[0] = "--Non spécifié--"; // Texte par défaut
        for (int i = 0; i < Categorie.values().length; i++) {
            categories[i + 1] = Categorie.values()[i].getNomCategorie();
        }
        // Initialisez votre JComboBox avec les catégories obtenues
        filterCategorieComboBox = new JComboBox<>(categories);

        JTextField textField = new JTextField("Texte initial", 1);

        // Ajoutez les composants à droite dans le JPanel rightPanel
        rightPanel.add(filterCategorieComboBox);
        rightPanel.add(textField);

        // Ajoutez le JPanel rightPanel à droite dans le JPanel principal avec des contraintes de position
        GridBagConstraints gbcRightPanel = new GridBagConstraints();
        gbcRightPanel.gridx = 1; // Colonne 1
        gbcRightPanel.gridy = 0; // Ligne 0
        gbcRightPanel.weightx = 0.1; // Poids horizontal (pour réduire la largeur)
        gbcRightPanel.weighty = 1.0; // Poids vertical (pour occuper l'espace disponible)
        gbcRightPanel.fill = GridBagConstraints.BOTH; // Remplissage dans les deux sens
        mainPanel.add(rightPanel, gbcRightPanel);
    }

    public void createView(JPanel jpBody) {

        JScrollPane jspList = new JScrollPane();
        jTableList = new VoitureJTable();
        jspList.setViewportView(jTableList);

        // Créez un JPanel pour contenir le tableau et le JPanel rightPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); // Utilisation du GridBagLayout

        // Ajoutez le tableau à gauche dans le JPanel principal avec des contraintes de position
        GridBagConstraints gbcTable = new GridBagConstraints();
        gbcTable.gridx = 0; // Colonne 0
        gbcTable.gridy = 0; // Ligne 0
        gbcTable.weightx = 1.0; // Poids horizontal (pour occuper l'espace disponible)
        gbcTable.weighty = 1.0; // Poids vertical (pour occuper l'espace disponible)
        gbcTable.fill = GridBagConstraints.BOTH; // Remplissage dans les deux sens
        mainPanel.add(jspList, gbcTable);

        filtrePanel(mainPanel);

        // Ajoutez le JPanel principal à jpBody
        jpBody.removeAll(); // Supprimer tous les composants existants de jpBody
        jpBody.add(new Options(jTableList), BorderLayout.SOUTH); // Options en bas
        jpBody.add(mainPanel, BorderLayout.CENTER); // Tableau et composants à droite
        jpBody.revalidate(); // Actualiser l'affichage
    }

}
