package Controller;

import Dao.ClientDAO;
import Dao.ClientDAOImpl;
import Model.Particulier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static BDD.init_bdd.*;

public class ClientFormController {
    public void addClient(String nom, String prenom, String email, String motDePasse, int age, String telephone, LocalDate birthDate, String numeroPermis, String type){
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            if (type.equals("Entreprise")){
                //Entreprise entreprise = new Entreprise(...);
            } else if (type.equals("Particulier")) {
                Particulier particulier = new Particulier(nom, prenom, email, motDePasse, age, telephone, numeroPermis, birthDate);
                ClientDAO clientDAO = new ClientDAOImpl(connection);
                clientDAO.add(particulier);
            }

            /*Client client = new Client(nom, prenom, email, motDePasse, age, telephone);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.add(client);*/

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
