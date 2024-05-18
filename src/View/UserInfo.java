package View;

import Controller.UserInfoController;
import Model.SessionManager;
import View.Employe.ModeleView;
import View.Employe.VoitureFormView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Model.SessionManager.updateTelephoneInDatabase;

public class UserInfo extends JFrame {
    private final JButton btnLogout;
    private final JButton btnRetour;
    private final JButton btnFacture;
    private final JButton btnReservations;
    private final JButton btnInterfaceEmploye;
    private final JButton btnAjouterVoiture;
    private final JButton btnAjouterModele;
    private final JButton btnSupprimerVoiture;
    private final JButton btnSupprimerModele;
    private final JButton btnConfirmerReservation;

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
                afficherReservationsDialog(SessionManager.getCurrentUser().getId());
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
                ModeleView.toggle();
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