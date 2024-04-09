package Controller;

import Dao.ClientDAO;
import Dao.ClientDAOImpl;
import Model.Client;
import Model.Particulier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static BDD.init_bdd.*;

public class ClientController {
    public void addClientPariculier(String nom, String prenom, String email, String motDePasse, int age, String telephone, LocalDate birthDate, String numeroPermis, String type){
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            Particulier particulier = new Particulier(nom, prenom, email, motDePasse, age, telephone, numeroPermis, birthDate);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.add(particulier);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
