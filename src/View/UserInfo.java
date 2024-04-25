package View;

import Model.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInfo extends JFrame {
    private final JButton btnLogout;
    private final JButton btnRetour;
    private final JButton btnFacture;
    private final JButton btnReservations;
    private final JButton btnModifierVehicule;
    private final JButton btnAddModele;

    private final JButton btnModifEmail;
    private final JButton btnModifMdp;
    private final JButton btnModifTel;
    //private final JButton btn;

    public UserInfo() {
        setTitle("Informations de l'utilisateur");
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(500, 300);
        setLocationRelativeTo(null); // Centre la fenêtre
        setLayout(new BorderLayout());

        btnLogout = new JButton("Déconnexion");
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionManager.getInstance().logOut();
                dispose(); // Ferme la fenêtre UserInfo
                new HomePage().setVisible(true); // Rouvre HomePage
            }
        });
        btnRetour = new JButton("Retour à la page principale");
        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la fenêtre UserInfo
                new HomePage().setVisible(true); // Rouvre HomePage
            }
        });
        btnFacture = new JButton("Afficher les factures");
        btnFacture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // faire le code
            }
        });
        btnReservations = new JButton("Afficher toutes mes réservations");
        btnReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // faire le code
            }
        });
        btnModifierVehicule = new JButton("Modifier le parc de véhicule");
        btnModifierVehicule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // faire le code
            }
        });
        btnAddModele = new JButton("Ajouter un modèle de véhicule");
        btnAddModele.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // faire le code
            }
        });
        btnModifEmail = new JButton("Modifier l'email");
        btnModifEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // faire le code
            }
        });
        btnModifMdp = new JButton("Modifier le mot de passe");
        btnModifMdp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // faire le code
            }
        });
        btnModifTel = new JButton("Modifier le numéro de téléphone");
        btnModifTel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // faire le code
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel cardPanel = new JPanel(new CardLayout());

        // Création des onglets
        JTabbedPane tabbedPane = new JTabbedPane();

        // Onglet des informations personnelles
        JPanel personalInfoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        // Ajoute des éléments au personalInfoPanel... personalInfoPanel.add()

        // Onglet des factures
        JPanel invoicesPanel = new JPanel(new BorderLayout());
        // Ajoute des éléments au invoicesPanel... invoicesPanel.add()

        // Onglet des réservations
        JPanel reservationsPanel = new JPanel(new BorderLayout());
        // Ajoute des éléments au reservationsPanel... reservationsPanel.add()

        tabbedPane.addTab("Informations Personnelles", personalInfoPanel);
        tabbedPane.addTab("Factures", invoicesPanel);
        tabbedPane.addTab("Autre", reservationsPanel);

        SessionManager.getInstance();
        JLabel labelUserType = new JLabel("Type : " + SessionManager.userType()); // Affiche le type d'utilisateur
        JLabel labelEmail = new JLabel("Email : " + SessionManager.getCurrentUser().getEmail());

        personalInfoPanel.add(labelUserType);
        personalInfoPanel.add(labelEmail);
        personalInfoPanel.add(btnModifEmail);
        personalInfoPanel.add(btnModifMdp);

        if (SessionManager.userType().equals("Particulier")) {
            JLabel labelNomPart = new JLabel("Nom : " + SessionManager.getCurrentParticulier().getNom());
            JLabel labelPrenomPart = new JLabel("Prenom : " + SessionManager.getCurrentParticulier().getPrenom());
            JLabel labelPermisPart = new JLabel("Numéro de permis : " + SessionManager.getCurrentParticulier().getNumeroPermis());
            JLabel labelBirthDatePart = new JLabel("Date de naissance : " + SessionManager.getCurrentParticulier().getBirthDate());
            JLabel labelNumPart = new JLabel("Numéro de téléphone : " + SessionManager.getCurrentClient().getTelephone());
            personalInfoPanel.add(btnModifTel);

            personalInfoPanel.add(labelNomPart);
            personalInfoPanel.add(labelPrenomPart);
            personalInfoPanel.add(labelPermisPart);
            personalInfoPanel.add(labelBirthDatePart);
            personalInfoPanel.add(labelNumPart);

            invoicesPanel.add(btnFacture);
            invoicesPanel.add(btnReservations);

            btnFacture.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnReservations.setAlignmentX(Component.CENTER_ALIGNMENT);
        } else if (SessionManager.userType().equals("Entreprise")) {
            JLabel labelNomEntreprise = new JLabel("Nom de l'entreprise : " + SessionManager.getCurrentEntreprise().getNom());
            JLabel labelNumSiretEntreprise = new JLabel("Numéro siret : " + SessionManager.getCurrentEntreprise().getNumSiret());
            JLabel labelNumEntreprise = new JLabel("Numéro de téléphone : " + SessionManager.getCurrentClient().getTelephone());
            personalInfoPanel.add(btnModifTel);

            personalInfoPanel.add(labelNomEntreprise);
            personalInfoPanel.add(labelNumSiretEntreprise);
            personalInfoPanel.add(labelNumEntreprise);

            invoicesPanel.add(btnFacture);
            invoicesPanel.add(btnReservations);

            btnFacture.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnReservations.setAlignmentX(Component.CENTER_ALIGNMENT);
        } else if (SessionManager.userType().equals("Employe")) {
            JLabel labelNomEmploye = new JLabel("Nom : " + SessionManager.getCurrentEmploye().getNom());
            JLabel labelPrenomEmploye = new JLabel("Prénom : " + SessionManager.getCurrentEmploye().getPrenom());
            JLabel labelFonctionEmploye = new JLabel("Fonction : " + SessionManager.getCurrentEmploye().getFonction());

            personalInfoPanel.add(labelNomEmploye);
            personalInfoPanel.add(labelPrenomEmploye);
            personalInfoPanel.add(labelFonctionEmploye);

            invoicesPanel.add(btnModifierVehicule);
            invoicesPanel.add(btnAddModele);
            btnModifierVehicule.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnAddModele.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        mainPanel.add(tabbedPane);

        // Panneau du bas pour les boutons de déconnexion et retour
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        southPanel.add(btnRetour);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Ajoute un espace vertical de 5 pixels entre les boutons
        southPanel.add(btnLogout);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Ceci est appelé lorsque l'utilisateur clique sur la croix pour fermer la fenêtre
                ouvrirHomePage();
            }
        });
    }

    private void ouvrirHomePage() {
        // Cette méthode rend la HomePage visible à nouveau
        EventQueue.invokeLater(() -> {
            new HomePage().setVisible(true);
        });
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }
}