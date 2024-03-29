package Controller;

import Dao.ClientDAO;
import Dao.ClientDAOImpl;
import Model.Client;
import Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static BDD.init_bdd.*;

public class ClientController {
    public void addClient(String nom, String prenom, String email, String motDePasse, int age, String telephone){
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {


            Client client = new Client(nom, prenom, email, motDePasse, age, telephone);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.add(client);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
