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

                // Extraire la date depuis le ResultSet et la convertir en String
                String birthDateStr = rs.getDate("birthDate").toString();
                // Définir le format de la date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Assure-toi que le format correspond à celui retourné par .toString()
                // Parser la chaîne de caractères en LocalDate
                LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);

                // Maintenant, tu peux utiliser la variable birthDate lors de la création de l'instance de Particulier
                currentParticulier = new Particulier(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("numeroPermis"),
                        birthDate,
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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