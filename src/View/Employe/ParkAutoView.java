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

public class ParkAutoView{

    JComboBox<String> filterCategorieComboBox;

    VoitureJTable jTableList;

    public ParkAutoView(JPanel jpBody){
        createTable(jpBody);
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

    public void createTable(JPanel jpBody){
        String[] categories = new String[Categorie.values().length + 1];
        categories[0] = "--Non spécifié--"; // Texte par défaut
        for (int i = 0; i < Categorie.values().length; i++) {
            categories[i + 1] = Categorie.values()[i].getNomCategorie();
        }

        // Initialisez votre JComboBox avec les catégories obtenues
        filterCategorieComboBox = new JComboBox<>(categories);

        JScrollPane jspList = new JScrollPane();
        jTableList = new VoitureJTable();
        jspList.setViewportView(jTableList);

        // Ajouter les éléments à votre interface utilisateur
        jpBody.removeAll(); // Supprimer tous les composants existants de jpBody
        jpBody.add(filterCategorieComboBox, BorderLayout.NORTH);
        jpBody.add(new Options(jTableList), BorderLayout.SOUTH);
        jpBody.add(jspList, BorderLayout.CENTER);
        jpBody.revalidate(); // Actualiser l'affichage
    }
}
