package Model;

import Dao.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SessionManager {
    private static SessionManager instance;
    private static User currentUser;
    private static Particulier currentParticulier;
    private static Employe currentEmploye;
    private static Entreprise currentEntreprise;
    private static Client currentClient;
    private static String userType;
    private static boolean isLoggedIn;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static Particulier getCurrentParticulier() {
        return currentParticulier;
    }

    public static Entreprise getCurrentEntreprise() {
        return currentEntreprise;
    }

    public static Employe getCurrentEmploye() {
        return currentEmploye;
    }

    public static void getUserInfo(int userId, Connection connection) throws SQLException {
        try {
            // Essayer de récupérer l'Employe
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Employe WHERE id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userType = "Employe";
                // Supposons que Employe ait un constructeur approprié
                currentEmploye = new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("fonction")
                );
                return;
            }

            stmt = connection.prepareStatement("SELECT * FROM Particulier WHERE id = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userType = "Particulier";

                String birthDateStr = rs.getDate("birthDate").toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);

                currentParticulier = new Particulier(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("numeroPermis"),
                        birthDate
                );
                stmt = connection.prepareStatement("SELECT * FROM Client WHERE id = ?");
                stmt.setInt(1, userId);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    currentClient = new Client(
                            rs.getInt("id"),
                            rs.getString("telephone")
                    );
                }
            }

            stmt = connection.prepareStatement("SELECT * FROM Entreprise WHERE id = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userType = "Entreprise";
                currentEntreprise = new Entreprise(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("numeroSiret")
                );
                stmt = connection.prepareStatement("SELECT * FROM Client WHERE id = ?");
                stmt.setInt(1, userId);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    currentClient = new Client(
                            rs.getInt("id"),
                            rs.getString("telephone")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String userType() {
        return userType;
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void logIn(User user) throws SQLException {
        currentUser = user;
        isLoggedIn = true;
        Connection connection = DatabaseManager.getConnection(); // Assure-toi d'avoir cette connexion disponible
        getUserInfo(user.getId(), connection);
    }

    public void logOut() {
        currentUser = null;
        isLoggedIn = false;
    }
}



/*
Code crée employée :
-- Ajout dans la table User
    INSERT INTO User (email, motDePasse) VALUES
        ('em', "em"),
        ('em2', "em2");

-- Récupérer les IDs générés pour les nouveaux utilisateurs
    SET @last_id1 = LAST_INSERT_ID();
    SET @last_id2 = LAST_INSERT_ID() + 1;

-- Ajout dans la table Employe avec les IDs récupérés
    INSERT INTO Employe (id, nom, prenom, fonction) VALUES
        (@last_id1, 'Thomas', 'Pierre-Louis', 'Vendeur'),
        (@last_id2, 'Niccolini', 'Nina', 'Vendeuse');
 */