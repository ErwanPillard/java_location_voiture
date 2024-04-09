package View;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import BDD.init_bdd;

public class init_bdd_graphique extends JFrame {
    private final JButton btnNettoyer;
    private final JButton btnAjouterUsers;
    private final JButton btnAjouterEmployes;
    private final JButton btnAjouterClients;
    private final JButton btnAjouterParticulier;
    private final JButton btnAjouterEntreprise;
    private final JButton btnAjouterVoiture;
    private final JButton btnAjouterModele;

    private Connection connection;

    public init_bdd_graphique() {
        super("Test BDD avec GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        btnNettoyer = new JButton("Nettoyer la BDD (optionnel)");
        btnAjouterUsers = new JButton("1. Ajouter Utilisateurs");
        btnAjouterEmployes = new JButton("2. Ajouter Employés");
        btnAjouterClients = new JButton("3. Ajouter Clients");
        btnAjouterParticulier = new JButton("4. Ajouter Particuliers");
        btnAjouterEntreprise = new JButton("5. Ajouter Entreprise");
        btnAjouterModele = new JButton("6. Ajouter Modele");
        btnAjouterVoiture = new JButton("7. Ajouter Voiture");

        add(btnNettoyer);
        add(btnAjouterUsers);
        add(btnAjouterEmployes);
        add(btnAjouterClients);
        add(btnAjouterParticulier);
        add(btnAjouterEntreprise);
        add(btnAjouterModele);
        add(btnAjouterVoiture);

        try {
            setupDatabaseConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        setupButtonActions();
    }

    private void setupDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(init_bdd.DATABASE_URL, init_bdd.DATABASE_USER, init_bdd.DATABASE_PASSWORD);
    }

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
        btnAjouterVoiture.addActionListener(e -> {
            try {
                init_bdd.insertVoitures(connection);
                JOptionPane.showMessageDialog(this, "Voiture ajoutés", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout des voitures", "Erreur", JOptionPane.ERROR_MESSAGE);
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new init_bdd_graphique().setVisible(true));
    }
}
