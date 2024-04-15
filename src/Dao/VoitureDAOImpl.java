package Dao;

import Model.Voiture;

import java.sql.*;

public class VoitureDAOImpl implements VoitureDAO{
    private Connection connection;
    public VoitureDAOImpl(Connection connection){this.connection = connection;}

    public void add(Voiture voiture) throws SQLException {
        String query = "INSERT INTO Voiture(immatriculation, dateMiseEnCirculation, nbKilometre, couleur, modele_id) VALUES(?,?,?,?,?)";

        try (PreparedStatement voitureStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            voitureStatement.setString(1, voiture.getImmatriculation());
            voitureStatement.setDate(2, Date.valueOf(voiture.getDateMiseCirculation()));
            voitureStatement.setDouble(3,voiture.getNbKilometre());
            voitureStatement.setString(4, voiture.getCouleur());
            voitureStatement.setInt(5, voiture.getModele_id());
            // Exécute la mise à jour de la base de données
            voitureStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
