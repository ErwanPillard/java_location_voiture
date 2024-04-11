package Dao;

import Model.Client;
import Model.Entreprise;
import Model.Particulier;
import Model.User;

import java.sql.SQLException;

public interface ClientDAO {
    void addParticulier(Particulier particulier) throws SQLException;
    void addEntreprise(Entreprise entreprise) throws SQLException;
}
