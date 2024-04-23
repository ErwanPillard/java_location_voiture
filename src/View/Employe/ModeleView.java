package View.Employe;

import Controller.ModeleController;

import Model.BoiteVitesse;
import Model.Categorie;
import Model.Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

import static utils.Util.addFormField;

public class ModeleView extends JDialog {
    private static final ModeleView instance = new ModeleView();
    public static ModeleView getInstance() {
        return instance;
    }


    private JTextField marqueField;
    private JTextField nomField;
    private JComboBox<Integer> nbPlaceBox;
    private JComboBox<Integer> nbPorteBox;
    private JTextField tailleCoffreField;
    private JTextField caracteristiquesField;
    private JTextField prixJournalierField;
    private JCheckBox attelageCheckBox;
    private JComboBox<Model.BoiteVitesse>  boiteVitesseBox;
    private JComboBox<Model.Categorie>  categorieBox;

    private JButton jbSave;
    private JButton jbCancel;

    public ModeleView(){
        createForms();
        createButtons();
        registerListeners();
        configure();
    }

    public void createForms(){
        JPanel jpForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.anchor = GridBagConstraints.WEST;
        gbcForm.insets = new Insets(5, 5, 5, 5);

        addFormField(jpForm, gbcForm, "Marque",marqueField = new JTextField(20));
        addFormField(jpForm, gbcForm, "Nom du véhicule",nomField = new JTextField(20));


        Integer[]choixNbPlace={2,4,5,7,8,9};
        Integer[]choixNbPorte={2,3,5,7};
        Model.BoiteVitesse[] choixBoiteVitesse={BoiteVitesse.AUTOMATIQUE, Model.BoiteVitesse.MANUEL};
        Model.Categorie[] choixCategorieVehicule={Categorie.BERLINE,Categorie.FAMILIALE,Categorie.SUV, Categorie.UTILITAIRE, Categorie.CITADINE};

        JComboBox<Integer> comboBox1 = new JComboBox<>(choixNbPlace);
        addFormField(jpForm, gbcForm, "Nombre de places :", nbPlaceBox = comboBox1);

        JComboBox<Integer> comboBox2 = new JComboBox<>(choixNbPorte);
        addFormField(jpForm, gbcForm, "Nombre de portes :", nbPorteBox = comboBox2);

        JComboBox<Categorie> comboBox3 = new JComboBox<>(choixCategorieVehicule);
        addFormField(jpForm, gbcForm, "Catégorie :", categorieBox = comboBox3);

        JComboBox<BoiteVitesse> comboBox4 = new JComboBox<>(choixBoiteVitesse);
        addFormField(jpForm, gbcForm, "Boite de vitesse :", boiteVitesseBox = comboBox4);

        addFormField(jpForm, gbcForm, "Taille du coffre",tailleCoffreField = new JTextField(20));

        addFormField(jpForm, gbcForm, "Caractéristiques",caracteristiquesField = new JTextField(20));

        addFormField(jpForm, gbcForm, "Prix journalier",prixJournalierField = new JTextField(20));

        addFormField(jpForm, gbcForm, "Attelage",attelageCheckBox = new JCheckBox("oui",false));

        this.add(jpForm, BorderLayout.CENTER);
    }

    public void createButtons(){
        JPanel jpButtons = new JPanel();

        jpButtons.add(jbSave = new JButton("Sauvegarder"));
        jpButtons.add(jbCancel = new JButton("Annuler"));

        this.add(jpButtons, BorderLayout.SOUTH);
    }

    private void registerListeners() {
        jbSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdSave();
            }
        });

        jbCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdCancel();
            }
        });
    }

    private void configure() {
        setTitle("Création de modèle");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(this.getRootPane());
    }

    private void cmdSave(){
        String marque = marqueField.getText();
        String nom = nomField.getText();
        int nbPlace = (Integer) nbPlaceBox.getSelectedItem();
        int nbPorte = (Integer) nbPorteBox.getSelectedItem();
        float tailleCoffre = Integer.parseInt(tailleCoffreField.getText());
        String caracteristique = caracteristiquesField.getText();
        int prixJournalier = Integer.parseInt(prixJournalierField.getText());
        boolean attelage = attelageCheckBox.isSelected();
        float noteSatisfaction = 0;
        BoiteVitesse boiteVitesse = (BoiteVitesse) Objects.requireNonNull(boiteVitesseBox.getSelectedItem());
        Categorie categorieSelectionnee = (Categorie) Objects.requireNonNull(categorieBox.getSelectedItem());

        try {
            Modele modele = new Modele(marque, nom, nbPlace, nbPorte,tailleCoffre, caracteristique, prixJournalier, noteSatisfaction,categorieSelectionnee, attelage, boiteVitesse);
            ModeleController.getInstance().addModele(modele);
            dispose();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void cmdCancel() {
        dispose();
    }

    public static void toggle(){instance.setVisible(!instance.isVisible());}

}
