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
    public void addParticulier(String nom, String prenom, String email, String motDePasse, int age, String telephone, String numeroPermis, LocalDate birthDate){
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {


            if (emailExists(email, connection)) {
                JOptionPane.showMessageDialog(null, "L'email existe déjà. Veuillez en choisir un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return; // Annule l'opération
            }

            Particulier particulier = new Particulier(nom, prenom, email, motDePasse, age, telephone, numeroPermis, birthDate);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.addParticulier(particulier);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addEntreprise(String nom, String email, String motDePasse, String telephone, String siret) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            if (emailExists(email, connection)) {
                JOptionPane.showMessageDialog(null, "L'email existe déjà. Veuillez en choisir un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return; // Annule l'opération
            }

            // Maintenant, utilise cet ID pour créer une Entreprise
            Entreprise entreprise = new Entreprise(nom, email, motDePasse, telephone, siret);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.addEntreprise(entreprise);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}