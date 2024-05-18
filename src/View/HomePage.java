package View;

import Controller.HomeController;
import Controller.UserConnectionController;
import Controller.VoitureController;
import Dao.DatabaseManager;
import Dao.UserConnectionImpl;
import Model.SessionManager;
import Model.Voiture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePage extends JFrame {
    private JButton btnLogin, btnCreateAccount, btnInitDB;
    JPanel searchPanel;
    JPanel carPanel; // Affichage des voitures

    JTextField tfPickUpDate = new JTextField("20-01-2024", 15);
    JTextField tfDropOffDate = new JTextField("29-01-2024", 15);
    String model = "Citadine";

    public HomePage() {
        searchPanel = createSearchPanel();
        carPanel = createCarPanel();
        initUI();
    }

    public void initUI() {
        setTitle("Carece - Page d'Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel(new BorderLayout());

        JLabel appTitle = new JLabel(new ImageIcon(getClass().getResource("/Pictures/Logo.png")));
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pageTitle = new JLabel("Votre location de voiture", SwingConstants.CENTER);
        pageTitle.setFont(new Font("Arial", Font.BOLD, 30));
        pageTitle.setForeground(new Color(0, 128, 0));
        pageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        northPanel.add(Box.createVerticalGlue());
        northPanel.add(appTitle);
        northPanel.add(pageTitle);
        northPanel.add(Box.createVerticalGlue());

        btnLogin = new JButton(new ImageIcon(getClass().getResource("/Pictures/AccountPicture.png")));
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setOpaque(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(e -> {
            SessionManager.getInstance();
            this.setVisible(false); // Cache HomePage
            if (SessionManager.isLoggedIn()) {
                /*if (SessionManager.userType().equals("Employe")) {
                    MainJFrame.employeInterface(); // Affiche l'interface employé
                } */
                new UserInfo().setVisible(true); // Affiche UserInfo
            } else {
                // L'utilisateur n'est pas connecté, ouvre le dialogue de connexion
                UserConnectionController controller = new UserConnectionController(this, new ConnexionUtilisateur(), new UserConnectionImpl());
                controller.showLoginDialog(this); // 'this' réfère à la JFrame HomePage
                new HomePage().setVisible(true); // Rouvre HomePage
            }
        });

        // Panneau pour crée compte
        btnCreateAccount = new JButton("Créer Compte");
        btnCreateAccount.addActionListener(e -> {
            ClientFormView.toggle();
        });
        btnCreateAccount.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCreateAccount.setBackground(new Color(0x377e21)); // Couleur Verte
        btnCreateAccount.setFont(new Font("Arial", Font.BOLD, 16)); // Police en gras
        btnCreateAccount.setForeground(Color.WHITE);
        btnCreateAccount.setFocusPainted(false);
        btnCreateAccount.setBorderPainted(false);
        btnCreateAccount.setOpaque(true);

        // Panneau pour init bdd graphique
        btnInitDB = new JButton("Init BDD Graphique");
        btnInitDB.addActionListener(e -> {
            init_bdd_graphique.toggle();
        });
        btnInitDB.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));  // Configuration pour aligner verticalement
        titlePanel.add(appTitle);
        titlePanel.add(pageTitle);

        // Créer un espace invisible à gauche qui équilibre les éléments à droite
        Box.Filler leftFiller = new Box.Filler(new Dimension(170, 50), new Dimension(170, 50), new Dimension(170, 50));
        northPanel.add(leftFiller, BorderLayout.WEST);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));  // Configuration pour aligner verticalement
        loginPanel.add(btnLogin);
        loginPanel.add(btnCreateAccount);
        loginPanel.add(btnInitDB);

        searchPanel = createSearchPanel();

        northPanel.add(titlePanel, BorderLayout.CENTER);
        northPanel.add(loginPanel, BorderLayout.EAST);

        // Ajouter le JScrollPane
        JScrollPane scrollPane = new JScrollPane(carPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(800, 600)); // Tu peux ajuster la taille selon tes besoins

        // Le JPanel retourné devrait être le JScrollPane pour intégrer le défilement
        JPanel panelWithScroll = new JPanel(new BorderLayout());
        panelWithScroll.add(scrollPane, BorderLayout.CENTER);

        // Panneau central pour contenir le titre et la barre de recherche
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(searchPanel, BorderLayout.CENTER);
        centerPanel.add(panelWithScroll, BorderLayout.SOUTH);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        // Configuration finale de la fenêtre
        getContentPane().add(mainPanel);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    // Getters pour les contrôleurs pour accéder aux composants
    public JButton getBtnLogin() {
        return btnLogin;
    }

    private JPanel createCarPanel() {
        JPanel carPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 voitures par ligne, espacement de 10 pixels
        carPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge de 10 pixels autour du panel

        // Récupérer les dates sélectionnées par l'utilisateur
        LocalDate startDate = LocalDate.parse(tfPickUpDate.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = LocalDate.parse(tfDropOffDate.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));


        // Liste fictive de voitures
        List<Voiture> voitures = new ArrayList<>();

        try {
            voitures = VoitureController.getInstance().allVoitures();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des voitures
        for (Voiture voiture : voitures) {
            boolean isAvailable = HomeController.isCarAvailable(voiture.getImmatriculation(), startDate, endDate);
            if (!isAvailable) {
                continue;
            }
            JPanel carInfoPanel = new JPanel(new BorderLayout());
            carInfoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            byte[] image = Voiture.getImageByImmatriculation(voiture.getImmatriculation());

            if (image != null) {
                // Chargement de l'image de la voiture
                ImageIcon carImage = null;
                try {
                    ByteArrayInputStream bais = new ByteArrayInputStream(image);
                    Image img = ImageIO.read(bais);
                    if (img == null) {
                        throw new IOException("L'image n'a pas pu être lue, img est null.");
                    }
                    carImage = new ImageIcon(img.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                } catch (IOException e) {
                    e.printStackTrace();
                    // Afficher un message d'erreur pour l'utilisateur
                    JLabel errorLabel = new JLabel("Erreur de chargement de l'image");
                    carInfoPanel.add(errorLabel, BorderLayout.NORTH);
                }
                JLabel carImageLabel = new JLabel(carImage);
                carInfoPanel.add(carImageLabel, BorderLayout.NORTH);
            } else {
                // Afficher un message d'erreur si l'image est null
                JLabel errorLabel = new JLabel("Image non disponible");
                carInfoPanel.add(errorLabel, BorderLayout.NORTH);
            }

            JPanel carDetailsPanel = new JPanel(new GridLayout(0, 1));
            carDetailsPanel.add(new JLabel("Immatriculation: " + voiture.getImmatriculation()));
            carDetailsPanel.add(new JLabel("Date de mise en circulation: " + voiture.getDateMiseCirculation()));
            carDetailsPanel.add(new JLabel("Kilométrage: " + voiture.getNbKilometre()));
            carDetailsPanel.add(new JLabel("Couleur: " + voiture.getCouleur()));
            if (voiture.getModele_id() == 1) {
                carDetailsPanel.add(new JLabel("Modèle: " + "citadine"));
            } else if (voiture.getModele_id() == 2) {
                carDetailsPanel.add(new JLabel("Modèle: " + "familiale"));
            } else if (voiture.getModele_id() == 3) {
                carDetailsPanel.add(new JLabel("Modèle: " + "utilitaire"));
            } else if (voiture.getModele_id() == 4) {
                carDetailsPanel.add(new JLabel("Modèle: " + "berline"));
            } else if (voiture.getModele_id() == 5) {
                carDetailsPanel.add(new JLabel("Modèle: " + "suv"));
            }
            carDetailsPanel.add(new JLabel("Prix journalier: " + getPrixJournalier(voiture.getModele_id())));

            JPanel reservation = new JPanel(new BorderLayout());
            if (SessionManager.isLoggedIn() && !Objects.equals(SessionManager.userType(), "Employe")) {
                JButton btnReserver = new JButton("Réserver");
                btnReserver.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String dateDebut = tfPickUpDate.getText();
                        String dateFin = tfDropOffDate.getText();
                        Reservation.toggle(dateDebut, dateFin, voiture);
                    }
                });
                btnReserver.setBackground(new Color(0x377e21)); // Couleur Verte
                btnReserver.setFont(new Font("Arial", Font.BOLD, 14)); // Police en gras
                btnReserver.setForeground(Color.WHITE);
                btnReserver.setFocusPainted(false);
                btnReserver.setBorderPainted(false);
                btnReserver.setOpaque(true);

                // Créer un panneau pour contenir le bouton avec des marges
                JPanel buttonPanel = new JPanel(new BorderLayout());
                buttonPanel.add(btnReserver, BorderLayout.CENTER);
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Ajoute de l'espace autour du bouton

                reservation.add(buttonPanel, BorderLayout.SOUTH);
            }

            carInfoPanel.add(reservation, BorderLayout.EAST);
            carInfoPanel.add(carDetailsPanel, BorderLayout.CENTER);

            carPanel.add(carInfoPanel);
        }
        return carPanel;
    }

    private void updateCarPanel(List<Voiture> voitures, String model) {
        carPanel.removeAll();  // Supprime tous les composants précédents de carPanel

        // Récupérer les dates sélectionnées par l'utilisateur
        LocalDate startDate = LocalDate.parse(tfPickUpDate.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = LocalDate.parse(tfDropOffDate.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Réinitialise le layout et les bordures si nécessaire
        carPanel.setLayout(new GridLayout(0, 3, 10, 10));
        carPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Voiture voiture : voitures) {
            // Vérifier la disponibilité de la voiture
            boolean isAvailable = HomeController.isCarAvailable(voiture.getImmatriculation(), startDate, endDate);
            if (!isAvailable) {
                continue;  // Si la voiture n'est pas disponible, passer à la suivante
            }

            JPanel carInfoPanel = new JPanel(new BorderLayout());
            carInfoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            byte[] image = Voiture.getImageByImmatriculation(voiture.getImmatriculation());
            if (image != null) {
                // Chargement de l'image de la voiture
                ImageIcon carImage = null;
                try {
                    ByteArrayInputStream bais = new ByteArrayInputStream(image);
                    Image img = ImageIO.read(bais);
                    carImage = new ImageIcon(img.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
                } catch (IOException e) {
                    // Handle error
                }

                JLabel carImageLabel = new JLabel(carImage);
                carInfoPanel.add(carImageLabel, BorderLayout.NORTH);
            }

            // Ajout des autres informations de la voiture
            JPanel carDetailsPanel = new JPanel(new GridLayout(0, 1));
            carDetailsPanel.add(new JLabel("Immatriculation: " + voiture.getImmatriculation()));
            carDetailsPanel.add(new JLabel("Date de mise en circulation: " + voiture.getDateMiseCirculation()));
            carDetailsPanel.add(new JLabel("Kilométrage: " + voiture.getNbKilometre()));
            carDetailsPanel.add(new JLabel("Couleur: " + voiture.getCouleur()));
            if (voiture.getModele_id() == 1) {
                carDetailsPanel.add(new JLabel("Modèle: " + "citadine"));
            } else if (voiture.getModele_id() == 2) {
                carDetailsPanel.add(new JLabel("Modèle: " + "familiale"));
            } else if (voiture.getModele_id() == 3) {
                carDetailsPanel.add(new JLabel("Modèle: " + "utilitaire"));
            } else if (voiture.getModele_id() == 4) {
                carDetailsPanel.add(new JLabel("Modèle: " + "berline"));
            } else if (voiture.getModele_id() == 5) {
                carDetailsPanel.add(new JLabel("Modèle: " + "suv"));
            }
            carDetailsPanel.add(new JLabel("Prix journalier: " + getPrixJournalier(voiture.getModele_id())));

            JPanel reservation = new JPanel(new BorderLayout());
            if (SessionManager.isLoggedIn() && !Objects.equals(SessionManager.userType(), "Employe")) {
                JButton btnReserver = new JButton("Réserver");
                btnReserver.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String dateDebut = tfPickUpDate.getText();
                        String dateFin = tfDropOffDate.getText();
                        Reservation.toggle(dateDebut, dateFin, voiture);
                    }
                });
                btnReserver.setBackground(new Color(0x377e21)); // Couleur Verte
                btnReserver.setFont(new Font("Arial", Font.BOLD, 14)); // Police en gras
                btnReserver.setForeground(Color.WHITE);
                btnReserver.setFocusPainted(false);
                btnReserver.setBorderPainted(false);
                btnReserver.setOpaque(true);

                // Créer un panneau pour contenir le bouton avec des marges
                JPanel buttonPanel = new JPanel(new BorderLayout());
                buttonPanel.add(btnReserver, BorderLayout.CENTER);
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Ajoute de l'espace autour du bouton

                reservation.add(buttonPanel, BorderLayout.SOUTH);
            }

            carInfoPanel.add(reservation, BorderLayout.EAST);
            carInfoPanel.add(carDetailsPanel, BorderLayout.CENTER);

            carPanel.add(carInfoPanel);
        }

        carPanel.revalidate();
        carPanel.repaint();  // Redessine le panel avec les nouvelles voitures
    }

    private JPanel createSearchPanel() {
        // Panel de recherche
        final JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(0xF8F8F8)); // Couleur de fond gris clair
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        searchPanel.setMaximumSize(new Dimension(10000, 80));

        // Création du JComboBox
        String[] carTypes = {"Modèle", "Citadine", "Familiale", "Utilitaire", "Berline", "SUV"};
        JComboBox<String> comboBox = new JComboBox<>(carTypes);
        comboBox.setSelectedIndex(0);  // Sélection par défaut du premier élément

        // Composants de la barre de recherche
        tfPickUpDate = new JTextField("20-01-2024", 15);
        tfDropOffDate = new JTextField("29-01-2024", 15);
        JButton btnSearch = new JButton("Rechercher");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = (String) comboBox.getSelectedItem();
                try {
                    List<Voiture> filteredVoiture = VoitureController.getInstance().findVoituresByModel(model);
                    updateCarPanel(filteredVoiture, model);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des voitures : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Personnalisation des composants
        tfPickUpDate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        tfDropOffDate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        btnSearch.setBackground(new Color(0xFF5733)); // Couleur rouge/orange
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 16)); // Police en gras
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setOpaque(true);

        // Ajout des composants au panel
        searchPanel.add(new JLabel("Quand voulez-vous louer votre voiture ? Quel modèle ?"));
        searchPanel.add(tfPickUpDate);
        searchPanel.add(tfDropOffDate);
        searchPanel.add(comboBox);
        searchPanel.add(btnSearch);

        add(searchPanel, BorderLayout.NORTH);

        return searchPanel;
    }

    private int getPrixJournalier(int modeleId) {
        // Initialisation de la valeur par défaut pour le prix journalier
        int prixJournalier = -1;

        // Obtention de la connexion à la base de données
        try (Connection conn = DatabaseManager.getConnection()) {
            // Préparation de la requête SQL pour récupérer le prix journalier
            String sql = "SELECT prixJournalier FROM Modele WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, modeleId);  // Définir l'ID du modèle dans la requête

                // Exécution de la requête
                try (ResultSet rs = stmt.executeQuery()) {
                    // Vérification si un résultat est retourné
                    if (rs.next()) {
                        prixJournalier = rs.getInt("prixJournalier");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du prix journalier: " + e.getMessage());
        }

        return prixJournalier;
    }

    private static byte[] readImageAsBytes(String imagePath) throws IOException {
        File file = new File(imagePath);
        if (!file.exists()) {
            throw new IOException("Le fichier image n'existe pas : " + imagePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }

}