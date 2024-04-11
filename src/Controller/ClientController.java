package Controller;

import Dao.ClientDAO;
import Dao.ClientDAOImpl;
import Model.Entreprise;
import Model.Particulier;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

import static BDD.init_bdd.*;

public class ClientController {
    public boolean emailExists(String email, Connection connection) throws SQLException {
        String query = "SELECT COUNT(email) FROM User WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Vérifie si le compteur est supérieur à 0
                }
            }
        }
        return false; // Par défaut, considère que l'email n'existe pas
    }

    public int addUserAndGetId(String email, String motDePasse, Connection connection) throws SQLException {
        String queryUser = "INSERT INTO User (email, motDePasse) VALUES (?, ?)";
        int userId = -1;

        try (PreparedStatement pstmt = connection.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, email);
            pstmt.setString(2, motDePasse);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de l'utilisateur a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1); // Récupère l'ID généré
                } else {
                    throw new SQLException("La création de l'utilisateur a échoué, ID non obtenu.");
                }
            }
        }
        return userId;
    }

    public void addParticulier(String nom, String prenom, String email, String motDePasse, int age, String telephone, String numeroPermis, LocalDate birthDate) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            // Vérifie d'abord si l'email existe déjà
            if (emailExists(email, connection)) {
                JOptionPane.showMessageDialog(null, "L'email existe déjà. Veuillez en choisir un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return; // Annule l'opération
            }

            // Insère l'utilisateur et récupère son ID
            int userId = addUserAndGetId(email, motDePasse, connection);

            // Maintenant, utilise cet ID pour créer un Particulier
            Particulier particulier = new Particulier(userId, nom, prenom, numeroPermis, birthDate, age);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.addParticulier(particulier);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addEntreprise(String nom, String email, String motDePasse, String telephone, String siret) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            // Vérifie d'abord si l'email existe déjà
            if (emailExists(email, connection)) {
                JOptionPane.showMessageDialog(null, "L'email existe déjà. Veuillez en choisir un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return; // Annule l'opération
            }

            // Insère l'utilisateur et récupère son ID
            int userId = addUserAndGetId(email, motDePasse, connection);

            // Maintenant, utilise cet ID pour créer une Entreprise
            Entreprise entreprise = new Entreprise(userId, nom, siret);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.addEntreprise(entreprise);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
