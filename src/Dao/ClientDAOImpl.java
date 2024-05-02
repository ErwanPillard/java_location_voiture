package Dao;

import Model.Entreprise;
import Model.Particulier;

import java.sql.*;


public class ClientDAOImpl implements ClientDAO {

    @Override
    public void addParticulier(Particulier particulier) throws SQLException {
        String queryUser = "INSERT INTO User (email, motDePasse) VALUES (?, ?)";
        String queryClient = "INSERT INTO Client (id, telephone) VALUES (?, ?)";
        String queryParticulier = "INSERT INTO Particulier (id, nom, prenom, numeroPermis, birthDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement userStatement = connection.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement clientStatement = connection.prepareStatement(queryClient);
             PreparedStatement particulierStatement = connection.prepareStatement(queryParticulier)) {
            // Insertion des données dans la table User
            userStatement.setString(1, particulier.getEmail());
            userStatement.setString(2, particulier.getMotDePasse());
            userStatement.executeUpdate();

            // Récupération de l'ID généré pour l'utilisateur inséré
            ResultSet generatedKeys = userStatement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Erreur lors de la récupération de l'ID de l'utilisateur généré.");
            }
            // Insertion des données dans la table Client
            clientStatement.setInt(1, userId);
            clientStatement.setString(2, particulier.getTelephone());
            clientStatement.executeUpdate();

            particulierStatement.setInt(1, userId);
            particulierStatement.setString(2, particulier.getNom());
            particulierStatement.setString(3, particulier.getPrenom());
            particulierStatement.setString(4, particulier.getNumeroPermis());

            particulierStatement.setDate(5, Date.valueOf(particulier.getBirthDate()));
            //particulierStatement.setDate(5, new java.sql.Date(new java.util.Date().getTime())); //.toLocalDate()
            particulierStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addEntreprise(Entreprise entreprise) throws SQLException {
        String queryUser = "INSERT INTO User (email, motDePasse) VALUES (?, ?)";
        String queryClient = "INSERT INTO Client (id, telephone) VALUES (?, ?)";
        String queryEntreprise = "INSERT INTO Entreprise (id, nom, numeroSiret) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement userStatement = connection.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement clientStatement = connection.prepareStatement(queryClient);
             PreparedStatement entrepriseStatement = connection.prepareStatement(queryEntreprise)) {
            // Insertion des données dans la table User
            userStatement.setString(1, entreprise.getEmail());
            userStatement.setString(2, entreprise.getMotDePasse());
            userStatement.executeUpdate();

            // Récupération de l'ID généré pour l'utilisateur inséré
            ResultSet generatedKeys = userStatement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Erreur lors de la récupération de l'ID de l'utilisateur généré.");
            }

            // Insertion des données dans la table Client
            clientStatement.setInt(1, userId);
            clientStatement.setString(2, entreprise.getTelephone());
            clientStatement.executeUpdate();

            entrepriseStatement.setInt(1, userId);
            entrepriseStatement.setString(2, entreprise.getNom());
            entrepriseStatement.setString(3, entreprise.getNumSiret());
            entrepriseStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(email) FROM User WHERE email = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Vérifie si le compteur est supérieur à 0
                }
            }
        }
        return false; // Par défaut, considère que l'email n'existe pas
    }

}