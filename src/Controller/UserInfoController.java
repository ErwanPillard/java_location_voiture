package Controller;

import Dao.DatabaseManager;
import Model.Facture;
import Model.SessionManager;
import View.HomePage;
import View.UserInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserInfoController {
    private final UserInfo userInfoView;

    public UserInfoController(UserInfo userInfoView) {
        this.userInfoView = userInfoView;
        initController();
    }

    private void initController() {
        // Ajoute un ActionListener au bouton de déconnexion
        userInfoView.getBtnLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        userInfoView.getBtnFacture().addActionListener(e -> showUserInvoices());
    }

    private void logout() {
        SessionManager.getInstance().logOut(); // Déconnecte l'utilisateur
        userInfoView.dispose(); // Ferme la fenêtre UserInfo

        // Rouvre la page d'accueil
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    public void showUserInvoices() {
        JFrame invoiceFrame = new JFrame("Factures Utilisateur");
        invoiceFrame.setSize(600, 400);
        invoiceFrame.setLayout(new BorderLayout());
        invoiceFrame.setLocationRelativeTo(null);

        // Récupère les factures pour l'utilisateur actuel.
        List<Facture> userInvoices = getUserInvoices(SessionManager.getCurrentUser().getId());


        String[] columnNames = {"Numéro de facture", "Date d'emission", "Montant", "Etat"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Facture invoice : userInvoices) {
            Object[] row = new Object[]{
                    invoice.getNumFacture(),
                    invoice.getDate(),
                    invoice.getDateDebutReservation(),
                    invoice.getDateFinReservation(),
                    invoice.getMontant(),
                    invoice.getEtat(),
                    invoice.getVoiture_immatriculation(),
                    invoice.getId_client()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        invoiceFrame.add(scrollPane, BorderLayout.CENTER);

        // Affiche la fenêtre des factures.
        invoiceFrame.setVisible(true);
    }

    public List<Facture> getUserInvoices(int userId) {
        List<Facture> invoices = new ArrayList<>();

        // Cette requête est juste un exemple, ajuste-la en fonction de la façon dont les factures sont liées aux utilisateurs dans ta base de données
        String sql = "SELECT f.dateDebutReservation, f.dateFinReservation, f.montant, f.etat, f.voiture_immatriculation, f.id_client " +
                "FROM Facture f " +
                "JOIN Reservation r ON f.dateDebutReservation = r.dateDebutReservation and f.dateFinReservation = r.dateFinReservation  and f.montant = r.montant  and f.etat = r.etat  and f.voiture_immatriculation = r.voiture_immatriculation  and f.id_client = r.id_client ";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int numeroFacture = rs.getInt("numeroFacture");
                    LocalDateTime dateEmission = LocalDateTime.parse("30-01-2003");
                    LocalDateTime dateDebut = LocalDateTime.parse("30-01-2003");
                    LocalDateTime dateFin = LocalDateTime.parse("30-01-2003");
                    float montant = rs.getFloat("montant");
                    String etat = rs.getString("etat");
                    String voiture_immatriculation = rs.getString("immatriculation");
                    int id_client = rs.getInt("id_client");
                    invoices.add(new Facture(numeroFacture, dateEmission, dateDebut, dateFin, montant, etat, voiture_immatriculation, id_client));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gestion des exceptions
        }

        return invoices;
    }
}
