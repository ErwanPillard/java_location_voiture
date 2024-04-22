package View.Employe;

import View.layouts.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParkAutoView{

    JComboBox<String> filterComboBox;

    public ParkAutoView(JPanel jpBody){
        createTable(jpBody);
        registerListeners();
    }


    private void registerListeners(){
        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
    }
    public void createTable(JPanel jpBody){
        // Instancier le JScrollPane et le VoitureJTableView
        filterComboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});

        JScrollPane jspList = new JScrollPane();
        VoitureJTable jTableList = new VoitureJTable();
        jspList.setViewportView(jTableList);

        // Ajouter les éléments à votre interface utilisateur
        jpBody.removeAll(); // Supprimer tous les composants existants de jpBody
        jpBody.add(filterComboBox, BorderLayout.NORTH);
        jpBody.add(new Options(jTableList), BorderLayout.SOUTH);
        jpBody.add(jspList, BorderLayout.CENTER);
        jpBody.revalidate(); // Actualiser l'affichage
    }
}
