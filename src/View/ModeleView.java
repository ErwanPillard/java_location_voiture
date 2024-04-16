package View;

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

public class ModeleView extends JDialog {
    private JTextField nomField;
    private JComboBox<Integer> nbPlaceBox;
    private JComboBox<Integer> nbPorteBox;
    private JTextField tailleCoffreField;
    private JTextField caracteristiquesField;
    private JTextField prixJournalierField;
    private JCheckBox attelageCheckBox;
    private JProgressBar noteSatisfactionBar;
    private JComboBox<Model.BoiteVitesse>  boiteVitesseBox;
    private JComboBox<Model.Categorie>  categorieBox;

    private JButton jbSave;

    public ModeleView(){
        createForms();
        registerListeners();
    }

    private void registerListeners() {
        jbSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                String nom = nomField.getText();
                int nbPlace = (Integer) nbPlaceBox.getSelectedItem();
                int nbPorte = (Integer) nbPorteBox.getSelectedItem();
                float tailleCoffre = Integer.parseInt(tailleCoffreField.getText());
                String caracteristique = caracteristiquesField.getText();
                int prixJournalier = Integer.parseInt(prixJournalierField.getText());
                boolean attelage = attelageCheckBox.isSelected();
                int noteSatisfaction = noteSatisfactionBar.getValue();
                BoiteVitesse boiteVitesse = (BoiteVitesse) Objects.requireNonNull(boiteVitesseBox.getSelectedItem());
                Categorie categorieSelectionnee = (Categorie) Objects.requireNonNull(categorieBox.getSelectedItem());

                try {
                    Modele modele = new Modele(nom, nbPlace, nbPorte,tailleCoffre, caracteristique, prixJournalier, noteSatisfaction,categorieSelectionnee, attelage, boiteVitesse);
                    ModeleController.getInstance().addModele(modele);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public void createForms(){
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);

        Integer[]choixNbPlace={2,4,5,7,8,9};
        Integer[]choixNbPorte={2,3,5,7};
        Model.BoiteVitesse[] choixBoiteVitesse={Model.BoiteVitesse.AUTOMATIC, Model.BoiteVitesse.MANUEL};
        Model.Categorie[] choixCategorieVehicule={Categorie.BERLINE,Categorie.FAMILIALE,Categorie.SUV, Categorie.UTILITAIRE, Categorie.CITADINE};

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1)); // Une seule colonne, lignes dynamiques
        jFrame.add(mainPanel);

        jbSave = new JButton("Valider");
        jFrame.add(jbSave, BorderLayout.SOUTH);

        JPanel nomPanel = new JPanel(new BorderLayout());
        nomPanel.add(new JLabel("Nom du vehicule: "), BorderLayout.WEST);
        nomField = new JTextField(30);
        nomPanel.add(nomField, BorderLayout.EAST);
        mainPanel.add(nomPanel);

        JPanel nbPlacePanel = new JPanel(new BorderLayout());
        nbPlacePanel.add(new JLabel("Nombre de places: "), BorderLayout.WEST);
        nbPlaceBox = new JComboBox<>(choixNbPlace);
        nbPlaceBox.setSize(nbPlacePanel.getSize());
        nbPlacePanel.add(nbPlaceBox, BorderLayout.EAST);
        mainPanel.add(nbPlacePanel);

        JPanel nbPortePanel = new JPanel(new BorderLayout());
        nbPortePanel.add(new JLabel("Nombre de portes:"), BorderLayout.WEST);
        nbPorteBox = new JComboBox<>(choixNbPorte);
        nbPortePanel.add(nbPorteBox, BorderLayout.EAST);
        mainPanel.add(nbPortePanel);

        JPanel choixCategoriePanel = new JPanel(new BorderLayout());
        choixCategoriePanel.add(new JLabel("Categorie de Vehicule:"), BorderLayout.WEST);
        categorieBox = new JComboBox<>(choixCategorieVehicule);
        choixCategoriePanel.add(categorieBox, BorderLayout.EAST);
        //String categorieBoxName = categorieBox.getName();
        mainPanel.add(choixCategoriePanel);

        JPanel choixBoitePanel = new JPanel(new BorderLayout());
        choixBoitePanel.add(new JLabel("Type de Boite:"), BorderLayout.WEST);
        boiteVitesseBox = new JComboBox<>(choixBoiteVitesse);
        choixBoitePanel.add(boiteVitesseBox, BorderLayout.EAST);
        mainPanel.add(choixBoitePanel);

        JPanel tailleCoffrePanel = new JPanel(new BorderLayout());
        tailleCoffrePanel.add(new JLabel("Taille du coffre: "), BorderLayout.WEST);
        tailleCoffreField = new JTextField(30);
        tailleCoffrePanel.add(tailleCoffreField, BorderLayout.EAST);
        mainPanel.add(tailleCoffrePanel);

        JPanel caracteristiquesPanel = new JPanel(new BorderLayout());
        caracteristiquesPanel.add(new JLabel("Caractéristiques: "), BorderLayout.WEST);
        caracteristiquesField = new JTextField(30);
        caracteristiquesPanel.add(caracteristiquesField, BorderLayout.EAST);
        mainPanel.add(caracteristiquesPanel);

        JPanel prixJournalierPanel = new JPanel(new BorderLayout());
        prixJournalierPanel.add(new JLabel("Prix journalier: "), BorderLayout.WEST);
        prixJournalierField = new JTextField(30);
        prixJournalierPanel.add(prixJournalierField, BorderLayout.EAST);
        mainPanel.add(prixJournalierPanel);

        JPanel attelagePanel = new JPanel(new BorderLayout());
        attelagePanel.add(new JLabel("Attelage: "), BorderLayout.WEST);
        attelageCheckBox = new JCheckBox("oui",false);
        attelagePanel.add(attelageCheckBox, BorderLayout.EAST);
        mainPanel.add(attelagePanel); // on va faire un radio button

        JPanel noteSatisfactionPanel = new JPanel(new BorderLayout());
        noteSatisfactionPanel.add(new JLabel("Note satisfaction:"));
        noteSatisfactionBar = new JProgressBar(SwingConstants.HORIZONTAL,0,100);
        noteSatisfactionBar.setValue(0);
        noteSatisfactionBar.setSize(JPanel.WIDTH,JPanel.HEIGHT);
        noteSatisfactionBar.setString("Note de satisfaction ");
        noteSatisfactionBar.setStringPainted(true);

        MouseListener clickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int progressBarValue = (int) Math.round((double) mouseX / noteSatisfactionBar.getWidth() * noteSatisfactionBar.getMaximum());
                noteSatisfactionBar.setValue(progressBarValue);
                System.out.println(noteSatisfactionBar);
            }
        };
        noteSatisfactionBar.addMouseListener(clickListener);// sur la get bar
        //noteSatisfactionPanel.addMouseListener(clickListener);// Associe l'écouteur de clics au panel
        noteSatisfactionPanel.add(noteSatisfactionBar,BorderLayout.CENTER);
        mainPanel.add(noteSatisfactionPanel);


        // Bien faire le setSize à la fin

        jFrame.setSize(400, 400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void toggle(){// modeleView.setVisible(!modeleView.isVisible());
        new ModeleView();
    }

}
