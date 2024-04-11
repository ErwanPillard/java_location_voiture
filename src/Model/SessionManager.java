package Model;

import Dao.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionManager {
    private static SessionManager instance;
    private static User currentUser;
    private static Particulier currentParticulier;
    private static Employe currentEmploye;
    private static Entreprise currentEntreprise;
    private static String userType;
    private boolean isLoggedIn;

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

            // Essayer de récupérer le Particulier
            stmt = connection.prepareStatement("SELECT * FROM Particulier WHERE id = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userType = "Particulier";
                currentParticulier = new Particulier(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("numeroPermis"),
                        rs.getDate("dateDeNaissance").toLocalDate(), // Assure-toi que la classe Particulier accepte une LocalDate pour la date de naissance
                        rs.getInt("age")
                );
                return;
            }

            // Essayer de récupérer l'Entreprise
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
            }

            // Si aucun type n'est trouvé, userType reste à son état initial
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow l'exception pour la gestion d'erreur externe
        }
    }

    public static String userType() {
        return userType;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void logIn(User user) throws SQLException {
        currentUser = user;
        this.isLoggedIn = true;
        Connection connection = DatabaseManager.getConnection(); // Assure-toi d'avoir cette connexion disponible
        getUserInfo(user.getId(), connection);
    }

    public void logOut() {
        currentUser = null;
        this.isLoggedIn = false;
    }
}
