package Dao;

import Model.Entreprise;
import Model.Particulier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ClientDAOImpl implements ClientDAO {
    private final Connection connection;

    public ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addParticulier(Particulier particulier) throws SQLException {
        String queryClient = "INSERT INTO Client (id, telephone) VALUES (?, ?)";
        String queryParticulier = "INSERT INTO Particulier (id, nom, prenom, numeroPermis, birthDate, age) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement clientStatement = connection.prepareStatement(queryClient);
             PreparedStatement particulierStatement = connection.prepareStatement(queryParticulier)) {

            // Insertion des données dans la table Client
            clientStatement.setInt(1, particulier.getId());
            clientStatement.setString(2, particulier.getTelephone());
            clientStatement.executeUpdate();

            particulierStatement.setInt(1, particulier.getId());
            particulierStatement.setString(2, particulier.getNom());
            particulierStatement.setString(3, particulier.getPrenom());
            particulierStatement.setString(4, particulier.getNumeroPermis());
            particulierStatement.setDate(5, Date.valueOf(particulier.getBirthDate()));
            particulierStatement.setInt(6, particulier.getAge());
            particulierStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addEntreprise(Entreprise entreprise) throws SQLException {
        String queryClient = "INSERT INTO Client (id, telephone) VALUES (?, ?)";
        String queryEntreprise = "INSERT INTO Entreprise (id, nom, numeroSiret) VALUES (?, ?, ?)";

        try (PreparedStatement clientStatement = connection.prepareStatement(queryClient);
             PreparedStatement entrepriseStatement = connection.prepareStatement(queryEntreprise)) {

            // Insertion des données dans la table Client
            clientStatement.setInt(1, entreprise.getId());
            clientStatement.setString(2, entreprise.getTelephone());
            clientStatement.executeUpdate();

            entrepriseStatement.setInt(1, entreprise.getId());
            entrepriseStatement.setString(2, entreprise.getNom());
            entrepriseStatement.setString(3, entreprise.getNumSiret());
            entrepriseStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
