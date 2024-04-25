package Dao;

import Model.Modele;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ModeleDAO {
    void add(Modele modele) throws SQLException;

    String[] all() throws SQLException;

    Modele getModeleById(int id) throws SQLException;

    int getIdByName(String modelName) throws SQLException;

    String getNameById(int id) throws SQLException;

    Modele createModele(ResultSet rset) throws SQLException;

    void update(Modele modele) throws SQLException;
}
