package Dao;

import Model.Client;
import Model.Particulier;
import Model.User;

import java.sql.*;


public class ClientDAOImpl implements ClientDAO{
    private Connection connection;

    public ClientDAOImpl(Connection connection){this.connection = connection;}

    @Override
    public void add(Particulier particulier) throws SQLException {
        String queryUser = "INSERT INTO User (nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";
        String queryClient = "INSERT INTO Client (id, age, telephone) VALUES (?, ?, ?)";
        String queryParticulier = "INSERT INTO Particulier (id, numeroPermis, birthDate) VALUES (?, ?, ?)";

        try (PreparedStatement userStatement = connection.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement clientStatement = connection.prepareStatement(queryClient);
             PreparedStatement particulierStatement = connection.prepareStatement(queryParticulier)) {
            // Insertion des données dans la table User
            userStatement.setString(1, particulier.getNom());
            userStatement.setString(2, particulier.getPrenom());
            userStatement.setString(3, particulier.getEmail());
            userStatement.setString(4, particulier.getMotDePasse());
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
            clientStatement.setInt(2, particulier.getAge());
            clientStatement.setString(3, particulier.getTelephone());
            clientStatement.executeUpdate();

            particulierStatement.setInt(1, userId);
            particulierStatement.setString(2, particulier.getNumeroPermis());

            particulierStatement.setDate(3, Date.valueOf(particulier.getBirthDate().toLocalDate())); //.toLocalDate()
            particulierStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
