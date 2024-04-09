package Model;

import Dao.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionManager {
    private static SessionManager instance;
    private static User currentUser;
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

    public static String getUserType(int userId, Connection connection) {
        String type = "Inconnu"; // Valeur par défaut si aucun type n'est trouvé

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM Employe WHERE id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                type = "Employe";
                rs.close();
                stmt.close();
                return type;
            }

            stmt = connection.prepareStatement("SELECT id FROM Particulier WHERE id = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                type = "Particulier";
                rs.close();
                stmt.close();
                return type;
            }

            stmt = connection.prepareStatement("SELECT id FROM Entreprise WHERE id = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                type = "Entreprise";
                rs.close();
                stmt.close();
                return type;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
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
        userType = getUserType(user.getId(), connection);
    }

    public void logOut() {
        currentUser = null;
        this.isLoggedIn = false;
    }
}
