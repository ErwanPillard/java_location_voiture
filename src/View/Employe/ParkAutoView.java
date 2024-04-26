package View.Employe;

import Controller.VoitureController;
import Model.BoiteVitesse;
import Model.Categorie;
import Model.Modele;
import Model.Voiture;
import View.layouts.Options;
import utils.PlaceholderTextField;
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

    public ParkAutoView(JPanel jpBody){
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

    protected static void displayImageVoiture(String immat) {
        // Récupérer l'image de la voiture
        byte[] image = Voiture.getImageByImmatriculation(immat);

        if (image != null) { // Vérifier si l'image existe
            ImageIcon carImage = null;
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(image);
                Image img = ImageIO.read(bais);
                carImage = new ImageIcon(img.getScaledInstance(300, 150, Image.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageLabel != null) { // Vérifier si imageLabel est initialisé
                // Afficher l'image dans un JLabel
                imageLabel.setIcon(carImage); // Définir l'icône avec l'image
                imageLabel.setText(null); // Effacer le texte si une image est chargée
            } else {
                System.out.println("imageLabel is null"); // Déboguer si imageLabel est null
            }
        } else {
            // Afficher un message si aucune image n'est disponible
            if (imageLabel != null) { // Vérifier si imageLabel est initialisé
                imageLabel.setIcon(null); // Effacer l'icône si aucune image n'est disponible
                imageLabel.setText("No images"); // Définir le texte si aucune image n'est disponible
            } else {
                System.out.println("imageLabel is null"); // Déboguer si imageLabel est null
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



    public void rightPanel(JPanel mainPanel) {
        // Créez un nouveau JPanel pour contenir les composants de droite
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(16, 1)); // 2 lignes, 1 colonne

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

        // Création du champ de recherche avec une icône de loupe
        searchField = new PlaceholderTextField("Search");
        searchField.setBorder(BorderFactory.createCompoundBorder(
                searchField.getBorder(),
                BorderFactory.createEmptyBorder(0, 5, 0, 5))); // Ajout d'un petit espacement à gauche et à droite du champ de recherche

        rightPanel.add(searchField);
        rightPanel.add(filterCategorieComboBox);
        rightPanel.add(filterBoiteVitesseComboBox);

        // Ajoutez les JLabels au JPanel rightPanel

        rightPanel.add(labelId);
        rightPanel.add(labelMarque);
        rightPanel.add(labelNom);
        rightPanel.add(labelNbPlaces);
        rightPanel.add(labelNbPortes);
        rightPanel.add(labelTailleCoffre);
        rightPanel.add(labelCaracteristiques);
        rightPanel.add(labelPrixJournalier);
        rightPanel.add(labelNoteSatisfaction);
        rightPanel.add(labelCategorie);
        rightPanel.add(labelAttelage);
        rightPanel.add(labelBoiteVitesse);

        // Ajoutez le JPanel rightPanel à droite dans le JPanel principal avec des contraintes de position
        GridBagConstraints gbcRightPanel = new GridBagConstraints();
        gbcRightPanel.gridx = 1; // Colonne 1
        gbcRightPanel.gridy = 1; // Ligne 0
        gbcRightPanel.weightx = 0.1; // Poids horizontal (pour réduire la largeur)
        gbcRightPanel.weighty = 0.4; // Poids vertical (pour occuper l'espace disponible)
        gbcRightPanel.fill = GridBagConstraints.BOTH; // Remplissage dans les deux sens
        mainPanel.add(rightPanel, gbcRightPanel);
    }

    public void topRightPanel(JPanel mainPanel) {
        // Créez un nouveau JPanel pour contenir les composants de droite
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new GridBagLayout()); // Utilisation du GridBagLayout

        // Définissez les marges internes du GridBagLayout sur 0
        ((GridBagLayout)topRightPanel.getLayout()).setConstraints(imageLabel, new GridBagConstraints() {
            {
                gridx = 0; // Colonne 0
                gridy = 0; // Ligne 0
                weightx = 1.0; // Poids horizontal (pour occuper l'espace disponible)
                weighty = 1.0; // Poids vertical (pour occuper l'espace disponible)
                fill = GridBagConstraints.BOTH; // Remplissage dans les deux sens
                insets = new Insets(0, 0, 0, 0); // Marges internes (haut, gauche, bas, droite)
            }
        });

        topRightPanel.add(imageLabel);
        topRightPanel.add(editButton);

        // Ajoutez le JPanel rightPanel à droite dans le JPanel principal avec des contraintes de position
        GridBagConstraints gbcRightPanel = new GridBagConstraints();
        gbcRightPanel.gridx = 1; // Colonne 1
        gbcRightPanel.gridy = 0; // Ligne 0
        gbcRightPanel.weightx = 0.1; // Poids horizontal (pour réduire la largeur)
        gbcRightPanel.weighty = 0.4; // Poids vertical (pour occuper l'espace disponible)
        gbcRightPanel.fill = GridBagConstraints.BOTH; // Remplissage dans les deux sens
        mainPanel.add(topRightPanel, gbcRightPanel);
    }


    public void leftPanel(JPanel mainPanel) {
        // Créez un nouveau JPanel pour contenir les composants de gauche
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1, 1)); // 1 ligne, 1 colonne

        JScrollPane jspList = new JScrollPane();
        jTableList = new VoitureJTable();
        jspList.setViewportView(jTableList);

        leftPanel.add(jspList);

        // Ajoutez le tableau à gauche dans le JPanel principal avec des contraintes de position
        GridBagConstraints gbcLeftPanel = new GridBagConstraints();
        gbcLeftPanel.gridx = 0; // Colonne 0
        gbcLeftPanel.gridy = 0; // Ligne 0
        gbcLeftPanel.gridheight = 2; // Occupe 2 lignes
        gbcLeftPanel.weightx = 0.6; // Poids horizontal (pour occuper l'espace disponible)
        gbcLeftPanel.weighty = 1.0; // Poids vertical (pour occuper l'espace disponible)
        gbcLeftPanel.fill = GridBagConstraints.BOTH; // Remplissage dans les deux sens
        mainPanel.add(leftPanel, gbcLeftPanel);
    }




    public void createView(JPanel jpBody) {
        // Créez une instance de JLabel pour l'image
        imageLabel = new JLabel();
        editButton = new JButton();

        // Créez un JPanel pour contenir le tableau et le JPanel rightPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); // Utilisation du GridBagLayout

        leftPanel(mainPanel);

        // Ajoutez le tableau à gauche dans le JPanel principal avec des contraintes de position
        GridBagConstraints gbcTable = new GridBagConstraints();
        gbcTable.gridx = 0; // Colonne 0
        gbcTable.gridy = 0; // Ligne 0
        gbcTable.gridwidth = 2; // Occupe 2 colonnes
        gbcTable.weightx = 1.0; // Poids horizontal (pour occuper l'espace disponible)
        gbcTable.weighty = 1.0; // Poids vertical (pour occuper l'espace disponible)
        gbcTable.fill = GridBagConstraints.BOTH; // Remplissage dans les deux sens

        topRightPanel(mainPanel);
        rightPanel(mainPanel);


        // Ajoutez le JPanel principal à jpBody
        jpBody.removeAll(); // Supprimer tous les composants existants de jpBody
        jpBody.add(new Options(jTableList), BorderLayout.SOUTH); // Options en bas
        jpBody.add(mainPanel, BorderLayout.CENTER); // Tableau et composants à droite
        jpBody.revalidate(); // Actualiser l'affichage
    }


}
