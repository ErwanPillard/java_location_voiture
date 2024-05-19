package View;

import Controller.PaymentPageController;
import Controller.ReservationController;
import Controller.UserInfoController;
import Dao.DatabaseManager;
import Model.SessionManager;
import Model.OffreReduction;
import Controller.OffreReductionController;
import View.Employe.ModeleView;
import View.Employe.VoitureFormView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.List;

import static Model.SessionManager.updateTelephoneInDatabase;

public class UserInfo extends JFrame {
    private final JButton btnLogout;
    private final JButton btnRetour;

    private final JButton btnFacture;
    private final JButton btnReservations;
    private final JButton btnOffres;

    private final JButton btnInterfaceEmploye;
    private final JButton btnAjouterVoiture;
    private final JButton btnAjouterModele;
    private final JButton btnSupprimerVoiture;
    private final JButton btnSupprimerModele;
    private final JButton btnConfirmerReservation;
    private final JButton btnInitDB;

    private final JButton btnModifMdp;
    private final JButton btnModifTel;
    //private final JButton btn;

    public UserInfo() {
        setTitle("Informations de l'utilisateur");
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(700, 500);
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
        btnLogout.setBackground(new Color(0xFF0000)); // Couleur ROUGE
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14)); // Police en gras
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setOpaque(true);

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
                dispose();
                UserInfoController userInfoController = new UserInfoController(UserInfo.this);
                userInfoController.showUserInvoices();
            }
        });
        btnReservations = new JButton("Afficher toutes mes réservations");
        btnReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                afficherReservationsDialog(SessionManager.getCurrentUser().getId());

                // Revenir à la page userInfo
                UserInfo userInfo = new UserInfo();
                userInfo.setVisible(true);
            }
        });
        btnOffres = new JButton("Souscrire à une offre");
        btnOffres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                afficherOffreDialog();

                // Revenir à la page userInfo
                UserInfo userInfo = new UserInfo();
                userInfo.setVisible(true);
            }
        });
        btnInterfaceEmploye = new JButton("Afficher l'interface employée");
        btnInterfaceEmploye.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInterfaceEmploye.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View.MainJFrame.employeInterface();
            }
        });
        btnInterfaceEmploye.setBackground(new Color(0x377e21)); // Couleur Verte
        btnInterfaceEmploye.setFont(new Font("Arial", Font.BOLD, 20)); // Police en gras
        btnInterfaceEmploye.setForeground(Color.WHITE);
        btnInterfaceEmploye.setFocusPainted(false);
        btnInterfaceEmploye.setBorderPainted(false);
        btnInterfaceEmploye.setOpaque(true);

        btnAjouterVoiture = new JButton("Ajouter une voiture");
        btnAjouterVoiture.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAjouterVoiture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VoitureFormView.toggle();
            }
        });
        btnAjouterModele = new JButton("Ajouter un modèle de voiture");
        btnAjouterModele.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAjouterModele.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModeleView.toggle();
            }
        });
        btnSupprimerVoiture = new JButton("Supprimer une voiture");
        btnSupprimerVoiture.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSupprimerVoiture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VoitureFormView.toggle();
            }
        });
        btnSupprimerModele = new JButton("Supprimer un modèle de voiture");
        btnSupprimerModele.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSupprimerModele.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModeleView.toggle();
            }
        });
        btnConfirmerReservation = new JButton("Confirmer une réservation");
        btnConfirmerReservation.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmerReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmeReservationDialog();
            }
        });
        btnModifMdp = new JButton("Modifier le mot de passe");
        btnModifMdp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePasswordDialog();
            }
        });
        btnModifTel = new JButton("Modifier le numéro de téléphone");
        btnModifTel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTelephoneDialog();
            }
        });
        // Panneau pour init bdd graphique
        btnInitDB = new JButton("Init BDD Graphique");
        btnInitDB.addActionListener(e -> {
            init_bdd_graphique.toggle();
        });
        btnInitDB.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel cardPanel = new JPanel(new CardLayout());

        // Création des onglets
        JTabbedPane tabbedPane = new JTabbedPane();

        SessionManager.getInstance();

        // Onglet des informations personnelles
        JPanel personalInfoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        tabbedPane.addTab("Informations Personnelles", personalInfoPanel);
        JPanel invoicesPanel = null;
        JPanel employeePanel = null;
        if ((SessionManager.userType().equals("Particulier")) || SessionManager.userType().equals("Entreprise")) {
            // Onglet des factures pour les particuliers et les entreprises
            invoicesPanel = new JPanel(new GridBagLayout());
            tabbedPane.addTab("Factures", invoicesPanel);
        } else {
            // Onglet des employés
            employeePanel = new JPanel(new GridBagLayout());
            tabbedPane.addTab("Interface employée", employeePanel);
        }

        JLabel labelUserType = new JLabel("Type : " + SessionManager.userType()); // Affiche le type d'utilisateur
        JLabel labelEmail = new JLabel("Email : " + SessionManager.getCurrentUser().getEmail());

        personalInfoPanel.add(labelUserType);
        personalInfoPanel.add(labelEmail);
        personalInfoPanel.add(btnModifMdp);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE; // Placer chaque bouton dans une nouvelle ligne

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

            invoicesPanel.add(btnFacture, gbc);
            invoicesPanel.add(btnReservations, gbc);
            invoicesPanel.add(btnOffres, gbc);
        } else if (SessionManager.userType().equals("Entreprise")) {
            JLabel labelNomEntreprise = new JLabel("Nom de l'entreprise : " + SessionManager.getCurrentEntreprise().getNom());
            JLabel labelNumSiretEntreprise = new JLabel("Numéro siret : " + SessionManager.getCurrentEntreprise().getNumSiret());
            JLabel labelNumEntreprise = new JLabel("Numéro de téléphone : " + SessionManager.getCurrentClient().getTelephone());
            personalInfoPanel.add(btnModifTel);

            personalInfoPanel.add(labelNomEntreprise);
            personalInfoPanel.add(labelNumSiretEntreprise);
            personalInfoPanel.add(labelNumEntreprise);

            invoicesPanel.add(btnFacture, gbc);
            invoicesPanel.add(btnReservations, gbc);
            invoicesPanel.add(btnOffres, gbc);
        } else if (SessionManager.userType().equals("Employe")) {
            JLabel labelNomEmploye = new JLabel("Nom : " + SessionManager.getCurrentEmploye().getNom());
            JLabel labelPrenomEmploye = new JLabel("Prénom : " + SessionManager.getCurrentEmploye().getPrenom());
            JLabel labelFonctionEmploye = new JLabel("Fonction : " + SessionManager.getCurrentEmploye().getFonction());

            personalInfoPanel.add(labelNomEmploye);
            personalInfoPanel.add(labelPrenomEmploye);
            personalInfoPanel.add(labelFonctionEmploye);

            employeePanel.add(btnInterfaceEmploye, gbc);
            employeePanel.add(btnAjouterVoiture, gbc);
            employeePanel.add(btnAjouterModele, gbc);
            employeePanel.add(btnSupprimerVoiture, gbc);
            employeePanel.add(btnSupprimerModele, gbc);
            employeePanel.add(btnConfirmerReservation, gbc);
            employeePanel.add(btnInitDB, gbc);
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
                ouvrirHomePage();
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    public AbstractButton getBtnFacture() {
        return btnFacture;
    }

    private void changePasswordDialog() {
        // Création d'une boîte de dialogue pour la saisie du mot de passe
        JDialog dialog = new JDialog(this, "Changer le mot de passe", true);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        // Champ pour l'ancien mot de passe
        JLabel labelOldPassword = new JLabel("Ancien mot de passe :");
        JPasswordField oldPasswordField = new JPasswordField();
        dialog.add(labelOldPassword);
        dialog.add(oldPasswordField);

        // Champ pour le nouveau mot de passe
        JLabel labelNewPassword = new JLabel("Nouveau mot de passe :");
        JPasswordField newPasswordField = new JPasswordField();
        dialog.add(labelNewPassword);
        dialog.add(newPasswordField);

        // Champ pour la confirmation du nouveau mot de passe
        JLabel labelConfirmPassword = new JLabel("Confirmer nouveau mot de passe :");
        JPasswordField confirmPasswordField = new JPasswordField();
        dialog.add(labelConfirmPassword);
        dialog.add(confirmPasswordField);

        // Bouton pour valider la modification
        JButton btnChangePassword = new JButton("Changer");
        btnChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(dialog, "Les mots de passe ne correspondent pas !");
                    return;
                }

                if (SessionManager.changePassword(oldPassword, newPassword)) {
                    JOptionPane.showMessageDialog(dialog, "Mot de passe modifié avec succès !");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Erreur : l'ancien mot de passe est incorrect !");
                }
            }
        });
        dialog.add(new JLabel()); // Placeholder pour alignement
        dialog.add(btnChangePassword);

        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void changeTelephoneDialog() {
        // Création d'une boîte de dialogue pour la saisie du mot de passe
        JDialog dialog = new JDialog(this, "Changer le telephone", true);
        dialog.setLayout(new GridLayout(2, 2, 10, 10));

        // Champ pour le nouveau telephone
        JLabel labelNewTelephone = new JLabel("Nouveau telephone :");
        JTextField newTelephoneField = new JTextField();
        dialog.add(labelNewTelephone);
        dialog.add(newTelephoneField);

        JButton btnChangeTelephone = new JButton("Changer");
        btnChangeTelephone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newTelephone = newTelephoneField.getText();

                if (updateTelephoneInDatabase(SessionManager.getCurrentUser().getId(), newTelephone)) {
                    JOptionPane.showMessageDialog(dialog, "Telephone modifié avec succès !");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Erreur dans la modification du telephone !");
                    dialog.dispose();
                }
            }
        });
        dialog.add(new JLabel()); // Placeholder pour alignement
        dialog.add(btnChangeTelephone);

        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void afficherReservationsDialog(int clientId) {
        // Création d'une boîte de dialogue pour afficher les réservations
        JDialog dialog = new JDialog(this, "Liste des réservations", true);
        dialog.setLayout(new BorderLayout());

        // Récupération des données de réservation depuis la base de données
        Object[][] data = SessionManager.fetchReservationsData(clientId);
        String[] columnNames = {"numReservation", "dateDebutReservation", "dateFinReservation", "montant", "etat", "voiture_immatriculation"};

        // Création du tableau pour afficher les données
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Bouton de fermeture
        JButton btnClose = new JButton("Fermer");
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(btnClose, BorderLayout.SOUTH);

        // Configuration et affichage de la boîte de dialogue
        dialog.setSize(900, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static void enregistrerAchat(int clientId, int offreId) throws SQLException {
        String sql = "INSERT INTO ClientOffreAchat (id_client, id_offre) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, offreId);
            stmt.executeUpdate();
        }
    }

    private void afficherOffreDialog() {
        JDialog dialog = new JDialog(this, "Offres de Réduction", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1200, 600);  // Augmente la taille de la fenêtre
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Ajoute des marges autour des composants
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        try {
            List<OffreReduction> offres = OffreReductionController.getInstance().allOffresReduction();
            for (OffreReduction offre : offres) {
                JPanel offrePanel = new JPanel(new GridBagLayout());
                GridBagConstraints innerGbc = new GridBagConstraints();
                innerGbc.insets = new Insets(5, 5, 5, 5);
                innerGbc.fill = GridBagConstraints.HORIZONTAL;
                innerGbc.gridx = 0;
                innerGbc.gridy = 0;

                offrePanel.add(new JLabel("Nom: " + offre.getNom()), innerGbc);
                innerGbc.gridx++;
                offrePanel.add(new JLabel("Description: " + offre.getDescription()), innerGbc);
                innerGbc.gridx++;
                offrePanel.add(new JLabel("Pourcentage de Réduction: " + offre.getPourcentageReduction() + "%"), innerGbc);
                innerGbc.gridx++;
                offrePanel.add(new JLabel("Date de début: " + offre.getDateDebut()), innerGbc);
                innerGbc.gridx++;
                offrePanel.add(new JLabel("Date de fin: " + offre.getDateFin()), innerGbc);

                innerGbc.gridx++;
                JButton acheterButton = new JButton("Acheter pour " + (offre.getPourcentageReduction() * 10) + " €");
                acheterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int clientId = SessionManager.getCurrentClient().getId();  // Assumes there is a method to get the client's ID
                        int offreId = offre.getId();  // Assumes there is a method to get the offer's ID
                        System.out.println("Client ID: " + clientId + ", Offre ID: " + offreId);  // Debugging output

                        try {
                            enregistrerAchat(clientId, offreId);
                            dialog.dispose();
                            // Rediriger vers la page de paiement
                            PaymentPage paymentPage = new PaymentPage(null);
                            paymentPage.setVisible(true);

                            // Vérifier si le paiement a été effectué avec succès
                            if (paymentPage.isPaymentSuccessful()) {
                                JOptionPane.showMessageDialog(dialog, "Achat offre de réduction confirmée !");
                                PaymentPageController.ajouterFactureOffre((offre.getPourcentageReduction() * 10));

                                // Fermer la fenêtre actuelle de réservation
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(dialog, "Le paiement a échoué.");
                            }
                        } catch (SQLException ex) {
                            System.err.println("Erreur lors de l'enregistrement de l'achat: " + ex.getMessage());
                            JOptionPane.showMessageDialog(dialog, "Erreur lors de l'achat de l'offre.");
                        }
                    }
                });
                offrePanel.add(acheterButton, innerGbc);

                panel.add(offrePanel, gbc);
                gbc.gridy++;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des offres de réduction: " + e.getMessage());
        }

        dialog.add(new JScrollPane(panel), BorderLayout.CENTER);

        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JPanel southPanel = new JPanel();
        southPanel.add(closeButton);
        dialog.add(southPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    public static void confirmeReservationDialog() {
        String query = "SELECT * FROM Reservation WHERE etat = 'non-confirmée'";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Assumer que tu as des colonnes comme id, dateDebutReservation, etc.
                Object[] row = new Object[]{
                        rs.getInt("numReservation"),
                        rs.getDate("dateDebutReservation"),
                        rs.getDate("dateFinReservation"),
                        rs.getFloat("montant"),
                        rs.getString("etat"),
                        // Ajouter d'autres colonnes selon la structure de ta table
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des réservations", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifParcDialog() {
        // Création d'une boîte de dialogue pour la saisie du mot de passe
        JDialog dialog = new JDialog(this, "Changer le telephone", true);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        // Champ pour le nouveau telephone
        JLabel labelNewTelephone = new JLabel("Nouveau telephone :");
        JTextField newTelephoneField = new JTextField();
        dialog.add(labelNewTelephone);
        dialog.add(newTelephoneField);

        JButton btnChangeTelephone = new JButton("Changer");
        btnChangeTelephone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newTelephone = newTelephoneField.getText();

                if (updateTelephoneInDatabase(SessionManager.getCurrentUser().getId(), newTelephone)) {
                    JOptionPane.showMessageDialog(dialog, "Telephone modifié avec succès !");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Erreur dans la modification du telephone !");
                    dialog.dispose();
                }
            }
        });
        dialog.add(new JLabel()); // Placeholder pour alignement
        dialog.add(btnChangeTelephone);

        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}