package Controller;

import Dao.DatabaseManager;
import Dao.UserConnection;
import Model.SessionManager;
import Model.User;
import View.HomePage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class HomeController {
    private final HomePage homePage;
    private final UserConnection userConnexion;


    public HomeController(HomePage homePage, UserConnection userConnexion) {
        this.homePage = homePage;
        this.userConnexion = userConnexion;

        // Essaye d'initialiser et capture toutes les exceptions
        try {
            initController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initController() {
        homePage.getBtnLogin().addActionListener(e -> {
            try {
                showLoginDialog();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void showLoginDialog() throws SQLException {
        // Exemple fictif de récupération de nom d'utilisateur et mot de passe
        String username = "user";
        String password = "pass";

        // Appelle la méthode de connexion sur l'objet userConnexion
        User user = userConnexion.connect(username, password);
        if (user != null) {
            SessionManager.getInstance().logIn(user);
        } else {
            SessionManager.getInstance().logOut();
        }
    }

    public static boolean isCarAvailable(String immatriculation, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) AS count FROM Reservation " +
                "WHERE voiture_immatriculation = ? " +
                "AND (dateDebutReservation <= ? AND dateFinReservation >= ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, immatriculation);
            stmt.setDate(2, java.sql.Date.valueOf(endDate));
            stmt.setDate(3, java.sql.Date.valueOf(startDate));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt("count") > 0) {
                    return false;  // Voiture non disponible
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;  // Voiture disponible
    }
}
