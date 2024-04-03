package View;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class HomePage extends JFrame {
    public HomePage() {
        super("Carece - Page d'Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Conteneur principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel pour le titre et la barre de recherche
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        // Panel pour le titre et le bouton de connexion
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBackground(Color.WHITE); // Fond blanc pour le panel du titre

        // Titre de l'application
        JLabel appTitle = new JLabel("CARECE", SwingConstants.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 40)); // Taille de police ajustée
        appTitle.setForeground(new Color(0, 128, 0)); // Couleur du texte en vert

        // Bouton de connexion
        JButton btnLogin = new JButton();
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setOpaque(false);
        try {
            // Chargement de l'image avec ImageIO pour une meilleure gestion des erreurs
            Image img = ImageIO.read(getClass().getResource("/Pictures/AccountPicture.png"));
            btnLogin.setIcon(new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH))); // Redimensionne l'image
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'erreur dans la console si l'image ne peut pas être chargée
            JOptionPane.showMessageDialog(this, "L'image ne peut pas être chargée : " + e.getMessage());
        }

        // Ajoute le titre et le bouton au panel du titre
        titlePanel.add(appTitle);
        titlePanel.add(btnLogin);

        // Panel pour le bouton de connexion
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginPanel.setOpaque(false);
        loginPanel.add(btnLogin);

        // Ajoute le titre et le panel de connexion au northPanel
        northPanel.add(appTitle, BorderLayout.CENTER);
        northPanel.add(loginPanel, BorderLayout.EAST);

        // Barre de recherche directement sous le titre
        JPanel searchPanel = createSearchPanel();
        northPanel.add(searchPanel, BorderLayout.SOUTH);

        // Ajoute le northPanel au mainPanel
        mainPanel.add(northPanel, BorderLayout.NORTH);

        // Contenu du panel de défilement pour les offres
        JPanel scrollableContentPanel = createScrollableContentPanel();

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(scrollableContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Ajoute le ScrollPane au mainPanel
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Configure le content pane
        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel createSearchPanel() {
        // Panel de recherche
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(0xF8F8F8)); // Couleur de fond gris clair
        //searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alignement à gauche
        searchPanel.setBackground(Color.WHITE); // Assure que le fond est blanc

        // Composants de la barre de recherche
        JTextField tfLocation = new JTextField("Lyon, Métropole de Lyon, France", 20);
        JTextField tfPickUpDate = new JTextField("sam. 6/4", 10);
        JTextField tfDropOffDate = new JTextField("sam. 13/4", 10);
        JButton btnSearch = new JButton("Rechercher");

        // Personnalisation des composants
        tfLocation.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        tfPickUpDate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        tfDropOffDate.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        btnSearch.setBackground(new Color(0xFF5733)); // Couleur rouge/orange
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setOpaque(true);

        // Ajout des composants au panel
        searchPanel.add(new JLabel("Où allez-vous ?"));
        searchPanel.add(tfLocation);
        searchPanel.add(tfPickUpDate);
        searchPanel.add(tfDropOffDate);
        searchPanel.add(btnSearch);

        add(searchPanel, BorderLayout.NORTH);

        return searchPanel;
    }

    private JPanel createScrollableContentPanel() {
        // Contenu du panel de défilement
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Ajoute un scroll pane pour le défilement
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0xF8F8F8)); // Couleur de fond gris clair
        contentPanel.add(headerPanel);

        // Section pour les offres, les prix, etc.
        JPanel offersPanel = new JPanel();
        offersPanel.setLayout(new GridLayout(2, 2, 10, 20)); // 2 lignes, 2 colonnes, espacement
        offersPanel.setBackground(Color.WHITE);
        offersPanel.add(createOfferPanel("Les meilleures offres de voitures", "Consultez les offres d’agences de location dans plus de 70 000 emplacements."));
        offersPanel.add(createOfferPanel("Transparence des prix", "Visualisez le prix total d'emblée pour éviter les surprises."));
        offersPanel.add(createOfferPanel("Réservation flexible", "Trouvez une voiture avec annulation gratuite et nettoyage amélioré."));
        offersPanel.add(createOfferPanel("Options écologiques", "Filtrez votre recherche pour trouver facilement une voiture hybride ou électrique."));
        contentPanel.add(offersPanel);

        return contentPanel;
    }

    private JPanel createOfferPanel(String title, String description) {
        // Création d'un panel pour une offre
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel(title));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("<html><p style='width:200px'>" + description + "</p></html>"));

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}

