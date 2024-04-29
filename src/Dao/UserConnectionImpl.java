package Dao;

import Model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConnectionImpl implements UserConnection {
    @Override
    public User connect(String email, String password) {
        String query = "SELECT id, email, motDePasse FROM User WHERE email = ?";
        try (Connection conn = DatabaseManager.getConnection(); // Utilise la connexion unique gérée par DatabaseManager
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("motDePasse");
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        // Création d'un nouvel utilisateur avec les informations récupérées.
                        int userId = rs.getInt("id");
                        String userEmail = rs.getString("email");

                        // Retourne l'utilisateur si la connexion est réussie.
                        return new User(userId, userEmail);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // retourne null si la connexion a échoué ou si le mot de passe est incorrect.
    }
}
