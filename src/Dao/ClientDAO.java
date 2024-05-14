package Dao;

import Model.*;

import java.sql.SQLException;
import java.util.List;

public interface ClientDAO {
    void addParticulier(Particulier particulier) throws SQLException;
    void addEntreprise(Entreprise entreprise) throws SQLException;
    boolean emailExists(String email) throws SQLException;
    Client findByTelephone(String telephone) throws SQLException;
    Client getClientById(int id) throws SQLException;



    List<User> allUserClient() throws SQLException;

    List<Particulier> allParticuliers() throws SQLException;

    List<Entreprise> allEntreprises() throws SQLException;
}
