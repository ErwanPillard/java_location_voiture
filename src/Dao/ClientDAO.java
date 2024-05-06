package Dao;

import Model.Entreprise;
import Model.Particulier;

import java.sql.Connection;
import java.sql.SQLException;

public interface ClientDAO {
    void addParticulier(Particulier particulier) throws SQLException;
    void addEntreprise(Entreprise entreprise) throws SQLException;
    boolean emailExists(String email) throws SQLException;
}