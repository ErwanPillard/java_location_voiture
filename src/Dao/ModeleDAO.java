package Dao;

import Model.Modele;
import java.sql.SQLException;

public interface ModeleDAO {
    void add(Modele modele) throws SQLException;
}
