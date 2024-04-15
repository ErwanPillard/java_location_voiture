package Dao;

import Model.Voiture;

import java.sql.SQLException;

public interface VoitureDAO {
    public void add(Voiture voiture) throws SQLException;
}
