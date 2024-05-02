package Model;

import Dao.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean changePassword(String oldPassword, String newPassword) {
        if (currentUser == null) {
            return false; // Aucun utilisateur connecté
        }
        if (verifyPassword(currentUser, oldPassword)) {
            String hashedNewPassword = hashPassword(newPassword);
            currentUser.setMotDePasse(hashedNewPassword);
            return updatePasswordInDatabase(currentUser.getId(), hashedNewPassword);
        }
        return false; // Ancien mot de passe incorrect
    }

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private static boolean verifyPassword(User user, String password) {
        String query = "SELECT id, email, motDePasse FROM User WHERE email = ?";
        try (Connection conn = DatabaseManager.getConnection(); // Utilise la connexion unique gérée par DatabaseManager
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getEmail());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("motDePasse");
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // retourne null si la connexion a échoué ou si le mot de passe est incorrect.
    }

    private static boolean updatePasswordInDatabase(int userId, String newHashedPassword) {
        String sql = "UPDATE User SET motDePasse = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newHashedPassword);
            pstmt.setInt(2, userId);
            int updatedRows = pstmt.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateTelephoneInDatabase(int userId, String telephone) {
        String sql = "UPDATE Client SET telephone = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, telephone);
            pstmt.setInt(2, userId);
            int updatedRows = pstmt.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object[][] fetchReservationsData(int clientId) {
        List<Object[]> list = new ArrayList<>();
        String query = "SELECT numReservation, dateDebutReservation, dateFinReservation, montant, etat, voiture_immatriculation FROM Reservation WHERE id_client = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getInt("numReservation"),
                        rs.getDate("dateDebutReservation").toString(),
                        rs.getDate("dateFinReservation").toString(),
                        rs.getDouble("montant"),
                        rs.getString("etat"),
                        rs.getString("voiture_immatriculation"),
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.toArray(new Object[0][]);
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