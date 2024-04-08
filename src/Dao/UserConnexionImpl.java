package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConnexionImpl implements UserConnexion {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Location_Voiture"; // Remplace avec l'URL de ta base de données
    private static final String USER = "root"; // Remplace avec ton utilisateur de base de données
    private static final String PASS = ""; // Remplace avec ton mot de passe de base de données

    public UserConnexionImpl() {
        try {
            // Assure-toi que le pilote JDBC est chargé
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean connect(String username, String password) {
        String query = "SELECT COUNT(*) AS count FROM User WHERE email = ? AND motDePasse = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Vérifie si le compte existe
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
