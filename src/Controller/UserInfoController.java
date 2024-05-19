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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        invoiceFrame.setSize(700, 400);
        invoiceFrame.setLayout(new BorderLayout());
        invoiceFrame.setLocationRelativeTo(null);

        // Récupère les factures pour l'utilisateur actuel.
        List<Facture> userInvoices = getUserInvoices(SessionManager.getCurrentUser().getId());

        String[] columnNames = {"Numéro de facture", "Date d'emission", "Montant", "Objet", "Etat"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Facture invoice : userInvoices) {
            Object[] row = new Object[]{
                    invoice.getNumFacture(),
                    invoice.getDate(),
                    invoice.getMontant(),
                    invoice.getObjet(),
                    invoice.getEtat()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        invoiceFrame.add(scrollPane, BorderLayout.CENTER);

        // Ajoute un WindowListener pour ouvrir UserInfo à la fermeture
        invoiceFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Ouvre la fenêtre UserInfo
                UserInfo userInfo = new UserInfo();
                userInfo.setVisible(true);
            }
        });

        // Affiche la fenêtre des factures.
        invoiceFrame.setVisible(true);
    }

    public List<Facture> getUserInvoices(int userId) {
        List<Facture> invoices = new ArrayList<>();

        String sql = "SELECT f.numeroFacture, f.dateEmission, r.dateDebutReservation, r.dateFinReservation, " +
                "COALESCE(r.montant, o.pourcentageReduction * 10) AS montant, " +
                "COALESCE(r.etat, 'Achat Offre') AS etat, " +
                "r.voiture_immatriculation, f.id_client, " +
                "CASE WHEN r.dateDebutReservation IS NOT NULL THEN 'Reservation' ELSE 'OffreReduction' END AS objet " +
                "FROM Facture f " +
                "LEFT JOIN Reservation r ON f.dateDebutReservation = r.dateDebutReservation AND f.dateFinReservation = r.dateFinReservation AND f.id_client = r.id_client " +
                "LEFT JOIN ClientOffreAchat c ON f.id_client = c.id_client " +
                "LEFT JOIN OffreReduction o ON c.id_offre = o.id " +
                "WHERE f.id_client = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int numeroFacture = rs.getInt("numeroFacture");
                    LocalDateTime dateEmission = rs.getTimestamp("dateEmission").toLocalDateTime();
                    LocalDateTime dateDebut = null;
                    LocalDateTime dateFin = null;
                    if (rs.getTimestamp("dateDebutReservation") != null) {
                        dateDebut = rs.getTimestamp("dateDebutReservation").toLocalDateTime();
                    }
                    if (rs.getTimestamp("dateFinReservation") != null) {
                        dateFin = rs.getTimestamp("dateFinReservation").toLocalDateTime();
                    }
                    float montant = rs.getFloat("montant");
                    String etat = rs.getString("etat");
                    String voitureImmatriculation = rs.getString("voiture_immatriculation");
                    int idClient = rs.getInt("id_client");
                    String objet = rs.getString("objet");

                    invoices.add(new Facture(numeroFacture, dateEmission, dateDebut, dateFin, montant, etat, voitureImmatriculation, idClient, objet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gestion des exceptions
        }
        return invoices;
    }
}
