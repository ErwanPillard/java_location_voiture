package Controller;

import Dao.ClientDAO;
import Dao.ClientDAOImpl;
import Model.Client;
import Model.Entreprise;
import Model.Particulier;
import Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static BDD.init_bdd.*;

public class ClientController {
    public void addPariculier(String nom, String prenom, String email, String motDePasse, int age, String telephone, String numeroPermis, LocalDate birthDate){
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            Particulier particulier = new Particulier(nom, prenom, email, motDePasse, age, telephone, numeroPermis, birthDate);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.addParticulier(particulier);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addEntreprise(String nom, String email, String motDePasse, int age, String telephone, String siret){
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            Entreprise entreprise = new Entreprise(nom, email, motDePasse, age, telephone, siret);
            ClientDAO clientDAO = new ClientDAOImpl(connection);
            clientDAO.addEntreprise(entreprise);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }






}
