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
                    invoice.getMontant(),
                    invoice.isEtat()
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
        String sql = "SELECT f.numeroFacture, f.dateEmission, f.montant, f.etat " +
                "FROM Facture f " +
                "JOIN Reservation r ON f.numeroFacture = r.facture_numeroFacture ";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int numeroFacture = rs.getInt("numeroFacture");
                    //LocalDate dateEmission = rs.getLocalDate("dateEmission");
                    LocalDateTime dateEmission = LocalDateTime.parse("30-01-2003");
                    float montant = rs.getFloat("montant");
                    boolean etat = rs.getBoolean("etat");
                    invoices.add(new Facture(numeroFacture, dateEmission, montant, etat));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gestion des exceptions
        }

        return invoices;
    }
}
