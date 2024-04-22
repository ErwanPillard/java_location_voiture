package Dao;

import Model.Modele;
import java.sql.SQLException;

public interface ModeleDAO {
    void add(Modele modele) throws SQLException;

    String[] all() throws SQLException;

    int getIdByName(String modelName) throws SQLException;

    String getNameById(int id) throws SQLException;
}
