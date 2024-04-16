package Dao;

import Model.Voiture;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VoitureDAOImpl implements VoitureDAO{
    public void add(Voiture voiture) throws SQLException {
        String query = "INSERT INTO Voiture(immatriculation, dateMiseEnCirculation, nbKilometre, couleur, modele_id) VALUES(?,?,?,?,?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement voitureStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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

    public List<Voiture> all() throws SQLException{
        ArrayList<Voiture> voitures = new ArrayList<>();

        Connection c = DatabaseManager.getConnection();
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Voiture");

        ResultSet rset = pstmt.executeQuery();
        while (rset.next()) {
            voitures.add(createUser(rset));
        }

        pstmt.close();
        c.close();

        return voitures;

    }

    public boolean immatExists(String immatriculation) throws SQLException{
        String query = "SELECT COUNT(immatriculation) FROM Voiture WHERE immatriculation = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, immatriculation);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Vérifie si le compteur est supérieur à 0
                }
            }
        }
        return false; // Par défaut, considère que l'email n'existe pas
    }

    /**
     * Métode qui retourne un nouvel objet User à partir d'un résultat de requête SQL
     * @param rset Résultat d'une requête SQL sélectionnant des utilisateurs
     * @return Nouvel objet User construit à partir du résultat de requête SQL
     * @throws SQLException
     */
    public Voiture createUser(ResultSet rset) throws SQLException {
        Voiture voiture = new Voiture(rset.getDate("dateMiseEnCirculation").toLocalDate(), rset.getString("immatriculation"), rset.getString("couleur"), rset.getInt("nbKilometre"), rset.getInt("modele_id"));
        return voiture;
    }
}
