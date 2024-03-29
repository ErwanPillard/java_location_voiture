package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import BDD.init_bdd;

public class MaFenetre extends JFrame {
    private JButton btnNettoyer;
    private JButton btnAjouterUsers;
    private JButton btnAjouterEmployes;
    private JButton btnAjouterClients;

    private Connection connection;

    public MaFenetre() {
        super("Test BDD avec GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        btnNettoyer = new JButton("Nettoyer la BDD");
        btnAjouterUsers = new JButton("Ajouter Utilisateurs");
        btnAjouterEmployes = new JButton("Ajouter Employés");
        btnAjouterClients = new JButton("Ajouter Clients");

        add(btnNettoyer);
        add(btnAjouterUsers);
        add(btnAjouterEmployes);
        add(btnAjouterClients);

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MaFenetre().setVisible(true));
    }
}
