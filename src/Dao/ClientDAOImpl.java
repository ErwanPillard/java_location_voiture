package Dao;

import Model.Client;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ClientDAOImpl implements ClientDAO{
    private Connection connection;

    public ClientDAOImpl(Connection connection){this.connection = connection;}

    @Override
    public void add(Client client) throws SQLException {
        String queryUser = "INSERT INTO User (nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";
        String queryClient = "INSERT INTO Client (id, age, telephone) VALUES (?, ?, ?)";

        try (PreparedStatement userStatement = connection.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement clientStatement = connection.prepareStatement(queryClient)) {
            // Insertion des données dans la table User
            userStatement.setString(1, client.getNom());
            userStatement.setString(2, client.getPrenom());
            userStatement.setString(3, client.getEmail());
            userStatement.setString(4, client.getMotDePasse());
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
            clientStatement.setInt(2, client.getAge());
            clientStatement.setString(3, client.getTelephone());
            clientStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
