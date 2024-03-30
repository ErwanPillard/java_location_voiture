package Dao;

import Model.Client;
import Model.Particulier;
import Model.User;

import java.sql.SQLException;

public interface ClientDAO {
    void add(Particulier particulier) throws SQLException;
}
