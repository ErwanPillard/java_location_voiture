package Dao;

import Model.OffreReduction;
import Model.TypeAdhesion;
import Model.Voiture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OffreReductionDAOImpl implements OffreReductionDAO{
    public List<OffreReduction> all() throws SQLException{
        ArrayList<OffreReduction> offreReductions = new ArrayList<>();

        Connection c = DatabaseManager.getConnection();
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM OffreReduction");

        ResultSet rset = pstmt.executeQuery();
        while (rset.next()) {
            offreReductions.add(createOffreReduction(rset));
        }

        pstmt.close();
        c.close();

        return offreReductions;
    }

    public OffreReduction createOffreReduction(ResultSet rset) throws SQLException {
        OffreReduction offreReduction = new OffreReduction(rset.getString("nom"), rset.getString("description"), rset.getDate("dateDebut").toLocalDate(), rset.getDate("dateFin").toLocalDate(), rset.getInt("pourcentageReduction"), TypeAdhesion.valueOf(rset.getString("typeAdhesion")));
        return offreReduction;
    }
}
