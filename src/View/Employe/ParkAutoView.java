package View.Employe;

import Controller.VoitureController;
import Model.BoiteVitesse;
import Model.Categorie;
import Model.Modele;
import Model.Voiture;
import View.MainJFrame;
import View.layouts.Options;
import utils.PlaceholderTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParkAutoView{

    private JComboBox<String> filterCategorieComboBox;
    private JComboBox<String> filterBoiteVitesseComboBox;
    private PlaceholderTextField searchField;
    private static JLabel imageLabel;
    private static JButton editButton;

    private VoitureJTable jTableList;


    // Créez des JLabels pour afficher les détails du modèle dans le panneau droit
    private static final JLabel labelId = new JLabel("Id : ");
    private static final JLabel labelMarque = new JLabel("Marque : ");
    private static final JLabel labelNom = new JLabel("Nom : ");
    private static final JLabel labelNbPlaces = new JLabel("Nombre de places : ");
    private static final JLabel labelNbPortes = new JLabel("Nombre de portes : ");
    private static final JLabel labelTailleCoffre = new JLabel("Taille du coffre : ");
    private static final JLabel labelCaracteristiques = new JLabel("Caractéristiques : ");
    private static final JLabel labelPrixJournalier = new JLabel("Prix journalier : ");
    private static final JLabel labelNoteSatisfaction = new JLabel("Note de satisfaction : ");
    private static final JLabel labelCategorie = new JLabel("Catégorie : ");
    private static final JLabel labelAttelage = new JLabel("Attelage : ");
    private static final JLabel labelBoiteVitesse = new JLabel("Boite de vitesse : ");

    public ParkAutoView(JPanel jpBody) {
        createView(jpBody);
        registerListeners();
    }

    private void setFondEcran(JPanel panel, String cheminImage) {
        try {
            // Charger l'image du fond d'écran
            BufferedImage img = ImageIO.read(new File(cheminImage));
            // Créer une ImageIcon à partir de l'image
            ImageIcon imageIcon = new ImageIcon(img);
            // Créer un JLabel avec l'image de fond
            JLabel backgroundLabel = new JLabel(imageIcon);
            // Définir la taille du JLabel pour qu'elle corresponde à la taille de la JPanel
            backgroundLabel.setBounds(0, 0, panel.getWidth(), panel.getHeight());
            // Ajouter le JLabel au JPanel
            panel.add(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void registerListeners() {
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

    protected static void displayImageVoiture(String immat) {
        // Récupérer l'image de la voiture
        byte[] image = Voiture.getImageByImmatriculation(immat);

        if (image != null) { // Vérifier si l'image existe
            ImageIcon carImage = null;
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(image);
                Image img = ImageIO.read(bais);
                carImage = new ImageIcon(img.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageLabel != null) { // Vérifier si imageLabel est initialisé
                // Afficher l'image dans un JLabel
                imageLabel.setIcon(carImage); // Définir l'icône avec l'image
                imageLabel.setText(null); // Effacer le texte si une image est chargée
            } else {
                //System.out.println("imageLabel is null"); // Déboguer si imageLabel est null
            }
        } else {
            // Afficher un message si aucune image n'est disponible
            if (imageLabel != null) { // Vérifier si imageLabel est initialisé
                imageLabel.setIcon(null); // Effacer l'icône si aucune image n'est disponible
                imageLabel.setText("No images"); // Définir le texte si aucune image n'est disponible
            } else {
                //System.out.println("imageLabel is null"); // Déboguer si imageLabel est null
            }
        }

        editButton.setText("Change Image");

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File selectedFile = VoitureFormView.getInstance().selectImage();
                if (selectedFile != null) {
                    try {
                        Voiture.addImage(immat, selectedFile);
                        //displayImageVoiture(immat); // Réouvrir la frame avec la nouvelle image
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }


    protected static void displayModele(Modele modele) {
        int id = modele.getId();
        String marque = modele.getMarque();
        String nom = modele.getNom();
        int nbPlaces = modele.getNbPlace();
        int nbPortes = modele.getNbPorte();
        float tailleCoffre = modele.getTailleCoffre();
        String caracteristiques = modele.getCaracteristiques();
        int prixJournalier = modele.getPrixJournalier();
        float noteSatisfaction = modele.getNoteSatisfaction();
        String categorie = modele.getCategorie().getNomCategorie();
        boolean attelage = modele.isAttelage();
        String boiteVitesse = modele.getBoiteVitesse().getTypeBoite().toUpperCase();

        labelId.setText("Id : " + id);
        labelMarque.setText("Marque : " + marque);
        labelNom.setText("Nom : " + nom);
        labelNbPlaces.setText("Nombre de places : " + nbPlaces);
        labelNbPortes.setText("Nombre de portes : " + nbPortes);
        labelTailleCoffre.setText("Taille du coffre : " + tailleCoffre);
        labelCaracteristiques.setText("Caractéristiques : " + caracteristiques);
        labelPrixJournalier.setText("Prix journalier : " + prixJournalier);
        labelNoteSatisfaction.setText("Note de satisfaction : " + noteSatisfaction);
        labelCategorie.setText("Catégorie : " + categorie);
        labelAttelage.setText("Attelage : " + (attelage ? "Oui" : "Non"));
        labelBoiteVitesse.setText("Boite de vitesse : " + boiteVitesse);
    }


    public JPanel tabPanel(JPanel tabPanel) {

        tabPanel.setLayout(new GridBagLayout());
        //Jtable into JScroll
        jTableList = new VoitureJTable();
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

    public JPanel filterPanel(JPanel filterPanel) {

        filterPanel.setLayout(new GridBagLayout());
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

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = GridBagConstraints.REMAINDER; //Occupe toute sa ligne
        gbc.gridheight = 1; // valeur par défaut. 1 seul cellule par column

        gbc.weightx = 1.; //espace supplémentaire alloué aux autres composants
        gbc.weighty = 1.;

        gbc.fill = GridBagConstraints.BOTH; //occupe tout l'espace dans les 2 sens

        gbc.anchor = GridBagConstraints.LINE_START; // aligné au debut du panel

        gbc.insets = new Insets(10, 10, 10, 10);

        filterPanel.add(labelTriezParCategorie, gbc);
        gbc.gridy++;
        filterPanel.add(filterCategorieComboBox, gbc);
        gbc.gridy++;
        filterPanel.add(labelTriezParBoiteVitesse, gbc);
        gbc.gridy++;
        filterPanel.add(filterBoiteVitesseComboBox, gbc);

        return filterPanel;
    }

    public JPanel categoriePanel(JPanel categoriePanel) {

        categoriePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = GridBagConstraints.REMAINDER; // Occupe toute sa ligne
        gbc.gridheight = 1; // valeur par défaut. 1 seul cellule par column

        gbc.weightx = 2.; // espace supplémentaire alloué aux autres composants
        gbc.weighty = 1.;

        gbc.fill = GridBagConstraints.BOTH; // occupe tout l'espace dans les 2 sens

        gbc.anchor = GridBagConstraints.LINE_START; // aligné au debut du panel

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Modèle");
        title.setFont(new Font("Arial", Font.BOLD, 20)); // Police Arial en gras

        categoriePanel.add(title, gbc);
        gbc.gridy++;
        categoriePanel.add(labelMarque, gbc);
        gbc.gridy++;
        categoriePanel.add(labelNom, gbc);
        gbc.gridy++;
        categoriePanel.add(labelNbPlaces, gbc);
        gbc.gridy++;
        categoriePanel.add(labelNbPortes, gbc);
        gbc.gridy++;
        categoriePanel.add(labelTailleCoffre, gbc);
        gbc.gridy++;
        categoriePanel.add(labelCaracteristiques, gbc);
        gbc.gridy++;
        categoriePanel.add(labelPrixJournalier, gbc);
        gbc.gridy++;
        categoriePanel.add(labelNoteSatisfaction, gbc);
        gbc.gridy++;
        categoriePanel.add(labelCategorie, gbc);
        gbc.gridy++;
        categoriePanel.add(labelAttelage, gbc);
        gbc.gridy++;
        categoriePanel.add(labelBoiteVitesse, gbc);

        return categoriePanel;
    }


    public void createView(JPanel jpBody) {

        editButton = new JButton();
        imageLabel = new JLabel();


        //Main
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel tabPanel = new JPanel(new GridBagLayout()); //Panel Tableau voiture
        JPanel filterPanel = new JPanel(new GridBagLayout());
        JPanel categoriePanel = new JPanel(new GridBagLayout());

        Color backgroundColor = new Color(55, 95, 158);
        mainPanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 2;
        filterPanel.setBackground(Color.white);
        filterPanel.setPreferredSize(new Dimension(300, 200));
        mainPanel.add(filterPanel(filterPanel), gbc);

        gbc.gridy = 1;
        categoriePanel.setBackground(Color.white);
        categoriePanel.setPreferredSize(new Dimension(300, 400));
        mainPanel.add(categoriePanel(categoriePanel), gbc);


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
}