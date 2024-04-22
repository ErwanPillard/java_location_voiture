package Dao;

import Model.Voiture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface VoitureDAO {
    void add(Voiture voiture) throws SQLException;
    List<Voiture> all() throws SQLException;
    Voiture createVoiture(ResultSet rset) throws SQLException;

    boolean immatExists(String immatriculation) throws SQLException;

    Voiture findByImmat(String immatriculation) throws SQLException;

    int delete(Voiture voiture) throws SQLException;

    void update(Voiture voiture) throws SQLException;

    byte[] getImageByImmatriculation(String immatriculation);
}
