package View.Employe;

import Controller.ClientController;
import Controller.ModeleController;
import Controller.VoitureController;
import Controller.listeners.ClientListener;
import Controller.listeners.MailEvent;
import Controller.listeners.VoitureListener;
import Model.Client;
import Model.Modele;
import Model.Particulier;
import Model.Voiture;
import View.layouts.Options;
import View.listeners.EventListener;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientBaseView {

    private ClientJTable jTableList;


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

        Color backgroundColor = new Color(21, 21, 23);
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
        jpBody.add(new Options(jTableList), BorderLayout.SOUTH); // Options en bas
        jpBody.add(mainPanel, BorderLayout.CENTER); // Tableau et composants à droite
        jpBody.revalidate(); // Actualiser l'affichage



    }

    public JPanel tabPanel(JPanel tabPanel) {

        tabPanel.setLayout(new GridBagLayout());
        //Jtable into JScroll
        jTableList = new ClientJTable();
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

    protected static void displayModele(Client client) {
        int id = client.getId();
        //String telephone = Client.getTelephone();

        labelId.setText("Id : " + id);
        //labelTelephone.setText("N°téléphone : " + telephone);
    }


}
