package View;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private JButton btnLogin;
    private JTextField tfLocation, tfPickUpDate, tfDropOffDate;
    private JButton btnSearch;

    public HomePage() {
        initUI();
    }

    private void initUI() {
        // Log pour le début de l'initialisation de l'interface
        System.out.println("Initialisation de l'interface...");

        setTitle("Carece - Page d'Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Haut de la page : Titre et bouton de connexion
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel appTitle = new JLabel("CARECE", SwingConstants.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 40));
        appTitle.setForeground(new Color(0, 128, 0));

        btnLogin = new JButton(new ImageIcon(getClass().getResource("/Pictures/AccountPicture.png")));
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setOpaque(false);
        if (btnLogin.getIcon() == null) {
            System.out.println("L'image du bouton de connexion n'a pas pu être chargée.");
        }

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(appTitle);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginPanel.add(btnLogin);

        northPanel.add(titlePanel, BorderLayout.CENTER);
        northPanel.add(loginPanel, BorderLayout.EAST);

        // Ajoute le northPanel au mainPanel
        mainPanel.add(northPanel, BorderLayout.NORTH);
        // Vérifie si les composants sont correctement ajoutés
        if (northPanel.isDisplayable()) {
            System.out.println("northPanel est prêt à l'affichage.");
        }

        // Centre de la page : Contenu principal
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Configuration finale de la fenêtre
        setContentPane(mainPanel);
        pack(); // Ajuste la taille de la fenêtre aux composants
        setVisible(true);

        // Log pour confirmer la visibilité
        System.out.println("L'interface utilisateur est maintenant visible.");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}
