package View;

import Controller.HomeController;
import Controller.UserConnectionController;
import Controller.VoitureController;
import Dao.UserConnection;
import Dao.UserConnectionImpl;
import Model.SessionManager;
import Model.Voiture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends JFrame {

    private JButton btnLogin, btnCreateAccount, btnInitDB;
    private JTextField tfLocation, tfPickUpDate, tfDropOffDate;
    private JButton btnSearch;
    private JButton btnClientForm;

    private JComboBox<String> typeField;

    public HomePage() {
        initUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            UserConnection userConnexion = new UserConnectionImpl(); // Assure-toi que cette classe est correctement implémentée.
            new HomeController(homePage, userConnexion); // Cette ligne connecte la vue et le contrôleur.
        });
    }

    public void initUI() {
        setTitle("Carece - Page d'Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel(new BorderLayout());

        JLabel appTitle = new JLabel("CARECE", SwingConstants.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 40));
        appTitle.setForeground(new Color(0, 128, 0));
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pageTitle = new JLabel("VOTRE LOCATION", SwingConstants.CENTER);
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
            if (SessionManager.isLoggedIn()) {
                this.setVisible(false); // Cache HomePage
                new UserInfo().setVisible(true); // Affiche UserInfo
            } else {
                // L'utilisateur n'est pas connecté, ouvre le dialogue de connexion
                this.setVisible(false);
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

        JPanel searchPanel = createSearchPanel();

        JPanel carPanel = createCarPanel(); // Affichage des voitures

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

    public JTextField getTfLocation() {
        return tfLocation;
    }

    public JTextField getTfPickUpDate() {
        return tfPickUpDate;
    }

    public JTextField getTfDropOffDate() {
        return tfDropOffDate;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    private void showUserConnectionDialog() {
        JDialog dialog = new JDialog();
        ConnexionUtilisateur connexionUtilisateurPanel = new ConnexionUtilisateur();
        UserConnection userConnexionDao = new UserConnectionImpl(); // Assure-toi que cette implémentation est correcte et complète.
        new UserConnectionController(this, connexionUtilisateurPanel, userConnexionDao);

        dialog.setTitle("Connexion");
        dialog.setContentPane(connexionUtilisateurPanel);
        dialog.setSize(300, 200); // Ajuste la taille selon tes besoins
        dialog.setModal(true);
        dialog.setLocationRelativeTo(this); // Centrer par rapport à HomePage
        dialog.setVisible(true);
    }

    private JPanel createCarPanel() {
        JPanel carPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 voitures par ligne, espacement de 10 pixels
        carPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge de 10 pixels autour du panel

        // Liste fictive de voitures
        List<Voiture> voitures = new ArrayList<>();

        try {
            voitures = VoitureController.getInstance().allVoitures();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des voitures
        for (Voiture voiture : voitures) {
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
                    //e.printStackTrace();
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
            carDetailsPanel.add(new JLabel("Modèle: " + voiture.getModele_id()));

            carInfoPanel.add(carDetailsPanel, BorderLayout.CENTER);

            carPanel.add(carInfoPanel);
        }

        return carPanel;
    }

    private JPanel createSearchPanel() {
        // Panel de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(0xF8F8F8)); // Couleur de fond gris clair
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        searchPanel.setMaximumSize(new Dimension(10000, 80));

        // Composants de la barre de recherche
        JTextField tfPickUpDate = new JTextField("30-01-2024", 15);
        JTextField tfDropOffDate = new JTextField("02-02-2024", 15);
        JButton btnSearch = new JButton("Rechercher");
        btnSearch = new JButton("Rechercher");
        btnSearch.addActionListener(e -> {
            init_bdd_graphique.toggle();
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

        // Création du JComboBox
        String[] carTypes = {"Citadine", "Utilitaire", "Familiale", "Berline", "SUV"};
        JComboBox<String> comboBox = new JComboBox<>(carTypes);
        comboBox.setSelectedIndex(0);  // Sélection par défaut du premier élément

        // Ajout des composants au panel
        searchPanel.add(new JLabel("Quand voulez-vous louer votre voiture ?"));
        searchPanel.add(tfPickUpDate);
        searchPanel.add(tfDropOffDate);
        searchPanel.add(btnSearch);
        searchPanel.add(comboBox);

        add(searchPanel, BorderLayout.NORTH);

        return searchPanel;
    }
}



/*
Passer la boite de dialogue qui affiche les informations de l'utilisateur en une classe à part.
Ajouter un bouton de déconnexion pour l'utilisateur connecté (dans la nouvelle classe 'UserInfo.java')
Différencier l'affichage de l'utilisateur s'il est particulier, entreprise ou employee.
 */