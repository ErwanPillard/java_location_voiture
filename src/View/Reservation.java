package View;

import Controller.OffreReductionController;
import Controller.PaymentPageController;
import Dao.DatabaseManager;
import Model.OffreReduction;
import Model.SessionManager;
import Model.Voiture;
import Controller.ReservationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import static Model.SessionManager.updateTelephoneInDatabase;

public class Reservation extends JDialog {

    private JTextField txtDateDebutReservation;
    private JTextField txtDateFinReservation;
    private JTextField txtMontant;
    private JTextField txtNombreJour;
    private JTextField txtVoitureImmatriculation;
    private JTextField txtVoitureNbKilometre;
    private JTextField txtVoitureCouleur;
    private JButton btnFermer;
    private JButton btnForfait;
    private JButton btnConfirmer = new JButton("Confirmer la réservation");
    ;

    // Paramètres ajoutés pour la réservation
    private Voiture voiture;
    private String dateDebut;
    private String dateFin;
    private LocalDate dateDebutLocalDate;
    private LocalDate dateFinLocalDate;

    public Reservation(String dateDebut, String dateFin, Voiture voiture) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.voiture = voiture;
        configure();
        initComponents();
        layoutComponents();
    }

    private void configure() {
        setTitle("Réservation d'un véhicule");
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400); // Ajuster la taille selon besoin
        setLocationRelativeTo(null);
    }

    private void convertDates() {
        if (dateDebut != null && dateFin != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                dateDebutLocalDate = LocalDate.parse(dateDebut, formatter);
                dateFinLocalDate = LocalDate.parse(dateFin, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Erreur de format de date: " + e.getMessage());
            }
        } else {
            System.err.println("Les dates de début et de fin de réservation ne peuvent pas être nulles.");
        }
    }

    private int getPrixJournalier(int modeleId) {
        // Initialisation de la valeur par défaut pour le prix journalier
        int prixJournalier = -1;

        // Obtention de la connexion à la base de données
        try (Connection conn = DatabaseManager.getConnection()) {
            // Préparation de la requête SQL pour récupérer le prix journalier
            String sql = "SELECT prixJournalier FROM Modele WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, modeleId);  // Définir l'ID du modèle dans la requête

                // Exécution de la requête
                try (ResultSet rs = stmt.executeQuery()) {
                    // Vérification si un résultat est retourné
                    if (rs.next()) {
                        prixJournalier = rs.getInt("prixJournalier");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du prix journalier: " + e.getMessage());
        }

        return prixJournalier;
    }

    public float getReductionPourcentage(int clientId) {
        String sql = "SELECT o.pourcentageReduction " +
                "FROM ClientOffreAchat c " +
                "JOIN OffreReduction o ON c.id_offre = o.id " +
                "WHERE c.id_client = ? " +
                "AND NOW() BETWEEN o.dateDebut AND o.dateFin";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("pourcentageReduction");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;  // Retourne 0 si aucune réduction n'est trouvée
    }

    private float calculerMontant(LocalDate dateDebut, LocalDate dateFin, Voiture voiture) {
        if (dateDebut == null || dateFin == null || voiture == null) {
            return -1;  // Retourne -1 en cas de données invalides
        }
        long nbJour = ChronoUnit.DAYS.between(dateDebut, dateFin);  // Calcul du nombre de jours

        int clientId = SessionManager.getCurrentClient().getId();  // Assumes there is a method to get the client's ID
        float reduction = getReductionPourcentage(clientId);

        float prixJournalier = getPrixJournalier(voiture.getModele_id()) * (1-(1/reduction));
        if (prixJournalier == -1) {
            return -1;
        }

        return (nbJour * prixJournalier);
    }

    private void initComponents() {
        txtDateDebutReservation = new JTextField(dateDebut);
        txtDateFinReservation = new JTextField(dateFin);
        convertDates();
        txtMontant = new JTextField(String.valueOf(calculerMontant(dateDebutLocalDate, dateFinLocalDate, voiture)) + " €");  // Montant calculé en tant que texte
        long nbJour = ChronoUnit.DAYS.between(dateDebutLocalDate, dateFinLocalDate);
        txtNombreJour = new JTextField(String.valueOf(nbJour));
        txtVoitureImmatriculation = new JTextField(voiture.getImmatriculation());
        txtVoitureNbKilometre = new JTextField(String.valueOf(voiture.getNbKilometre()) + " km");
        txtVoitureCouleur = new JTextField(voiture.getCouleur());
        btnFermer = new JButton("Fermer");
        btnForfait = new JButton("Choisir la réservation");

        txtDateDebutReservation.setEditable(false);
        txtDateFinReservation.setEditable(false);
        txtMontant.setEditable(false);
        txtNombreJour.setEditable(false);
        txtVoitureImmatriculation.setEditable(false);
        txtVoitureNbKilometre.setEditable(false);
        txtVoitureCouleur.setEditable(false);

        //btnFermer.addActionListener(e -> dispose());
        btnFermer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomePage().setVisible(true);
            }
        });

        btnForfait.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    confirmationReservation();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnForfait.setBackground(new Color(0x377e21)); // Couleur Verte
        btnForfait.setFont(new Font("Arial", Font.BOLD, 14)); // Police en gras
        btnForfait.setForeground(Color.WHITE);
        btnForfait.setFocusPainted(false);
        btnForfait.setBorderPainted(false);
        btnForfait.setOpaque(true);
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Date début réservation :"));
        panel.add(txtDateDebutReservation);
        panel.add(new JLabel("Date fin réservation :"));
        panel.add(txtDateFinReservation);
        panel.add(new JLabel("Montant :"));
        panel.add(txtMontant);
        if (txtNombreJour.equals("1")) {
            panel.add(new JLabel("Nombre de jour :"));
        } else {
            panel.add(new JLabel("Nombre de jours :"));
        }
        panel.add(txtNombreJour);
        panel.add(new JLabel("Immatriculation de la voiture :"));
        panel.add(txtVoitureImmatriculation);
        panel.add(new JLabel("Nombre de kilomètres de la voiture :"));
        panel.add(txtVoitureNbKilometre);
        panel.add(new JLabel("Couleur de la voiture :"));
        panel.add(txtVoitureCouleur);
        panel.add(btnFermer, BorderLayout.WEST);
        panel.add(btnForfait, BorderLayout.EAST);

        getContentPane().add(panel);
    }

    public static void toggle(String dateDebut, String dateFin, Voiture voiture) {
        Reservation reservation = new Reservation(dateDebut, dateFin, voiture);
        reservation.setVisible(true);
    }

    private void confirmationReservation() throws SQLException {
        JDialog dialog = new JDialog(this, "Confirmation de réservation", true);
        dialog.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel panelInfo = new JPanel();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel labelDateDebut = new JLabel("Date de début de réservation :");
        JLabel labelDateFin = new JLabel("Date de fin de réservation :");
        JLabel labelMontant = new JLabel("Montant de la réservation :");
        JLabel labelVoiture = new JLabel("Immatriculation de la voiture :");
        JLabel labelEmail = new JLabel("Email :");
        JLabel labelTelephone = new JLabel("Téléphone :");
        JLabel labelNom = new JLabel("Nom :");
        JLabel labelPrenom = new JLabel("Prenom :");
        JLabel labelNumSiret = new JLabel("Numéro Siret :");
        JLabel labelNumPermis = new JLabel("Numéro de permis :");
        JLabel labelBirthDate = new JLabel("Date de naissance :");

        // Champs pour un particulier avec téléphone
        JTextField dateDebutTextField = txtDateDebutReservation;
        JTextField dateFinTextField = txtDateFinReservation;
        JTextField montantTextField = txtMontant;
        JTextField voitureImmatTextField = txtVoitureImmatriculation;

        panelInfo.add(labelDateDebut);
        panelInfo.add(dateDebutTextField);
        panelInfo.add(labelDateFin);
        panelInfo.add(dateFinTextField);
        panelInfo.add(labelMontant);
        panelInfo.add(montantTextField);
        panelInfo.add(labelVoiture);
        panelInfo.add(voitureImmatTextField);

        String dateDebut = txtDateDebutReservation.getText();
        String dateFin = txtDateFinReservation.getText();
        Voiture voiture = getVoitureByImmatriculation(txtVoitureImmatriculation.getText());
        float montantFloat = Float.parseFloat(String.valueOf(calculerMontant(dateDebutLocalDate, dateFinLocalDate, voiture)));

        btnConfirmer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Rediriger vers la page de paiement
                PaymentPage paymentPage = new PaymentPage(null);
                paymentPage.setVisible(true);

                // Vérifier si le paiement a été effectué avec succès
                if (paymentPage.isPaymentSuccessful()) {
                    PaymentPageController.ajouterFactureVoiture(dateDebut, dateFin, voiture, montantFloat);
                    ReservationController.confirmerReservation(dateDebut, dateFin, voiture, montantFloat);
                    JOptionPane.showMessageDialog(dialog, "Réservation confirmée avec succès !");

                    // Revenir à la page d'accueil
                    HomePage homePage = new HomePage();
                    homePage.setVisible(true);

                    // Fermer la fenêtre actuelle de réservation
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Le paiement a échoué.");
                }
            }
        });



        btnConfirmer.setBackground(new Color(0x377e21)); // Couleur Verte
        btnConfirmer.setFont(new Font("Arial", Font.BOLD, 14)); // Police en gras
        btnConfirmer.setForeground(Color.WHITE);
        btnConfirmer.setFocusPainted(false);
        btnConfirmer.setBorderPainted(false);
        btnConfirmer.setOpaque(true);

        JButton btnAnnuler = new JButton("Annuler la réservation");
        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomePage().setVisible(true);
            }
        });
        btnAnnuler.setBackground(new Color(0xFF0000)); // Couleur ROUGE
        btnAnnuler.setFont(new Font("Arial", Font.BOLD, 14)); // Police en gras
        btnAnnuler.setForeground(Color.WHITE);
        btnAnnuler.setFocusPainted(false);
        btnAnnuler.setBorderPainted(false);
        btnAnnuler.setOpaque(true);

        buttonPanel.add(btnAnnuler, BorderLayout.WEST);
        buttonPanel.add(btnConfirmer, BorderLayout.EAST);

        panelInfo.setLayout(new GridLayout(10, 2, 10, 10));
        panelInfo.add(labelEmail);
        panelInfo.add(new JTextField(SessionManager.getCurrentUser().getEmail()));
        panelInfo.add(labelTelephone);
        panelInfo.add(new JTextField(SessionManager.getCurrentClient().getTelephone()));

        if (SessionManager.userType.equals("Particulier")) {
            panelInfo.add(labelNom);
            panelInfo.add(new JTextField(SessionManager.getCurrentParticulier().getNom()));
            panelInfo.add(labelPrenom);
            panelInfo.add(new JTextField(SessionManager.getCurrentParticulier().getPrenom()));
            panelInfo.add(labelNumPermis);
            panelInfo.add(new JTextField(SessionManager.getCurrentParticulier().getNumeroPermis()));
            panelInfo.add(labelBirthDate);
            panelInfo.add(new JTextField(String.valueOf(SessionManager.getCurrentParticulier().getBirthDate())));
        } else if (SessionManager.userType.equals("Entreprise")) {
            panelInfo.add(labelNom);
            panelInfo.add(new JTextField(SessionManager.getCurrentEntreprise().getNom()));
            panelInfo.add(labelNumSiret);
            panelInfo.add(new JTextField(SessionManager.getCurrentEntreprise().getNumSiret()));
        }

        dialog.setSize(500, 600);
        dialog.add(panelInfo, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public Voiture getVoitureByImmatriculation(String immatriculation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Voiture voiture = null;

        try {
            conn = DatabaseManager.getConnection();
            // Préparer la requête SQL pour récupérer toutes les colonnes de la voiture
            pstmt = conn.prepareStatement("SELECT * FROM Voiture WHERE immatriculation = ?");
            pstmt.setString(1, immatriculation);
            // Exécuter la requête
            rs = pstmt.executeQuery();
            // Lire le résultat
            if (rs.next()) {
                // Conversion de Date en LocalDate
                LocalDate dateMiseEnCirculation = rs.getDate("dateMiseEnCirculation").toLocalDate();

                voiture = new Voiture(
                        dateMiseEnCirculation,
                        rs.getString("immatriculation"),
                        rs.getString("couleur"),
                        rs.getDouble("nbKilometre"),
                        rs.getInt("modele_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return voiture;
    }
}
