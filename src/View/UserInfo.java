package View;

import Model.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInfo extends JFrame {
    private final JButton btnLogout;
    private final JButton btnRetour;
    JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JButton btnFactures;
    private JButton btnMettreAJourVoitures;
    private JButton btnOffresReduction;
    private JButton btnStatistiques;

    public UserInfo() {
        setTitle("Informations de l'utilisateur");
        setSize(500, 400); // Ajuste selon les besoins
        setLocationRelativeTo(null); // Centre la fenêtre
        getContentPane().setBackground(new Color(240, 240, 240)); // Couleur de fond

        // Définit le layout principal
        setLayout(new BorderLayout(10, 10));

        // Informations de l'utilisateur
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(0, 1, 10, 10)); // Ajuste l'espacement
        panelInfo.setBackground(new Color(240, 240, 240)); // Assorti au fond

        SessionManager.getInstance();
        String userType = SessionManager.userType();
        JLabel labelUserType = new JLabel("Type : " + userType);
        JLabel labelNom = new JLabel("Nom : " + SessionManager.getCurrentUser().getNom());
        JLabel labelPrenom = new JLabel("Prénom : " + SessionManager.getCurrentUser().getPrenom());
        JLabel labelEmail = new JLabel("Email : " + SessionManager.getCurrentUser().getEmail());

        // Ajoute les informations spécifiques selon le type d'utilisateur
        panelInfo.add(labelUserType);
        panelInfo.add(labelNom);
        panelInfo.add(labelPrenom);
        panelInfo.add(labelEmail);

        // Conditions pour afficher les informations spécifiques et boutons selon le type
        switch (userType) {
            case "Particulier":
                JLabel labelNumeroPermis = new JLabel("Numéro de permis : " + SessionManager.getCurrentUser().getNumPermis());
                JLabel labelDateNaissance = new JLabel("Date de naissance : " + SessionManager.getCurrentUser().getDateNaissance());
                panelInfo.add(labelNumeroPermis);
                panelInfo.add(labelDateNaissance);

                btnFactures = new JButton("Accéder aux factures");
                break;
            case "Entreprise":
                JLabel labelNumeroSiret = new JLabel("Numéro SIRET : " + SessionManager.getCurrentUser().getNumSiret());
                panelInfo.add(labelNumeroSiret);

                btnFactures = new JButton("Accéder aux factures");
                break;
            case "Employe":
                JLabel labelFonction = new JLabel("Fonction : " + SessionManager.getCurrentUser().getFonctionEmploye());
                panelInfo.add(labelFonction);

                btnMettreAJourVoitures = new JButton("Mettre à jour les voitures");
                btnOffresReduction = new JButton("Introduire diverses offres de réduction");
                btnStatistiques = new JButton("Statistiques");
                break;
        }

        add(panelInfo, BorderLayout.CENTER);

        // Panel Sud pour les boutons d'action
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setBackground(new Color(240, 240, 240)); // Assorti au fond

        btnLogout = new JButton("Déconnexion");
        btnLogout.addActionListener(e -> {
            SessionManager.getInstance().logOut();
            dispose();
            new HomePage().setVisible(true);
        });

        btnRetour = new JButton("Retour à la page principale");
        btnRetour.addActionListener(e -> {
            dispose();
            new HomePage().setVisible(true);
        });

        // Ajoute les boutons spécifiques selon le type d'utilisateur
        if (userType.equals("Particulier") || userType.equals("Entreprise")) {
            southPanel.add(btnFactures);
            // Placeholder pour la fonctionnalité du bouton
            btnFactures.addActionListener(e -> {/* Accéder aux factures */});
        } else if (userType.equals("Employe")) {
            southPanel.add(btnMettreAJourVoitures);
            southPanel.add(btnOffresReduction);
            southPanel.add(btnStatistiques);
            // Placeholders pour les fonctionnalités des boutons
            btnMettreAJourVoitures.addActionListener(e -> {/* Mettre à jour les voitures */});
            btnOffresReduction.addActionListener(e -> {/* Introduire offres de réduction */});
            btnStatistiques.addActionListener(e -> {/* Afficher statistiques */});
        }

        // Ajoute les boutons de base
        southPanel.add(btnRetour);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Ajoute un espace vertical de 5 pixels entre les boutons
        southPanel.add(btnLogout);

        add(panelInfo, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Listener pour la fermeture de la fenêtre
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Ceci est appelé lorsque l'utilisateur clique sur la croix pour fermer la fenêtre
                ouvrirHomePage();
            }
        });
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    private void ouvrirHomePage() {
        // Cette méthode rend la HomePage visible à nouveau
        EventQueue.invokeLater(() -> {
            new HomePage().setVisible(true);
        });
    }
}