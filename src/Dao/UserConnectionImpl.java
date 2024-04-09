package Dao;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConnectionImpl implements UserConnection {
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
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String userNom = rs.getString("nom");
                    String userPrenom = rs.getString("prenom");
                    String userEmail = rs.getString("email");

                    // Création de l'objet User
                    User user = new User(userId, userNom, userPrenom, userEmail);

                    // Vérifier le type d'utilisateur et récupérer les informations supplémentaires
                    if (checkAndSetParticulier(conn, user)) {
                        // Informations spécifiques aux particuliers déjà définies dans user
                    } else if (checkAndSetEntreprise(conn, user)) {
                        // Informations spécifiques aux entreprises déjà définies dans user
                    } else if (checkAndSetEmploye(conn, user)) {
                        // Informations spécifiques aux employés déjà définies dans user
                    }

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // retourne null si la connexion a échoué.
    }

    private boolean checkAndSetParticulier(Connection conn, User user) throws SQLException {
        String query = "SELECT numeroPermis, birthDate FROM Particulier WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, user.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setNumPermis(rs.getString("numeroPermis"));
                    user.setDateNaissance(rs.getDate("birthDate"));
                    return true; // L'utilisateur est un particulier
                }
            }
        }
        return false;
    }

    private boolean checkAndSetEntreprise(Connection conn, User user) throws SQLException {
        String query = "SELECT numeroSiret FROM Entreprise WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, user.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setNumSiret(rs.getString("numeroSiret"));
                    return true; // L'utilisateur est une entreprise
                }
            }
        }
        return false;
    }

    private boolean checkAndSetEmploye(Connection conn, User user) throws SQLException {
        String query = "SELECT fonction FROM Employe WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, user.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setFonctionEmploye(rs.getString("fonction"));
                    return true; // L'utilisateur est un employee
                }
            }
        }
        return false;
    }
}
