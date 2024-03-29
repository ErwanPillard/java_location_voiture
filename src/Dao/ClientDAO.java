package Dao;

import Model.Client;
import Model.User;

import java.sql.SQLException;

public interface ClientDAO {
    void add(Client client) throws SQLException;
}
