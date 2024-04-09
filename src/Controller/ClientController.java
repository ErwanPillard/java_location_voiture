package Controller;

import Dao.ClientDAO;
import Dao.ClientDAOImpl;
import Dao.ModeleDAO;
import Dao.ModeleDAOImpl;
import Model.Client;
import Model.*;
import Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static BDD.init_bdd.*;

public class ClientController {
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

    public void addModele(String nom, int nbPlace, int  nbPorte, float tailleCoffre, String caracteristique, int prixJournalier, int noteSatisfaction,  Categorie categorie, boolean attelage){

        try(Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
                System.out.println("on passe en addModele");
                Modele modele = new Modele(nom, nbPlace, nbPorte,tailleCoffre, caracteristique, prixJournalier,noteSatisfaction, categorie, attelage);
                ModeleDAO modeleDAO = new ModeleDAOImpl(connection);
                modeleDAO.add(modele);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
