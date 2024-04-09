package Dao;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConnectionImpl implements UserConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Location_Voiture"; // Remplace avec l'URL de ta base de données
    private static final String USER = "root"; // Remplace avec ton utilisateur de base de données
    private static final String PASS = ""; // Remplace avec ton mot de passe de base de données

    public UserConnectionImpl() {
        try {
            // Assure-toi que le pilote JDBC est chargé
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User connect(String email, String password) {
        String query = "SELECT id, nom, prenom, email FROM User WHERE email = ? AND motDePasse = ?";
        try (Connection conn = DatabaseManager.getConnection(); // Utilise la connexion unique gérée par DatabaseManager
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Création d'un nouvel utilisateur avec les informations récupérées.
                    int userId = rs.getInt("id");
                    String userNom = rs.getString("nom");
                    String userPrenom = rs.getString("prenom");
                    String userEmail = rs.getString("email");

                    // Retourne l'utilisateur si la connexion est réussie.
                    return new User(userId, userNom, userPrenom, userEmail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // retourne null si la connexion a échoué.
    }
}
