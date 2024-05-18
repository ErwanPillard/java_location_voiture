package View;

import BDD.init_bdd;
import Dao.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class init_bdd_graphique extends JFrame {

    static init_bdd_graphique init_bdd_graphique;

    static {
        try {
            init_bdd_graphique = new init_bdd_graphique();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final Connection connection;
    private final JButton btnNettoyer;
    private final JButton btnAjouterUsers;
    private final JButton btnAjouterEmployes;
    private final JButton btnAjouterClients;
    private final JButton btnAjouterParticulier;
    private final JButton btnAjouterEntreprise;
    private final JButton btnAjouterModele;
    private final JButton btnAjouterReservation;
    private final JButton btnAjouterFacture;

    private final JButton btnAjouterCitadine;
    private final JButton btnAjouterFamiliale;
    private final JButton btnAjouterUtilitaire;
    private final JButton btnAjouterBerline;
    private final JButton btnAjouterSUV;

    /*public init_bdd_graphique() {
        createButtons();
        configure();
    }*/

    public init_bdd_graphique() throws SQLException {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        connection = DatabaseManager.getConnection();

        btnNettoyer = new JButton("Nettoyer la BDD");
        btnAjouterUsers = new JButton("1. Ajouter Utilisateurs");
        btnAjouterEmployes = new JButton("2. Ajouter Employés");
        btnAjouterClients = new JButton("3. Ajouter Clients");
        btnAjouterParticulier = new JButton("4. Ajouter Particuliers");
        btnAjouterEntreprise = new JButton("5. Ajouter Entreprise");
        btnAjouterModele = new JButton("6. Ajouter Modele");
        btnAjouterReservation = new JButton("8. Ajouter des Reservation");
        btnAjouterFacture = new JButton("9. Ajouter des Factures");

        btnAjouterCitadine = new JButton("9. Ajouter des Citadines");
        btnAjouterFamiliale = new JButton("9. Ajouter des Familiale");
        btnAjouterUtilitaire = new JButton("9. Ajouter des Utilitaire");
        btnAjouterBerline = new JButton("9. Ajouter des Berline");
        btnAjouterSUV = new JButton("9. Ajouter des SUV");

        add(btnNettoyer);
        add(btnAjouterUsers);
        add(btnAjouterEmployes);
        add(btnAjouterClients);
        add(btnAjouterParticulier);
        add(btnAjouterEntreprise);
        add(btnAjouterModele);
        add(btnAjouterReservation);
        add(btnAjouterFacture);

        add(btnAjouterCitadine);
        add(btnAjouterFamiliale);
        add(btnAjouterUtilitaire);
        add(btnAjouterBerline);
        add(btnAjouterSUV);

        setupButtonActions();
    }

    /*private void createButtons() {
        JPanel jpButtons = new JPanel();

        jpButtons.add(btnNettoyer = new JButton("Nettoyer la BDD"));
        jpButtons.add(btnAjouterUsers = new JButton("1. Ajouter Utilisateurs"));
        jpButtons.add(btnAjouterEmployes = new JButton("2. Ajouter Employés"));
        jpButtons.add(btnAjouterClients = new JButton("3. Ajouter Clients"));
        jpButtons.add(btnAjouterParticulier = new JButton("4. Ajouter Particuliers"));
        jpButtons.add(btnAjouterEntreprise = new JButton("5. Ajouter Entreprise"));
        jpButtons.add(btnAjouterModele = new JButton("6. Ajouter Modele"));
        jpButtons.add(btnAjouterVoiture = new JButton("7. Ajouter Voiture"));

        this.add(jpButtons, BorderLayout.SOUTH);
    }

    private void configure() {
        setTitle("Init BDD graphique");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(this.getRootPane());
    }*/

    public static void toggle() {
        init_bdd_graphique.setVisible(!init_bdd_graphique.isVisible());
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new init_bdd_graphique().setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }*/

    private void setupButtonActions() {
        btnNettoyer.addActionListener(e -> {
            try {
                init_bdd.clearData(connection);
                JOptionPane.showMessageDialog(this, "Base de données nettoyée", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors du nettoyage de la base", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterUsers.addActionListener(e -> {
            try {
                init_bdd.insertUsers(connection);
                JOptionPane.showMessageDialog(this, "Utilisateurs ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des utilisateurs", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterEmployes.addActionListener(e -> {
            try {
                init_bdd.insertEmployes(connection);
                JOptionPane.showMessageDialog(this, "Employés ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des employés", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterClients.addActionListener(e -> {
            try {
                init_bdd.insertClient(connection);
                JOptionPane.showMessageDialog(this, "Clients ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des clients", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterParticulier.addActionListener(e -> {
            try {
                init_bdd.insertParticuliers(connection);
                JOptionPane.showMessageDialog(this, "Particulier ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des particulier", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterEntreprise.addActionListener(e -> {
            try {
                init_bdd.insertEntreprises(connection);
                JOptionPane.showMessageDialog(this, "Entreprise ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des Entreprise", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterModele.addActionListener(e -> {
            try {
                init_bdd.insertModeles(connection);
                JOptionPane.showMessageDialog(this, "Modele ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des modeles", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterReservation.addActionListener(e -> {
            try {
                init_bdd.insertReservation(connection);
                JOptionPane.showMessageDialog(this, "Reservations ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des reservations", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterFacture.addActionListener(e -> {
            try {
                init_bdd.insertFacture(connection);
                JOptionPane.showMessageDialog(this, "Factures ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des factures", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });


        btnAjouterCitadine.addActionListener(e -> {
            try {
                init_bdd.insertCitadine(connection);
                JOptionPane.showMessageDialog(this, "Citadines ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des citadines", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterFamiliale.addActionListener(e -> {
            try {
                init_bdd.insertFamiliale(connection);
                JOptionPane.showMessageDialog(this, "Familiales ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des familiales", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterUtilitaire.addActionListener(e -> {
            try {
                init_bdd.insertUtilitaire(connection);
                JOptionPane.showMessageDialog(this, "Utilitaires ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des utilitaires", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterBerline.addActionListener(e -> {
            try {
                init_bdd.insertBerline(connection);
                JOptionPane.showMessageDialog(this, "Berlines ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des berlines", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAjouterSUV.addActionListener(e -> {
            try {
                init_bdd.insertSUV(connection);
                JOptionPane.showMessageDialog(this, "SUV ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des SUV", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
