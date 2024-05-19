package Dao;

import Model.OffreReduction;
import Model.TypeAdhesion;
import Model.Voiture;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OffreReductionDAOImpl implements OffreReductionDAO{
    @Override
    public List<OffreReduction> all() throws SQLException {
        List<OffreReduction> offres = new ArrayList<>();
        String sql = "SELECT * FROM OffreReduction";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                LocalDate dateDebut = rs.getDate("dateDebut").toLocalDate();
                LocalDate dateFin = rs.getDate("dateFin").toLocalDate();
                float pourcentageReduction = rs.getFloat("pourcentageReduction");
                String typeAdhesion = rs.getString("typeAdhesion");
                OffreReduction offre = new OffreReduction(id, nom, description, dateDebut, dateFin, pourcentageReduction, typeAdhesion);
                offres.add(offre);
            }
        }
        return offres;
    }

    public void add(OffreReduction offreReduction) throws SQLException {
        String query = "INSERT INTO OffreReduction(nom, description, dateDebut, dateFin, pourcentageReduction, typeAdhesion) VALUES(?,?,?,?,?,?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement offreStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            offreStatement.setString(1, offreReduction.getNom());
            offreStatement.setString(2, offreReduction.getDescription());
            offreStatement.setDate(3, Date.valueOf(offreReduction.getDateDebut()));
            offreStatement.setDate(4, Date.valueOf(offreReduction.getDateFin()));
            offreStatement.setFloat(5, offreReduction.getPourcentageReduction());
            offreStatement.setString(6, offreReduction.getTypeAdhesion());
            // Exécute la mise à jour de la base de données
            offreStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public OffreReduction createOffreReduction(ResultSet rset) throws SQLException {
        OffreReduction offreReduction = new OffreReduction(rset.getString("nom"), rset.getString("description"), rset.getDate("dateDebut").toLocalDate(), rset.getDate("dateFin").toLocalDate(), rset.getInt("pourcentageReduction"), rset.getString("typeAdhesion"));
        return offreReduction;
    }
}
