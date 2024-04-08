package View;

import Controller.HomeController;
import Controller.UserConnectionController;

import Dao.UserConnexion;
import Dao.UserConnexionImpl;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private JButton btnLogin;
    private JTextField tfLocation, tfPickUpDate, tfDropOffDate;
    private JButton btnSearch;
    private JLabel loginStatusLabel;

    public HomePage() {
        initUI();
    }

    private void initUI() {
        setTitle("Carece - Page d'Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Haut de la page : Titre et bouton de connexion
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel appTitle = new JLabel("CARECE", SwingConstants.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 40));
        appTitle.setForeground(new Color(0, 128, 0));

        loginStatusLabel = new JLabel("Non connecté");
        loginStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        btnLogin = new JButton(new ImageIcon(getClass().getResource("/Pictures/AccountPicture.png")));
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setOpaque(false);

        btnLogin.addActionListener(e -> showUserConnectionDialog());


        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(appTitle);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginPanel.add(btnLogin);

        northPanel.add(titlePanel, BorderLayout.CENTER);
        northPanel.add(loginPanel, BorderLayout.EAST);

        // Ajoute le northPanel au mainPanel
        mainPanel.add(northPanel, BorderLayout.NORTH);

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

    public void setUserLoggedIn(boolean isLoggedIn) {
        loginStatusLabel.setText(isLoggedIn ? "Connecté" : "Non connecté");
        // Tu pourrais aussi changer la couleur ou d'autres propriétés visuelles ici
    }

    private void showUserConnectionDialog() {
        JDialog dialog = new JDialog();
        UserConnection userConnectionPanel = new UserConnection();
        UserConnexion userConnexionDao = new UserConnexionImpl(); // Assure-toi que cette implémentation est correcte et complète.
        new UserConnectionController(userConnectionPanel, userConnexionDao);

        dialog.setTitle("Connexion");
        dialog.setContentPane(userConnectionPanel);
        dialog.setSize(300, 200); // Ajuste la taille selon tes besoins
        dialog.setModal(true);
        dialog.setLocationRelativeTo(this); // Centrer par rapport à HomePage
        dialog.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            UserConnexion userConnexion = new UserConnexionImpl(); // Assure-toi que cette classe est correctement implémentée.
            new HomeController(homePage, userConnexion); // Cette ligne connecte la vue et le contrôleur.
        });
    }
}
