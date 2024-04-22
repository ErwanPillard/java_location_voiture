package Dao;

import Model.Voiture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VoitureDAOImpl implements VoitureDAO {
    public void add(Voiture voiture) throws SQLException {
        String query = "INSERT INTO Voiture(immatriculation, dateMiseEnCirculation, nbKilometre, couleur, modele_id) VALUES(?,?,?,?,?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement voitureStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            voitureStatement.setString(1, voiture.getImmatriculation());
            voitureStatement.setDate(2, Date.valueOf(voiture.getDateMiseCirculation()));
            voitureStatement.setDouble(3, voiture.getNbKilometre());
            voitureStatement.setString(4, voiture.getCouleur());
            voitureStatement.setInt(5, voiture.getModele_id());
            // Exécute la mise à jour de la base de données
            voitureStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Voiture> all() throws SQLException {
        ArrayList<Voiture> voitures = new ArrayList<>();

        Connection c = DatabaseManager.getConnection();
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Voiture");

        ResultSet rset = pstmt.executeQuery();
        while (rset.next()) {
            voitures.add(createVoiture(rset));
        }

        pstmt.close();
        c.close();

        return voitures;
    }

    public boolean immatExists(String immatriculation) throws SQLException {
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
     *
     * @param rset Résultat d'une requête SQL sélectionnant des utilisateurs
     * @return Nouvel objet User construit à partir du résultat de requête SQL
     * @throws SQLException
     */
    public Voiture createVoiture(ResultSet rset) throws SQLException {
        Voiture voiture = new Voiture(rset.getDate("dateMiseEnCirculation").toLocalDate(), rset.getString("immatriculation"), rset.getString("couleur"), rset.getInt("nbKilometre"), rset.getInt("modele_id"));
        return voiture;
    }

    public Voiture findByImmat(String immatriculation) throws SQLException {
        Connection c = DatabaseManager.getConnection();

        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Voiture WHERE immatriculation = ?");
        pstmt.setString(1, immatriculation);

        Voiture voiture = null;
        ResultSet rset = pstmt.executeQuery();

        while (rset.next()) {
            voiture = createVoiture(rset);
        }

        pstmt.close();
        c.close();

        return voiture;
    }

    public int delete(Voiture voiture) throws SQLException {
        Connection c = DatabaseManager.getConnection();
        PreparedStatement pstmt = c.prepareStatement("DELETE FROM Voiture where immatriculation = ?");
        pstmt.setString(1, voiture.getImmatriculation());

        int rowsAffected = pstmt.executeUpdate();

        pstmt.close();
        c.close();

        return rowsAffected;
    }

    public void update(Voiture voiture) throws SQLException {
        String query = "UPDATE Voiture SET dateMiseEnCirculation = ?, nbKilometre = ?, couleur = ?, modele_id = ? WHERE immatriculation = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Mettre à jour les valeurs des champs
            pstmt.setDate(1, Date.valueOf(voiture.getDateMiseCirculation()));
            pstmt.setDouble(2, voiture.getNbKilometre());
            pstmt.setString(3, voiture.getCouleur());
            pstmt.setInt(4, voiture.getModele_id());

            pstmt.setString(5, voiture.getImmatriculation());

            // Exécuter la requête SQL
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public byte[] getImageByImmatriculation(String immatriculation) {

        Connection conn = null;

        PreparedStatement pstmt = null;

        ResultSet rs = null;

        byte[] imageBytes = null;



        try {

            conn = DatabaseManager.getConnection();



            // Préparer la requête SQL pour récupérer l'image

            pstmt = conn.prepareStatement("SELECT image FROM Voiture WHERE immatriculation = ?");

            pstmt.setString(1, immatriculation);



            // Exécuter la requête

            rs = pstmt.executeQuery();



            // Lire le résultat

            if (rs.next()) {

                // Récupérer le BLOB

                InputStream inputStream = rs.getBinaryStream("image");



                // Convertir l'InputStream en tableau de bytes

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[4096];

                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {

                    outputStream.write(buffer, 0, bytesRead);

                }

                imageBytes = outputStream.toByteArray();

            }

        } catch (SQLException | IOException e) {

            e.printStackTrace();

        } finally {

            // Fermer les ressources

            try {

                if (rs != null) rs.close();

                if (pstmt != null) pstmt.close();

                if (conn != null) conn.close();

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }



        return imageBytes;

    }
}